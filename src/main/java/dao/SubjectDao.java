package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
    /**
     * getメソッド 科目コードと学校を指定して科目インスタンスを1件取得する
     *
     * @param cd:String 科目コード
     * @param school:School 学校
     * @return 科目クラスのインスタンス 存在しない場合はnull
     * @throws Exception
     */
    public Subject get(String cd, School school) throws Exception {
        // 科目インスタンスを初期化
        Subject subject = new Subject();
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;

        try {
            // プリペアードステートメントにSQL文をセット
            // 科目コード(CD)と学校コード(SCHOOL_CD)の両方で絞り込む
            statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ?");
            // プリペアードステートメントに値をバインド
            statement.setString(1, cd);
            statement.setString(2, school.getSchoolCd());
            // プリペアードステートメントを実行
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // リザルトセットが存在する場合
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
            } else {
                // 存在しない場合
                subject = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return subject;
    }
}