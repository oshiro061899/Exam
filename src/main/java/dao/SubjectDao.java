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
            statement = connection.prepareStatement("select * from subject where subject_cd = ? and school_cd = ?");
            statement.setString(1, cd);
            statement.setString(2, school.getSchoolCd());
            
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                subject = new Subject();
                subject.setCd(rSet.getString("subject_cd"));
                subject.setName(rSet.getString("subject_name"));
                subject.setSchool(school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subject;
    }

    /**
     * filterメソッド：学校に所属する全科目をリストで取得する
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 学校コードで絞り込み、科目コード順に並べる
            statement = connection.prepareStatement("select * from subject where school_cd = ? order by subject_cd asc");
            statement.setString(1, school.getSchoolCd());
            
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Subject subject = new Subject();
                subject.setCd(rSet.getString("subject_cd"));
                subject.setName(rSet.getString("subject_name"));
                subject.setSchool(school);
                list.add(subject); // リストに追加
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
     * saveメソッド：科目の保存（新規登録または更新）
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // INSERT文のカラム順序(school_cd, subject_cd, subject_name)に合わせてセット
                statement = connection.prepareStatement("insert into subject(school_cd, subject_cd, subject_name) values(?, ?, ?)");
                statement.setString(1, subject.getSchool().getSchoolCd()); // 1番目: 学校コード
                statement.setString(2, subject.getCd());                   // 2番目: 科目コード
                statement.setString(3, subject.getName());                 // 3番目: 科目名
            } else {
                // UPDATE文
                statement = connection.prepareStatement("update subject set subject_name = ? where subject_cd = ? and school_cd = ?");
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getSchoolCd());
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

    /**
     * deleteメソッド：科目の削除
     */
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // テーブルのカラム名 subject_cd に合わせる
            statement = connection.prepareStatement("delete from subject where subject_cd = ? and school_cd = ?");
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool().getSchoolCd());
            
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