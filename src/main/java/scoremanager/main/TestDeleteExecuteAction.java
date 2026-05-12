//// TestDeleteExecuteAction.java
//package scoremanager.main;
//
//import bean.School;
//import bean.Student;
//import bean.Subject;
//import bean.Teacher;
//import bean.Test;
//import dao.TestDao;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import tool.Action;
//
//public class TestDeleteExecuteAction extends Action {
//
//	@Override
//	public void execute(
//		HttpServletRequest req,
//		HttpServletResponse res
//	) throws Exception {
//
//		// セッション
//		HttpSession session =
//			req.getSession();
//
//		// ログインユーザー
//		Teacher teacher =
//			(Teacher)session.getAttribute(
//				"user"
//			);
//
//		// パラメータ取得
//		String studentNo =
//			req.getParameter(
//				"student_no"
//			);
//
//		String subjectCd =
//			req.getParameter(
//				"subject_cd"
//			);
//
//		String classNum =
//			req.getParameter(
//				"class_num"
//			);
//
//		String entYear =
//			req.getParameter(
//				"ent_year"
//			);
//
//		int no =
//			Integer.parseInt(
//				req.getParameter("no")
//			);
//
//		// Bean作成
//		Test test =
//			new Test();
//
//		Student student =
//			new Student();
//
//		student.setStudentNo(
//			studentNo
//		);
//
//		Subject subject =
//			new Subject();
//
//		subject.setCd(
//			subjectCd
//		);
//
//		School school =
//			teacher.getSchool();
//
//		// セット
//		test.setStudent(student);
//
//		test.setSubject(subject);
//
//		test.setSchool(school);
//
//		test.setClassNum(classNum);
//
//		test.setNo(no);
//
//		// DAO
//		TestDao testDao =
//			new TestDao();
//
//		// 削除実行
//		testDao.delete(test);
//
//		// 検索条件保持
//		req.setAttribute(
//			"f1",
//			entYear
//		);
//
//		req.setAttribute(
//			"f2",
//			classNum
//		);
//
//		req.setAttribute(
//			"f3",
//			subjectCd
//		);
//
//		req.setAttribute(
//			"f4",
//			no
//		);
//
//		req.getRequestDispatcher(
//				"test_delete_done.jsp"
//			).forward(req, res);
//	}
//}