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

        // SQL: 学生情報と成績を結合。回数(no)が1と2の結果を取得
        String sql = 
            "select st.ent_year, st.class_num, st.student_no, st.student_name, t.no, t.point " +
            "from student st " +
            "left join test t on st.student_no = t.student_no and t.subject_cd = ? " +
            "where st.ent_year = ? and st.class_num = ? " +
            "order by st.student_no asc, t.no asc";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                Test test = new Test();
                
                Student student = new Student();
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setStudentNo(rSet.getString("student_no"));
                student.setStudentName(rSet.getString("student_name"));
                test.setStudent(student);

                test.setSubject(subject);
                test.setNo(rSet.getInt("no"));
                // pointがnull（未受験）の場合は-1等で判定するか、そのままセット
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

//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import bean.School;
//import bean.Subject;
//import bean.TestListSubject;
//
//public class TestListSubjectDao extends Dao {
//
//    /**
//     * クラス別・科目別の成績一覧を取得する
//     * @param school 学校
//     * @param entYear 入学年度
//     * @param classNum クラス番号
//     * @param subject 科目
//     * @return 1行に1回目・2回目の点数がまとまったリスト
//     */
//    public List<TestListSubject> filter(School school, int entYear, String classNum, Subject subject) throws Exception {
//        
//        // 最終的にJSPに渡すリスト
//        List<TestListSubject> list = new ArrayList<>();
//        
//        // 学生ごとにデータをまとめるためのマップ (キー：学生番号)
//        Map<String, TestListSubject> map = new HashMap<>();
//        
//        Connection connection = getConnection();
//        PreparedStatement statement = null;
//
//        // SQLのポイント：
//        // 1. 指定したクラスの学生(STUDENT)をベースに、成績(TEST)を外部結合(LEFT JOIN)する
//        // 2. 成績がない学生も表示するため外部結合にする
//        String sql = "SELECT s.ent_year, s.class_num, s.student_no, s.student_name, t.no, t.point " +
//                     "FROM student s " +
//                     "LEFT JOIN test t ON s.student_no = t.student_no " +
//                     "    AND t.subject_cd = ? AND t.school_cd = ? " +
//                     "WHERE s.school_cd = ? AND s.ent_year = ? AND s.class_num = ? " +
//                     "ORDER BY s.student_no ASC, t.no ASC";
//
//        try {
//            statement = connection.prepareStatement(sql);
//            statement.setString(1, subject.getCd());
//            statement.setString(2, school.getSchoolCd());
//            statement.setString(3, school.getSchoolCd());
//            statement.setInt(4, entYear);
//            statement.setString(5, classNum);
//            
//            ResultSet rSet = statement.executeQuery();
//
//            while (rSet.next()) {
//                String studentNo = rSet.getString("student_no");
//                
//                // すでにマップにその学生がいるか確認
//                TestListSubject tls = map.get(studentNo);
//                
//                if (tls == null) {
//                    // 初めて出てきた学生ならBeanを新規作成して基本情報をセット
//                    tls = new TestListSubject();
//                    tls.setEntYear(rSet.getInt("ent_year"));
//                    tls.setClassNum(rSet.getString("class_num"));
//                    tls.setStudentNo(studentNo);
//                    tls.setStudentName(rSet.getString("student_name"));
//                    
//                    map.put(studentNo, tls);
//                    list.add(tls); // 表示順序を保持するためにリストにも追加
//                }
//                
//                // 点数情報があれば、回数をキーにしてBean内のMapに保存
//                int no = rSet.getInt("no");
//                if (no > 0) {
//                    // getIntはnullの場合0を返すため、noが1以上の時のみセット
//                    tls.putPoint(no, rSet.getInt("point"));
//                }
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (statement != null) statement.close();
//            if (connection != null) connection.close();
//        }
//
//        return list;
//    }
//}