package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータの取得（検索ボタンが押されたとき用）
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス番号
        String f3 = req.getParameter("f3"); // 科目コード
        String f4 = req.getParameter("f4"); // 学生番号

        // 2. 検索用のDAOを準備
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        StudentDao stDao = new StudentDao();

        // 3. 画面のプルダウン等の初期表示用データをセット
        // 入学年度リスト（現在の年から10年前までなど）
        List<Integer> entYearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }
        // クラス一覧、科目一覧を取得
        List<String> classList = cDao.filter(teacher.getSchool());
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // 4. 検索処理
        if (f4 != null && !f4.isEmpty()) {
            // 【学生番号検索】(画像 ⑬ の検索ボタン押下時)
            TestListStudentDao tlsDao = new TestListStudentDao();
            Student student = stDao.get(f4); // 学生情報を取得
            if (student != null) {
                List<Test> tests = tlsDao.filter(student);
                req.setAttribute("tests", tests); // 学生用成績リスト
                req.setAttribute("student", student); // 氏名表示用
            }
        } else if (f1 != null && f2 != null && f3 != null && !f3.equals("0")) {
            // 【科目検索】(画像 ⑨ の検索ボタン押下時)
            TestListSubjectDao tljDao = new TestListSubjectDao();
            Subject subject = sDao.get(f3, teacher.getSchool());
            // 先ほど作ったクラス別DAOを使って、1行まとめ形式で取得
            List<TestListSubject> tests = tljDao.filter(teacher.getSchool(), Integer.parseInt(f1), f2, subject);
            
            req.setAttribute("tests", tests); // クラス別成績リスト
            req.setAttribute("subject", subject); // 表示用
        }

        // 5. レスポンス値をセット（JSPでの再表示用）
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);
        req.setAttribute("ent_year_set", entYearList);
        req.setAttribute("class_num_set", classList);
        req.setAttribute("subjects", subjects);

        // 6. 成績参照JSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}