package scoremanager.main;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestSubjectListExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. パラメータの取得（科目、回数、学生リスト、点数リスト）
        String subjectCd = req.getParameter("subject_cd");
        String numStr = req.getParameter("no");
        String[] studentNos = req.getParameterValues("student_no");
        String[] points = req.getParameterValues("point");

        // 3. 数値変換とDAOの準備
        int num = 0;
        if (numStr != null && !numStr.isEmpty()) {
            num = Integer.parseInt(numStr);
        }

        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, teacher.getSchool());

        // 4. 更新処理
        if (studentNos != null && points != null) {
            for (int i = 0; i < studentNos.length; i++) {
                Test test = new Test();
                test.setSchool(teacher.getSchool());
                test.setSubject(subject);
                test.setNo(num);

                // 学生情報をセット
                Student student = new Student();
                student.setStudentNo(studentNos[i]);
                test.setStudent(student);

                // 点数のセット（空文字チェック）
                if (points[i] != null && !points[i].isEmpty()) {
                    test.setPoint(Integer.parseInt(points[i]));
                } else {
                    test.setPoint(-1); // 未入力などのルールに合わせて変更してください
                }

                // 保存実行（TestDaoのsaveメソッドを呼ぶ）
                tDao.save(test);
            }
        }

        // 5. 完了後に一覧画面へ戻る（または完了ページへ）
        // 検索条件を保持したまま戻るためにリダイレクトではなくフォワードを使用
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}