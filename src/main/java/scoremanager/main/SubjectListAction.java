package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ローカル変数の初期化
        SubjectDao subjectDao = new SubjectDao();

        // 2. DBからログインユーザーの学校に紐づく全科目を抜き出す
        // get(1件) ではなく filter(全件) を使用します
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // 3. レスポンス値をセット
        // JSP側の ${subjects} で参照できるようにセット
        req.setAttribute("subjects", subjects);

        // 4. JSPへフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}