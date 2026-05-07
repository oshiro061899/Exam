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
        // セッションからユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータ（科目コードと科目名）を取得
        // JSPの"cd"と"name"から送られてくる値
        String subjectCd = req.getParameter("cd");
        String subjectName = req.getParameter("name");

        // 2. 科目Beanの作成と値のセット
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setName(subjectName);
        // ログイン中の教員の学校をセット
        subject.setSchool(teacher.getSchool());

        // 3. DBに保存（更新）
        SubjectDao sDao = new SubjectDao();
        sDao.save(subject);

        // 4. 完了画面へ遷移
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}