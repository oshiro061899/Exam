// TestRegistExecuteAction.java
package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

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

		// セッション
		HttpSession session = req.getSession();

		// ログインユーザー取得
		Teacher teacher =
			(Teacher) session.getAttribute("user");

		// パラメータ取得
		String[] studentNo =
			req.getParameterValues("student_no");

		String[] points =
			req.getParameterValues("point");

		String subjectCd =
			req.getParameter("subject_cd");

		String classNum =
			req.getParameter("class_num");

		String entYear =
			req.getParameter("ent_year");

		int no =
			Integer.parseInt(
				req.getParameter("no")
			);

		// Dao
		TestDao testDao = new TestDao();

		// エラー格納
		Map<String, String> errors =
			new HashMap<>();

		// 登録処理
		for (int i = 0; i < studentNo.length; i++) {

			// 未入力ならスキップ
			if (
				points[i] == null ||
				points[i].isEmpty()
			) {
				continue;
			}

			try {

				int point =
					Integer.parseInt(points[i]);

				// 点数チェック
				if (
					point < 0 ||
					point > 100
				) {

					errors.put(
						studentNo[i],
						"0～100の範囲で入力してください"
					);

				} else {

					// Bean作成
					Test test = new Test();

					Student student =
						new Student();

					student.setStudentNo(
						studentNo[i]
					);

					Subject subject =
						new Subject();

					subject.setCd(subjectCd);

					School school =
						teacher.getSchool();

					// Testへセット
					test.setStudent(student);

					test.setSubject(subject);

					test.setSchool(school);

					test.setClassNum(classNum);

					test.setNo(no);

					test.setPoint(point);

					// 保存
					testDao.save(test);
				}

			} catch (NumberFormatException e) {

				errors.put(
					studentNo[i],
					"点数は数字で入力してください"
				);
			}
		}

		// 入力値保持
		req.setAttribute("f1", entYear);

		req.setAttribute("f2", classNum);

		req.setAttribute("f3", subjectCd);

		req.setAttribute("f4", no);

		// エラー保持
		req.setAttribute("errors", errors);

		// 遷移
		if (errors.isEmpty()) {

			// 完了画面
			req.getRequestDispatcher(
				"test_regist_done.jsp"
			).forward(req, res);

		} else {

			// 再検索結果を設定
			req.setAttribute(
				"tests",
				testDao.filter(
					teacher.getSchool(),
					Integer.parseInt(entYear),
					classNum,
					subjectCd,
					no
				)
			);

			// 登録画面へ戻る
			req.getRequestDispatcher(
				"test_regist.jsp"
			).forward(req, res);
		}
	}
}
