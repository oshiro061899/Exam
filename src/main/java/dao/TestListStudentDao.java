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

    public List<Test> filter(Student student) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        
        String sql = "SELECT t.subject_cd, s.subject_name, t.no, t.point " +
                     "FROM test t " +
                     "JOIN subject s ON t.subject_cd = s.subject_cd AND t.school_cd = s.school_cd " +
                     "WHERE t.student_no = ? AND t.school_cd = ? AND t.point IS NOT NULL " + 
                     "ORDER BY t.subject_cd ASC, t.no ASC";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentNo());
            statement.setString(2, student.getSchool().getSchoolCd());
            
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                
                // 科目情報のセット
                Subject subject = new Subject();
                subject.setCd(rSet.getString("subject_cd"));
                subject.setName(rSet.getString("subject_name"));
                
                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setSchool(student.getSchool());
                
                list.add(test);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Daoクラスの仕組みに合わせ、安全にクローズ
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}