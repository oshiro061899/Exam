package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // hiddenから3つのキーを受け取る
        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("subject_cd");
        // Integer.parseInt の中の綴りミスを修正
        int no = Integer.parseInt(request.getParameter("no"));

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        TestDao tDao = new TestDao();
        // 削除対象を特定して削除
        Test test = tDao.get(studentNo, subjectCd, no, teacher.getSchool());
        if (test != null) {
            tDao.delete(test);
        }

        // 成績一覧へ戻る（リダイレクト）
        response.sendRedirect("TestList.action");
    }
}