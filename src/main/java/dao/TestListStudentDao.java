package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Test;

public class TestListStudentDao extends Dao {

    /**
     * 指定された学生の成績一覧を取得する
     */
    public List<Test> filter(Student student) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        // SQL: 学生番号で検索し、科目名を表示するためにSubjectテーブルと結合
        // カラム名は DB定義に合わせて no, point を使用
        String sql = 
            "select t.subject_cd, s.subject_name, t.no, t.point " +
            "from test t " +
            "join subject s on t.subject_cd = s.subject_cd and t.school_cd = s.school_cd " +
            "where t.student_no = ? " +
            "order by t.subject_cd asc, t.no asc";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentNo());
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                test.setStudent(student);

                Subject sub = new Subject();
                sub.setCd(rSet.getString("subject_cd"));
                sub.setName(rSet.getString("subject_name"));
                test.setSubject(sub);

                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));

                list.add(test);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}
