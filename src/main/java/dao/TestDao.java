package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
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

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			if (rSet.next()) {
				test = new Test();
				// 関連するBeanをDAO経由で取得してセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setClassNum(rSet.getString("class_num"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
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

	/**
	 * filterメソッド：条件を指定して成績一覧を取得する
	 */
	public List<Test> filter(School school, int entYear, String classNum, Subject subject, int no) throws Exception {
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		String sql = "select t.* from test t " +
					 "join student s on t.student_no = s.student_no " +
					 "where t.school_cd = ? and s.ent_year = ? and s.class_num = ? and t.subject_cd = ? and t.no = ? " +
					 "order by t.student_no asc";

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, school.getSchoolCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());
			statement.setInt(5, no);

			ResultSet rSet = statement.executeQuery();
			
			StudentDao studentDao = new StudentDao();
			while (rSet.next()) {
				Test test = new Test();
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subject);
				test.setSchool(school);
				test.setClassNum(rSet.getString("class_num"));
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

	/**
	 * saveメソッド：成績の保存（登録・更新）
	 */
	public boolean save(Test test) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// 既存データの確認!
			Test old = get(test.getStudent().getStudentNo(), test.getSubject().getCd(), test.getNo(), test.getSchool());

			if (old == null) {
				// 新規登録
				statement = connection.prepareStatement(
					"insert into test(student_no, subject_cd, school_cd, class_num, no, point) values(?, ?, ?, ?, ?, ?)");
				statement.setString(1, test.getStudent().getStudentNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getSchoolCd());
				statement.setString(4, test.getClassNum());
				statement.setInt(5, test.getNo());
				statement.setInt(6, test.getPoint());
			} else {
				// 更新
				statement = connection.prepareStatement(
					"update test set point = ? where student_no = ? and subject_cd = ? and no = ? and school_cd = ?");
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getStudentNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setInt(4, test.getNo());
				statement.setString(5, test.getSchool().getSchoolCd());
			}
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