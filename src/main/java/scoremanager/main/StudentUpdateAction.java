package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからユーザー（教員）情報を取得（所属校のクラス取得に必要）
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータ（学生番号）を取得
        String studentNo = req.getParameter("no");

        // 2. DAOを使ってDBから学生情報を取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(studentNo);

        // --- 選択肢の準備（登録画面と同様の処理が必要） ---
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        
        // 入学年度の選択肢（過去10年分など）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // クラス番号の選択肢をDBから取得
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(teacher.getSchool());
        // ----------------------------------------------

        // 3. リクエストにデータをセット
        req.setAttribute("student", student);      // 現在の学生情報
        req.setAttribute("ent_year_set", entYearSet); // 入学年度のリスト
        req.setAttribute("class_num_set", classNumSet); // クラスのリスト

        // 4. 更新画面（JSP）へフォワード
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}