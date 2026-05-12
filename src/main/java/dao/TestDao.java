// TestDao.java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql =
		"select * from test where school_cd = ? ";

	/**
	 * 1件取得
	 */
	public Test get(
		String schoolCd,
		String studentNo,
		String subjectCd,
		int no
	) throws Exception {

		Test test = null;

		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			String sql =
				baseSql +
				"and student_no = ? and subject_cd = ? and no = ?";

			statement = connection.prepareStatement(sql);

			statement.setString(1, schoolCd);
			statement.setString(2, studentNo);
			statement.setString(3, subjectCd);
			statement.setInt(4, no);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				test = new Test();

				Student student = new Student();
				student.setStudentNo(resultSet.getString("student_no"));

				Subject subject = new Subject();
				subject.setCd(resultSet.getString("subject_cd"));

				School school = new School();
				school.setSchoolCd(resultSet.getString("school_cd"));

				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setClassNum(resultSet.getString("class_num"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
			}

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return test;
	}

	/**
	 * 検索結果整形
	 */
	private List<Test> postFilter(
			ResultSet resultSet,
			School school
		) throws Exception {

			List<Test> list = new ArrayList<>();

			while (resultSet.next()) {

				Test test = new Test();

				Student student = new Student();

				student.setStudentNo(
					resultSet.getString("student_no")
				);

				student.setStudentName(
					resultSet.getString("student_name")
				);

				Subject subject = new Subject();

				subject.setCd(
					resultSet.getString("subject_cd")
				);

				subject.setName(
					resultSet.getString("subject_name")
				);

				test.setStudent(student);

				test.setSubject(subject);

				test.setSchool(school);

				test.setClassNum(
					resultSet.getString("class_num")
				);

				test.setNo(
					resultSet.getInt("no")
				);

				test.setPoint(
					resultSet.getInt("point")
				);

				list.add(test);
			}

			return list;
		}

	/**
	 * 成績検索
	 */
	public List<Test> filter(
		School school,
		int entYear,
		String classNum,
		String subjectCd,
		int no
	) throws Exception {

		List<Test> list = new ArrayList<>();

		Connection connection = getConnection();

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		String sql =
				"select " +
				"t.*, " +
				"s.ent_year, " +
				"st.student_name, " +
				"sub.subject_name " +
				"from test t " +

				"inner join student s " +
				"on t.student_no = s.student_no " +

				"inner join student st " +
				"on t.student_no = st.student_no " +

				"inner join subject sub " +
				"on t.subject_cd = sub.subject_cd " +

				"where t.school_cd = ? " +
				"and s.ent_year = ? " +
				"and t.class_num = ? " +
				"and t.subject_cd = ? " +
				"and t.no = ? " +

				"order by t.student_no";

		try {

			statement = connection.prepareStatement(sql);

			statement.setString(1, school.getSchoolCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subjectCd);
			statement.setInt(5, no);

			resultSet = statement.executeQuery();

			list = postFilter(resultSet, school);

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return list;
	}

	/**
	 * 保存
	 */
	public boolean save(Test test) throws Exception {

		Connection connection = getConnection();

		PreparedStatement statement = null;

		int count = 0;

		try {

			Test old = get(
				test.getSchool().getSchoolCd(),
				test.getStudent().getStudentNo(),
				test.getSubject().getCd(),
				test.getNo()
			);

			if (old == null) {

				String insert =
					"insert into test(" +
					"student_no," +
					"school_cd," +
					"subject_cd," +
					"no," +
					"point," +
					"class_num" +
					") values(?,?,?,?,?,?)";

				statement = connection.prepareStatement(insert);

				statement.setString(1, test.getStudent().getStudentNo());
				statement.setString(2, test.getSchool().getSchoolCd());
				statement.setString(3, test.getSubject().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());

			} else {

				String update =
					"update test set point = ? " +
					"where student_no = ? " +
					"and subject_cd = ? " +
					"and no = ?";

				statement = connection.prepareStatement(update);

				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getStudentNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setInt(4, test.getNo());
			}

			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return count > 0;} 
/**
 * 特定の学生の全成績を取得する
 */
public List<Test> filter(Student student) throws Exception {
    List<Test> list = new ArrayList<>();
    Connection connection = getConnection();
    PreparedStatement statement = null;

    // 科目名も表示したいので subjectテーブルをJOIN
    String sql = "select t.*, s.subject_name from test t " +
                 "join subject s on t.subject_cd = s.subject_cd and t.school_cd = s.school_cd " +
                 "where t.student_no = ? order by t.subject_cd asc, t.no asc";

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
	