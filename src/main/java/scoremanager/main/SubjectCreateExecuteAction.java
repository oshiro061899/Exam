package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッション
		HttpSession session = req.getSession();

		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 入力値
		String cd = "";
		String name = "";

		// Bean
		Subject subject = new Subject();

		// Dao
		SubjectDao subjectDao = new SubjectDao();

		// エラー格納
		Map<String, String> errors = new HashMap<>();

		// パラメータ取得
		cd = req.getParameter("cd");
		name = req.getParameter("name");

		// バリデーション

		// 科目コード未入力
		if (cd == null || cd.isEmpty()) {

			errors.put("1", "科目コードを入力してください");

		// 3文字以外
		} else if (cd.length() != 3) {

			errors.put("2", "科目コードは3文字で入力してください");

		// 重複チェック
		} else if (subjectDao.get(cd, teacher.getSchool()) != null) {

			errors.put("3", "科目コードが重複しています");
		}

		// 科目名未入力
		if (name == null || name.isEmpty()) {

			errors.put("4", "科目名を入力してください");
		}

		// エラーなし
		if (errors.isEmpty()) {

			// Subjectにセット
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			// 保存
			subjectDao.save(subject);
		}

		// 入力値保持
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		// エラー保持
		req.setAttribute("errors", errors);

		// 遷移
		if (errors.isEmpty()) {

			// 完了画面
			req.getRequestDispatcher("subject_create_done.jsp")
				.forward(req, res);

		} else {

			// 登録画面へ戻る
			req.getRequestDispatcher("subject_create.jsp")
				.forward(req, res);
		}
	}
}