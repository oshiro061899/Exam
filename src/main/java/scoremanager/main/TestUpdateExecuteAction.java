package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションからユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool(); // 教員の所属校を取得

        // 2. リクエストパラメータの取得
        String studentNo = req.getParameter("student_no"); // 学籍番号
        String subjectCd = req.getParameter("subject_cd"); // 科目コード
        String classNum = req.getParameter("class_num");   // クラス番号
        int num = Integer.parseInt(req.getParameter("num"));       // 回数
        int point = Integer.parseInt(req.getParameter("point"));   // 得点

        // 3. Testビーンの組み立て
        Test test = new Test();
        
        // --- 学生情報のセット ---
        Student student = new Student();
        student.setStudentNo(studentNo);
        test.setStudent(student);

        // --- 科目情報のセット ---
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        test.setSubject(subject);

        // --- その他の属性セット ---
        test.setSchool(school);     // 学校
        test.setClassNum(classNum); // クラス番号
        test.setNo(num);           // テスト回数
        test.setPoint(point);      // 点数

        // 4. DBへの保存処理
        TestDao testDao = new TestDao();
        
        // 成績登録は一般的に「一括更新」を考慮するためリスト形式で渡す設計が多いです
        List<Test> testList = new ArrayList<>();
        testList.add(test);
        
        // DAOの保存メソッド呼び出し
        testDao.save(testList);

        // 5. 完了画面へフォワード
        // パスを "test_update_done.jsp" に修正
        req.getRequestDispatcher("test_update_done.jsp").forward(req, res);
    }
}