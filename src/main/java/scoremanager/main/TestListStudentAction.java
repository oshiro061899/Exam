package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // --- 1. プルダウンデータの準備 (常に必要) ---
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();
        
        List<Integer> entYearSet = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) entYearSet.add(i);

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", cDao.filter(teacher.getSchool()));
        req.setAttribute("subjects", sDao.filter(teacher.getSchool()));

        // --- 2. 検索処理 ---
        // JSPの「学生番号入力欄」の name を "f4" に合わせて取得
        String studentNo = req.getParameter("f4");

        if (studentNo != null && !studentNo.isEmpty()) {
            StudentDao studentDao = new StudentDao();
            TestListStudentDao tDao = new TestListStudentDao();

            Student student = studentDao.get(studentNo);
            if (student != null) {
                List<Test> tests = tDao.filter(student);
                req.setAttribute("student", student);
                req.setAttribute("tests", tests);
            } else {
                req.setAttribute("search_error", "学生情報が存在しませんでした。");
            }
        }

        // 入力値を保持
        req.setAttribute("f4", studentNo);

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
