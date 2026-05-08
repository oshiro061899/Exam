package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. リクエストパラメータから成績を特定するキーを取得
        // 成績の更新には通常「学籍番号」「科目コード」「回数」が必要です
        String studentNo = req.getParameter("no");
        String subjectCd = req.getParameter("subject_cd");
        int num = Integer.parseInt(req.getParameter("num"));

        // 2. DAOを使って、現在の成績情報を取得
        TestDao tDao = new TestDao();
        // getメソッドは (学生番号, 科目コード, 回数, 学校) で1件特定する想定
        Test test = tDao.get(studentNo, subjectCd, num, teacher.getSchool());

        // 3. 画面のプルダウン等で使用するデータを準備
        // 科目一覧を取得（もし科目も変更可能にする場合）
        SubjectDao subDao = new SubjectDao();
        List<Subject> subjects = subDao.filter(teacher.getSchool());

        // 4. リクエストにデータをセット
        // 取得した成績情報をセット（JSPで ${test.student.studentName} や ${test.point} で表示）
        req.setAttribute("test", test);
        req.setAttribute("subjects", subjects);

        // 5. 更新画面（JSP）へフォワード
        req.getRequestDispatcher("test_update.jsp").forward(req, res);
    }
}