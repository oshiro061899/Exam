package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req,
		HttpServletResponse res
	) throws Exception {

		HttpSession session = req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		int entYear = 0;
		String classNum = "";
		String subjectCd = "";
		int no = 0;
		String entYearStr = req.getParameter("f1");
		if (entYearStr == null) {
			entYearStr =
				(String)req.getAttribute("f1");
		}

		classNum = req.getParameter("f2");
		if (classNum == null) {
			classNum =
				(String)req.getAttribute("f2");
		}

		subjectCd = req.getParameter("f3");
		if (subjectCd == null) {
			subjectCd =
				(String)req.getAttribute("f3");
		}

		String noStr = req.getParameter("f4");
		if (noStr == null) {
			Object obj =
				req.getAttribute("f4");
			if (obj != null) {
				noStr = obj.toString();
			}
		}

		// 数値変換
		if (entYearStr != null &&! entYearStr.equals("")) {
			entYear =Integer.parseInt(entYearStr);
		}

		if (noStr != null &&! noStr.equals("")) {
			no = Integer.parseInt(noStr);
		}

		LocalDate todaysDate =LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10;
			i <= year;
			i++) {
			entYearSet.add(i);
		}

		// DAO
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		// 画面表示用
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumDao.filter(teacher.getSchool()));
		req.setAttribute("subject_set", subjectDao.filter(teacher.getSchool()));

		// 検索条件保持
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", no);

		// 検索
		if (entYear != 0 &&
			!classNum.equals("") &&
			!subjectCd.equals("") &&
			no != 0) {
			req.setAttribute("tests",testDao.filter(teacher.getSchool(), entYear, classNum, subjectCd, no));
		}

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}