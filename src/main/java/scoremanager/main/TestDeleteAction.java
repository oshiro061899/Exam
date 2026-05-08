package scoremanager.main;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // パラメータの取得
        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("subject_cd");
        int no = Integer.parseInt(request.getParameter("no"));

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAOで該当する成績を1件取得
        TestDao tDao = new TestDao();
        Test test = tDao.get(studentNo, subjectCd, no, teacher.getSchool());

        // JSPへ渡す
        request.setAttribute("test", test);
        request.getRequestDispatcher("test_delete.jsp").forward(request, response);
    }
}