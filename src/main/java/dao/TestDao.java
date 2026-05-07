package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test where school_cd = ?";

	// 1件取得
	public Test get(String studentNo, String subjectCd, int no) throws Exception {

		Test test = new Test();

		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {

			statement = connection.prepareStatement(
				"select * from test where student_no = ? and subject_cd = ? and no = ?"
			);

			statement.setString(1, studentNo);
			statement.setString(2, subjectCd);
			statement.setInt(3, no);

			ResultSet resultSet = statement.executeQuery();

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			if (resultSet.next()) {

				test.setStudent(studentDao.get(resultSet.getString("student_no")));
				test.setSubject(subjectDao.get(resultSet.getString("subject_cd")));
				test.setSchoolCd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));

			} else {
				test = null;
			}

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return test;
	}

	// ResultSet → List変換
	private List<Test> postFilter(ResultSet resultSet) throws Exception {

		List<Test> list = new ArrayList<>();

		try {

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			while (resultSet.next()) {

				Test test = new Test();

				test.setStudent(
					studentDao.get(resultSet.getString("student_no"))
				);

				test.setSubject(
					subjectDao.get(resultSet.getString("subject_cd"))
				);

				test.setSchoolCd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));

				list.add(test);
			}

		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 条件検索
	public List<Test> filter(
		String schoolCd,
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
			"select t.* " +
			"from test t " +
			"join student s on t.student_no = s.student_no " +
			"where t.school_cd = ? " +
			"and s.ent_year = ? " +
			"and s.class_num = ? " +
			"and t.subject_cd = ? " +
			"and t.no = ? " +
			"order by t.student_no asc";

		try {

			statement = connection.prepareStatement(sql);

			statement.setString(1, schoolCd);
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subjectCd);
			statement.setInt(5, no);

			resultSet = statement.executeQuery();

			list = postFilter(resultSet);

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

	// 保存（登録・更新）
	public boolean save(Test test) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;

		int count = 0;

		try {

			Test old = get(
				test.getStudent().getStudentNo(),
				test.getSubject().getSubjectCd(),
				test.getNo()
			);

			if (old == null) {

				statement = connection.prepareStatement(
					"insert into test(student_no, subject_cd, school_cd, no, point) values(?, ?, ?, ?, ?)"
				);

				statement.setString(
					1,
					test.getStudent().getStudentNo()
				);

				statement.setString(
					2,
					test.getSubject().getSubjectCd()
				);

				statement.setString(3, test.getSchoolCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());

			} else {

				statement = connection.prepareStatement(
					"update test set point = ? where student_no = ? and subject_cd = ? and no = ?"
				);

				statement.setInt(1, test.getPoint());

				statement.setString(
					2,
					test.getStudent().getStudentNo()
				);

				statement.setString(
					3,
					test.getSubject().getSubjectCd()
				);

				statement.setInt(4, test.getNo());
			}

			count = statement.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return count > 0;
	}
}