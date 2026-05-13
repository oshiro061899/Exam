package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
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

        // 1. パラメータの取得
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス番号
        String f3 = req.getParameter("f3"); // 科目コード
        String f4 = req.getParameter("f4"); // 学生番号

        // 2. 共通DAOの準備
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        StudentDao stDao = new StudentDao();

        // 3. 画面表示用データの作成（プルダウン等）
        List<Integer> entYearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) { // 過去から現在の順
            entYearList.add(i);
        }
        List<String> classList = cDao.filter(teacher.getSchool());
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // 4. 検索処理の分岐
        if (f4 != null && !f4.isEmpty()) {
            // 【学生番号検索】(個人単位)
            TestListStudentDao tlsDao = new TestListStudentDao();
            Student student = stDao.get(f4);
            if (student != null) {
                // DAO内で科目情報をJOINしたリストを取得
                List<Test> tests = tlsDao.filter(student);
                req.setAttribute("tests", tests);     // 科目名、コード、回数、点数が入ったリスト
                req.setAttribute("student", student); // 氏名表示用
            } else if (f1 != null && f2 != null && f3 != null && !f3.equals("0") && !f3.isEmpty()) {
	            // 【科目検索】(クラス単位)
	            TestListSubjectDao tljDao = new TestListSubjectDao();
	            Subject subject = sDao.get(f3, teacher.getSchool());
	            
	            if (subject != null) {
	                // クラス別の成績リストを取得
	                List<Test> tests = tljDao.filter(Integer.parseInt(f1), f2, subject);
	                req.setAttribute("tests", tests);
	                req.setAttribute("subject", subject);
	            } else {
	                // 科目が存在しない場合などのエラー処理（任意）
	                req.setAttribute("error", "指定された科目が見つかりません。");
	            }
	        }
        }

        // 5. レスポンス値をセット（検索条件の保持とプルダウン用）
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);
        req.setAttribute("ent_year_set", entYearList);
        req.setAttribute("class_num_set", classList);
        req.setAttribute("subjects", subjects);

        // 6. フォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}

