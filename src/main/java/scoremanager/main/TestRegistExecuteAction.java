package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();

		// パラメータ取得
		String[] studentNos = req.getParameterValues("student_no");
		String subjectCd = req.getParameter("subject_cd");
		int no = Integer.parseInt(req.getParameter("no"));

		Subject subject =
			subjectDao.get(subjectCd, teacher.getSchool());

		// 学生ごとに点数更新
		for (int i = 0; i < studentNos.length; i++) {

			String pointStr =
				req.getParameter("point_" + studentNos[i]);

			// 未入力スキップ
			if (pointStr == null || pointStr.equals("")) {
				continue;
			}

			int point = Integer.parseInt(pointStr);

			Test test = new Test();

			test.setStudent(
			    studentDao.get(studentNos[i])
			);

			test.setSubject(subject);

			test.setSchool(teacher.getSchool());

			test.setClassNum(
			    test.getStudent().getClassNum()
			);

			test.setNo(no);

			test.setPoint(point);

			testDao.save(test);
		}

		req.getRequestDispatcher("test_regist_done.jsp")
			.forward(req, res);
	}
}