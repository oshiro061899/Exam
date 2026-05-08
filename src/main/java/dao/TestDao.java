package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	// 基本SQL
	private String baseSql = "select * from test where school_cd = ?";

	// 1件取得
	public Test get(String studentNo, String subjectCd, int no) throws Exception {

		Test test = null;

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
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {

				test = new Test();

				String schoolCd = resultSet.getString("school_cd");
				String subCd = resultSet.getString("subject_cd");

				School school = null;
				Subject subject = null;

				if (schoolCd != null) {
					school = schoolDao.get(schoolCd);

					test.setSchool(school);
				}

				if (subCd != null && school != null) {
					subject = subjectDao.get(subCd, school);

					test.setSubject(subject);
				}

				test.setStudent(
					studentDao.get(
						resultSet.getString("student_no")
					)
				);

				test.setClassNum(
					resultSet.getString("class_num")
				);

				test.setNo(
					resultSet.getInt("no")
				);

				test.setPoint(
					resultSet.getInt("point")
				);
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
	private List<Test> postFilter(ResultSet resultSet)
		throws Exception {

		List<Test> list = new ArrayList<>();

		try {

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();

			while (resultSet.next()) {

				Test test = new Test();

				String schoolCd =
					resultSet.getString("school_cd");

				String subjectCd =
					resultSet.getString("subject_cd");

				School school = null;
				Subject subject = null;

				// school が存在する場合
				if (schoolCd != null) {

					school = schoolDao.get(schoolCd);

					test.setSchool(school);
				}

				// subject が存在する場合
				if (subjectCd != null && school != null) {

					subject = subjectDao.get(
						subjectCd,
						school
					);

					test.setSubject(subject);
				}

				test.setStudent(
					studentDao.get(
						resultSet.getString("student_no")
					)
				);

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
			"select s.no as student_no, " +
			"s.class_num, " +
			"t.subject_cd, " +
			"t.school_cd, " +
			"t.no, " +
			"t.point " +
			"from student s " +
			"left join test t " +
			"on s.no = t.student_no " +
			"and t.subject_cd = ? " +
			"and t.no = ? " +
			"where s.school_cd = ? " +
			"and s.ent_year = ? " +
			"and s.class_num = ? " +
			"order by s.no";

		try {

			statement = connection.prepareStatement(sql);

			statement.setString(1, subjectCd);
			statement.setInt(2, no);
			statement.setString(3, schoolCd);
			statement.setInt(4, entYear);
			statement.setString(5, classNum);

			resultSet = statement.executeQuery();

			list = postFilter(resultSet);

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

	// 保存（登録・更新）
	public boolean save(Test test) throws Exception {

		Connection connection = getConnection();
		PreparedStatement statement = null;

		int count = 0;

		try {

			// 既存データ確認
			Test old = get(
				test.getStudent().getStudentNo(),
				test.getSubject().getCd(),
				test.getNo()
			);

			// 新規登録
			if (old == null) {

				statement = connection.prepareStatement(
					"insert into test(student_no, subject_cd, school_cd, class_num, no, point) values(?, ?, ?, ?, ?, ?)"
				);

				statement.setString(
					1,
					test.getStudent().getStudentNo()
				);

				statement.setString(
					2,
					test.getSubject().getCd()
				);

				statement.setString(
					3,
					test.getSchool().getSchoolCd()
				);

				statement.setString(
					4,
					test.getClassNum()
				);

				statement.setInt(
					5,
					test.getNo()
				);

				statement.setInt(
					6,
					test.getPoint()
				);

			// 更新
			} else {

				statement = connection.prepareStatement(
					"update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
				);

				statement.setInt(
					1,
					test.getPoint()
				);

				statement.setString(
					2,
					test.getStudent().getStudentNo()
				);

				statement.setString(
					3,
					test.getSubject().getCd()
				);

				statement.setString(
					4,
					test.getSchool().getSchoolCd()
				);

				statement.setInt(
					5,
					test.getNo()
				);
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