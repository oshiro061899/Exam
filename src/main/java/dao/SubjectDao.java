package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	/**
	 * getメソッド：科目コードと学校を指定して科目インスタンスを1件取得する
	 */
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null;
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			// SQL文のセット：科目コードと学校コードで絞り込み
			statement = connection.prepareStatement("select * from subject where subject_cd = ? and school_cd = ?");
			statement.setString(1, cd);
			statement.setString(2, school.getSchoolCd());
			
			// SQL実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// データが存在する場合、Beanにセット
				subject = new Subject();
				subject.setCd(rSet.getString("subject_cd"));
				subject.setName(rSet.getString("subject_name"));
				subject.setSchool(school);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// リソースの解放
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return subject;
	}

	/**
	 * saveメソッド：科目の保存（新規登録または更新）
	 */
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// データベースから検索して存在チェック
			Subject old = get(subject.getCd(), subject.getSchool());

			if (old == null) {
				// 存在しない場合：INSERT（新規登録）
				statement = connection.prepareStatement("insert into subject(school_cd, subject_cd, subject_name) values(?, ?, ?)");
				statement.setString(1, subject.getSchool().getSchoolCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				// 存在する場合：UPDATE（更新）
				statement = connection.prepareStatement("update subject set subject_name = ? where subject_cd = ? and school_cd = ?");
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getSchool().getSchoolCd());
			}

			// SQLの実行
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
		return count > 0;
	}

	/**
	 * deleteメソッド：科目の削除
	 */
	public boolean delete(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// SQLセット
			statement = connection.prepareStatement("delete from subject where subject_cd = ? and school_cd = ?");
			statement.setString(1, subject.getCd());
			statement.setString(2, subject.getSchool().getSchoolCd());
			
			// SQLの実行
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
		return count > 0;
	}
	
	public List<Subject> filter(School school) throws Exception {

		List<Subject> list = new ArrayList<>();

		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from subject where school_cd = ? order by subject_cd");
			statement.setString(1,school.getSchoolCd());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Subject subject = new Subject();

				subject.setCd(resultSet.getString("subject_cd"));
				subject.setName(resultSet.getString("subject_name"));
				subject.setSchool(school);
				list.add(subject);
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

		return list;
	}
}