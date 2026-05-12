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
     * 学生情報から成績リストを取得する
     */
    public List<Test> filter(Student student) throws Exception {
        List<List<Test>> result = new ArrayList<>(); // 実際にはList<Test>で返せばOKです
        List<Test> list = new ArrayList<>();
        
        Connection connection = getConnection();
        PreparedStatement statement = null;

        // 科目名を取得するために SUBJECT テーブルと JOIN します
        String sql = "SELECT t.subject_cd, s.subject_name, t.no, t.point " +
                     "FROM test t " +
                     "JOIN subject s ON t.subject_cd = s.subject_cd AND t.school_cd = s.school_cd " +
                     "WHERE t.student_no = ? " +
                     "ORDER BY t.subject_cd ASC, t.no ASC";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentNo());
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                Subject subject = new Subject();
                
                // 科目情報をセット
                subject.setCd(rSet.getString("subject_cd"));
                subject.setName(rSet.getString("subject_name"));
                
                test.setSubject(subject);
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                
                list.add(test);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}