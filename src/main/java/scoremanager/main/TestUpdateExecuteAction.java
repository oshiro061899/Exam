package scoremanager.main;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータの取得
        String studentNo = req.getParameter("student_no");
        String subjectCd = req.getParameter("subject_cd");
        int num = Integer.parseInt(req.getParameter("num"));
        int point = Integer.parseInt(req.getParameter("point"));

        // 2. Testビーンに値をセット
        Test test = new Test();
        test.setPoint(point);
        test.setNo(num);
        test.setSchool(teacher.getSchool());
        
        // StudentとSubjectの入れ物を作ってセット
        Student student = new Student();
        student.setStudentNo(studentNo);
        test.setStudent(student);
        
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        test.setSubject(subject);

	     // 3. DB更新
	     TestDao tDao = new TestDao();
	     tDao.save(test); 

        // 4. 完了画面へ
        req.getRequestDispatcher("test_update_done.jsp").forward(req, res);
    }
}