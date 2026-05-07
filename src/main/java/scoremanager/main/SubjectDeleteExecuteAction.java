package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 2. リクエストパラメータから科目コードを取得
        String cd = request.getParameter("cd");

        // 3. DAOを使って削除対象の科目インスタンスを取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, school);

        // 4. 科目が存在する場合に削除処理を実行
        if (subject != null) {
            sDao.delete(subject);
        }

        // 5. 削除完了後、科目一覧画面へリダイレクト
        response.sendRedirect("SubjectList.action");
    }
}