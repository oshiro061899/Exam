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
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		List<Integer> entYearSet = new ArrayList<>();
		int entYear = 0;
		int no = 0;
		
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");
		String noStr = req.getParameter("f4");

		boolean isSearch = (entYearStr != null || classNum != null || subjectCd != null || noStr != null);
		
		if (isSearch) {
			// いずれかが null または 空文字 の場合
			if (entYearStr == null || entYearStr.isEmpty() ||
				classNum == null || classNum.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty() ||
				noStr == null || noStr.isEmpty()) {
				
				req.setAttribute("search_error", "入学年度とクラスと科目と回数を選択してください");
			} else {
				// 数値変換
				entYear = Integer.parseInt(entYearStr);
				no = Integer.parseInt(noStr);
			}
		}
		
			LocalDate todaysDate =LocalDate.now();
			int year = todaysDate.getYear();
			for (int i = year - 10; i <= year; i++) {
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
		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", noStr);

		// 検索
		if (entYear != 0 && classNum != null && !classNum.isEmpty() && 
		        subjectCd != null && !subjectCd.isEmpty() && no != 0) {
			req.setAttribute("tests",testDao.filter(teacher.getSchool(), entYear, classNum, subjectCd, no));
		}

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}