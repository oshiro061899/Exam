package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータから科目コードを取得
        String cd = request.getParameter("cd");

        // DAOを使って科目の詳細を取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, school);

        // 取得した科目情報をリクエスト属性にセットしてJSPへ送る
        request.setAttribute("subject", subject);
        
        // 削除確認画面へフォワード
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}