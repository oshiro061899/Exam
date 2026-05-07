package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ（科目コード）を取得
        String subjectCd = req.getParameter("cd");
        
        // DAOを使ってDBから特定の科目情報を取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, teacher.getSchool());
        
        // リクエストにデータをセット
        req.setAttribute("subject", subject);

        //  更新画面（JSP）へフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}