package scoremanager;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータから学生番号を取得
        String studentNo = request.getParameter("f1");

        if (studentNo != null && !studentNo.isEmpty()) {
            StudentDao sDao = new StudentDao();
            TestDao tDao = new TestDao();

            // 学生情報を取得
            Student student = sDao.get(studentNo);
            
            if (student != null) {
                // その学生の全成績を取得するメソッド（後述のTestDaoに追加が必要）
                List<Test> tests = tDao.filter(student);
                request.setAttribute("tests", tests);
                request.setAttribute("student", student);
            } else {
                request.setAttribute("errors", "学生が見つかりませんでした");
            }
        }

        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}