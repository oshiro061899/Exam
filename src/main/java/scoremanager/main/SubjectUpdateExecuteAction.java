package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// パラメータ取得
		String entYearStr = req.getParameter("ent_year");
		String studentNo = req.getParameter("no");
		String studentName = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend"); // チェックボックス

		// 在学フラグの判定
		// チェックが入っている場合は "on" などの文字列が入り、入っていない場合は null になる
		boolean isAttend = false;
		if (isAttendStr != null) {
			isAttend = true;
		}

		// 学生Beanの作成と値のセット
		Student student = new Student();
		student.setEntYear(Integer.parseInt(entYearStr));
		student.setStudentNo(studentNo);
		student.setStudentName(studentName);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());

		// DBに保存（更新）
		StudentDao studentDao = new StudentDao();
		studentDao.save(student);

		// 完了画面へ遷移
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}