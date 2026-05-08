package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータを取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 2. Beanを組み立てる
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // 3. DBを更新（DAOのsaveメソッドは、既存データがあればUPDATEする設計になっています）
        SubjectDao sDao = new SubjectDao();
        sDao.save(subject);

        // 4. 完了画面へフォワード、または一覧へリダイレクト
        // ここでは一覧へ戻る例にします
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}