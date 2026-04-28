package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.security.auth.Subject;

import bean.School;

public class SubjectDao extends Dao {

    public Subject get(String cd, School school) throws Exception {

        Subject subject = new Subject();

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from subject where cd = ? and school_cd = ?"
            );

            statement.setString(1, cd);
            statement.setString(2, school.getSchoolCd());


        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return subject;
    }
}