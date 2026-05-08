// TestRegistExecuteAction.java
package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req,
		HttpServletResponse res
	) throws Exception {

		HttpSession session = req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		String[] studentNo =
			req.getParameterValues("student_no");

		String[] points =
			req.getParameterValues("point");

		String subjectCd =
			req.getParameter("subject_cd");

		String classNum =
			req.getParameter("class_num");

		int no =
			Integer.parseInt(
				req.getParameter("no")
			);

		TestDao testDao = new TestDao();

		for (int i = 0; i < studentNo.length; i++) {

			Test test = new Test();

			Student student = new Student();
			student.setStudentNo(studentNo[i]);

			Subject subject = new Subject();
			subject.setCd(subjectCd);

			School school = teacher.getSchool();

			test.setStudent(student);

			test.setSubject(subject);

			test.setSchool(school);

			test.setClassNum(classNum);

			test.setNo(no);

			if (
				points[i] != null &&
				!points[i].equals("")
			) {

				test.setPoint(
					Integer.parseInt(points[i])
				);

				testDao.save(test);
			}
		}

		req.getRequestDispatcher(
				"test_regist_done.jsp"
			).forward(req, res);
	}
}