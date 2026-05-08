package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. ローカル変数の初期化
        List<Subject> subjects = new ArrayList<>(); // 空のリストを作成
        SubjectDao subjectDao = new SubjectDao();

        // 2. 既存の get メソッドを使ってデータを取得する
        // ※ 本来は一覧取得が必要ですが、既存の get(String, School) を呼ぶ形にします。
        // テスト用に、科目コード "A001" などを直接指定して1件だけ取得してみる例です。
        Subject subject = subjectDao.get("A001", teacher.getSchool());
        
        if (subject != null) {
            subjects.add(subject); // 取得できたらリストに追加
        }

        // 3. レスポンス値をセット
        req.setAttribute("subjects", subjects);

        // 4. JSPへフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}