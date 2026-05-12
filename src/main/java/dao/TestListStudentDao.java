package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Test;

public class TestListStudentDao extends Dao {

    /**
     * 学生情報から成績リストを取得する
     */
	// --- 修正箇所：filterメソッドの中 ---

	public List<Test> filter(Student student) throws Exception {
	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    // ① SQL文の中の「no」を「test_no」に書き換える（2箇所）
	    String sql = "SELECT t.subject_cd, s.subject_name, t.test_no, t.test_point " + 
	             "FROM test t " +
	             "JOIN subject s ON t.subject_cd = s.subject_cd AND t.school_cd = s.school_cd " +
	             "WHERE t.student_no = ? " +
	             "ORDER BY t.subject_cd ASC, t.test_no ASC";
	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, student.getStudentNo());
	        rSet = statement.executeQuery();

	        while (rSet.next()) {
	            Test test = new Test();
	            // 省略（subject等のセット）
	            
	            test.setNo(rSet.getInt("test_no")); 
	            test.setPoint(rSet.getInt("test_point"));
	            
	            list.add(test);
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // クローズ処理
	    }
	    return list;
	}
}