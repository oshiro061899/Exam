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

public class TestStudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. リクエストパラメータの取得
        String f1Str = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2");    // クラス番号
        String f3 = req.getParameter("f3");    // 科目コード
        String f4 = req.getParameter("f4");    // 学生番号

        // 2. DAOの初期化
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        StudentDao stDao = new StudentDao();

        // 3. プルダウン用データの準備
        // 入学年度リスト（10年前から今年まで：昇順）
        List<Integer> entYearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }
        
        // 学校に紐づくクラス一覧と科目一覧を取得
        List<String> classList = cDao.filter(teacher.getSchool());
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // 4. 検索ロジックの実行
        if (f4 != null && !f4.isEmpty()) {
            // --- 【パターンA：学生番号検索】 ---
            TestListStudentDao tlsDao = new TestListStudentDao();
            Student student = stDao.get(f4); // 学生情報を取得
            
            if (student != null) {
                // その学生の全成績（科目名入り）を取得
                List<Test> tests = tlsDao.filter(student);
                req.setAttribute("tests", tests);
                req.setAttribute("student", student);
            }
        } else if (f1Str != null && !f1Str.equals("0") && f2 != null && !f2.equals("0") && f3 != null && !f3.equals("0")) {
            // --- 【パターンB：科目・クラス検索】 ---
            int entYear = Integer.parseInt(f1Str);
            TestListSubjectDao tljDao = new TestListSubjectDao();
            Subject subject = sDao.get(f3, teacher.getSchool());
            
            // 指定したクラス・科目の成績一覧（1回目・2回目横並び）を取得
            List<TestListSubject> tests = tljDao.filter(teacher.getSchool(), entYear, f2, subject);
            
            req.setAttribute("tests", tests);
            req.setAttribute("subject", subject);
        }

        // 5. JSPへ渡す値をセット
        req.setAttribute("f1", f1Str);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);
        req.setAttribute("ent_year_set", entYearList);
        req.setAttribute("class_num_set", classList);
        req.setAttribute("subjects", subjects);

        // 6. JSPへフォワード
        req.getRequestDispatcher("test_student_list.jsp").forward(req, res);
    }
}