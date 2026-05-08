package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// DAO
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		// 現在年
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		// 入学年度リスト
		List<Integer> entYearSet = new ArrayList<>();

		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		// クラス一覧
		List<String> classNumSet =classNumDao.filter(teacher.getSchool());

		// 科目一覧
		List<Subject> subjectSet =subjectDao.filter(teacher.getSchool());

		// 回数一覧
		List<Integer> noSet = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			noSet.add(i);
		}

		// 検索条件取得
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("subject_cd");
		String noStr = req.getParameter("no");

		List<Test> testList = null;

		// 全項目入力時のみ検索
		if (entYearStr != null &&
			classNum != null &&
			subjectCd != null &&
			noStr != null &&
			!entYearStr.equals("0") &&
			!classNum.equals("0") &&
			!subjectCd.equals("") &&
			!noStr.equals("")) {

			int entYear = Integer.parseInt(entYearStr);
			int no = Integer.parseInt(noStr);

			testList = testDao.filter(
				teacher.getSchool().getSchoolCd(),
				entYear,
				classNum,
				subjectCd,
				no
			);

			req.setAttribute("test_list", testList);
		}

		// JSPへ渡す
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);
		req.setAttribute("no_set", noSet);

		req.getRequestDispatcher("test_regist.jsp")
			.forward(req, res);
	}
}