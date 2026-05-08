package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    /**
     * getメソッド：特定の成績を1件取得する
     */
    public Test get(String studentNo, String subjectCd, int no, School school) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 学生番号、科目コード、回数、学校コードで絞り込み
            statement = connection.prepareStatement(
                "select * from test where student_no = ? and subject_cd = ? and no = ? and school_cd = ?");
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setInt(3, no);
            statement.setString(4, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                test = new Test();
                // 簡易的にIDだけセットしたBeanを作成（必要に応じて各Daoで完全なBeanを取得してください）
                Student student = new Student();
                student.setStudentNo(rSet.getString("student_no"));
                
                Subject subject = new Subject();
                subject.setCd(rSet.getString("subject_cd"));
                
                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setSchool(school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }

    /**
     * deleteメソッド：成績の削除
     */
    public boolean delete(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "delete from test where student_no = ? and subject_cd = ? and no = ? and school_cd = ?");
            statement.setString(1, test.getStudent().getStudentNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setInt(3, test.getNo());
            statement.setString(4, test.getSchool().getSchoolCd());

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
}