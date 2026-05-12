package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

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
	public void execute(HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		// セッション
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		// パラメータ取得
		String[] studentNo = req.getParameterValues("student_no");
		String[] points = req.getParameterValues("point");
		String[] deletes = req.getParameterValues("delete");
		String subjectCd = req.getParameter("subject_cd");
		String classNum = req.getParameter("class_num");
		String entYear = req.getParameter("ent_year");
		int no = Integer.parseInt(req.getParameter("no"));

		// Dao
		TestDao testDao = new TestDao();

		// エラー格納
		Map<String, String> errors = new HashMap<>();

		// 削除が1件でも行われたかを記録するフラグ
		boolean isDeletedAny = false;

		// 登録処理
		for (int i = 0; i < studentNo.length; i++) {
			Test test = new Test();
			
			Student student = new Student();
			student.setStudentNo(studentNo[i]); // 現在のループの学籍番号
			Subject subject = new Subject();
			subject.setCd(subjectCd); // 画面で選択された科目コード
			test.setStudent(student);
			test.setSubject(subject);
			test.setSchool(teacher.getSchool()); // ログインユーザーの学校
			test.setClassNum(classNum); // 画面で選択されたクラス
			test.setNo(no); // 画面で選択された回数

			// 削除チェック
			boolean isDelete = false;
			if (deletes != null) {
				for (String del : deletes) {
					if (del.equals(studentNo[i])) {
						isDelete = true;
						break;
					}
				}
			}

			// 削除処理
			if (isDelete) {
				testDao.delete(test);
				isDeletedAny = true;
				continue;
			}

			// 点数未入力ならスキップ
			if (points[i] == null || points[i].isEmpty()) {
				continue;
			}

			// 点数チェックと保存処理
			try {
				int point = Integer.parseInt(points[i]);
				if (point < 0 || point > 100) {
					errors.put(studentNo[i], "0～100の範囲で入力してください");
				} else {
					test.setPoint(point);
					testDao.save(test);
				}
			} catch (NumberFormatException e) {
				errors.put(studentNo[i], "点数は数字で入力してください");
			}
		}

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", no);
		req.setAttribute("errors", errors);
		
		// 遷移判定
		if (errors.isEmpty()) {
			if (isDeletedAny) {
				// 削除が行われていた場合は削除完了画面へ
				req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
			} else {
			// 通常の登録完了画面
			req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
			}

		} else {
			// エラーがある場合は再検索して入力画面に戻る
			req.setAttribute("tests", testDao.filter(
		    	teacher.getSchool(), Integer.parseInt(entYear), classNum, subjectCd, no
			));
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
		}
	}
}