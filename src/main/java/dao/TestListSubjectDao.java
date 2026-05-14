package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Test;

public class TestListSubjectDao extends Dao {

    /**
     * 科目・入学年度・クラスを指定して成績一覧を取得する
     */
	public List<Test> filter(int entYear, String classNum, Subject subject) throws Exception {
	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    // 【確定版SQL】
	    // 1. STUDENT を主軸にし、指定科目の成績(TEST)だけを INNER JOIN する
	    // 2. 結合条件(ON)に SUBJECT_CD を含めることで、他科目の成績を持つ学生も除外する
	    String sql = 
	        "SELECT s.ENT_YEAR, s.CLASS_NUM, s.STUDENT_NO, s.STUDENT_NAME, t.NO, t.POINT " +
	        "FROM STUDENT s " +
	        "INNER JOIN TEST t ON s.STUDENT_NO = t.STUDENT_NO " +
	        "  AND s.SCHOOL_CD = t.SCHOOL_CD " +
	        "  AND t.SUBJECT_CD = ? " + // ここで結合対象を「指定科目」に限定
	        "WHERE s.ENT_YEAR = ? " +
	        "  AND s.CLASS_NUM = ? " +
	        "  AND s.SCHOOL_CD = ? " +
	        "ORDER BY s.STUDENT_NO ASC, t.NO ASC";

	    try {
	        statement = connection.prepareStatement(sql);
	        
	        // パラメータのセット（順番を絶対に間違えないでください）
	        statement.setString(1, subject.getCd());         // 1番目: t.SUBJECT_CD
	        statement.setInt(2, entYear);                   // 2番目: s.ENT_YEAR
	        statement.setString(3, classNum);               // 3番目: s.CLASS_NUM
	        statement.setString(4, subject.getSchool().getSchoolCd()); // 4番目: s.SCHOOL_CD
	        
	        ResultSet rSet = statement.executeQuery();

	        while (rSet.next()) {
	            Test test = new Test();
	            
	            Student student = new Student();
	            student.setEntYear(rSet.getInt("ENT_YEAR"));
	            student.setClassNum(rSet.getString("CLASS_NUM"));
	            student.setStudentNo(rSet.getString("STUDENT_NO"));
	            student.setStudentName(rSet.getString("STUDENT_NAME"));
	            test.setStudent(student);

	            test.setSubject(subject);
	            test.setNo(rSet.getInt("NO"));
	            test.setPoint(rSet.getInt("POINT"));

	            list.add(test);
	        }
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return list;
	}
}
