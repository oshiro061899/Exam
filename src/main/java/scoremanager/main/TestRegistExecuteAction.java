package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        String[] studentNo = req.getParameterValues("student_no");
        String[] points = req.getParameterValues("point");
        String[] deletes = req.getParameterValues("delete");
        String subjectCd = req.getParameter("subject_cd");
        String classNum = req.getParameter("class_num");
        String entYear = req.getParameter("ent_year");
        int no = Integer.parseInt(req.getParameter("no"));

        TestDao testDao = new TestDao();
        Map<String, String> errors = new HashMap<>();
        boolean isDeletedAny = false;

        // 登録・削除ループ処理
        for (int i = 0; i < studentNo.length; i++) {
            Test test = new Test();
            Student student = new Student();
            student.setStudentNo(studentNo[i]);
            Subject subject = new Subject();
            subject.setCd(subjectCd);
            
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(teacher.getSchool());
            test.setClassNum(classNum);
            test.setNo(no);

            // 削除チェックボックスの判定
            boolean isDelete = false;
            if (deletes != null) {
                for (String del : deletes) {
                    if (del.equals(studentNo[i])) {
                        isDelete = true;
                        break;
                    }
                }
            }

            try {
                if (isDelete) {
                    // --- 削除処理 ---
                    testDao.delete(test);
                    isDeletedAny = true; 
                } else {
                    // --- 登録・更新処理 ---
                    if (points[i] != null && !points[i].isEmpty()) {
                        int point = Integer.parseInt(points[i]);
                        if (point < 0 || point > 100) {
                            errors.put(studentNo[i], "0～100の範囲で入力してください");
                        } else {
                            test.setPoint(point);
                            testDao.save(test);
                        }
                    }
                    // 点数が空の場合は何もしない（スキップ）
                }
            } catch (NumberFormatException e) {
                errors.put(studentNo[i], "点数は数字で入力してください");
            }
        } // ここでループ終了

        // JSPへ戻すパラメータのセット
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", no);
        req.setAttribute("errors", errors);
        
        if (errors.isEmpty()) {
            if (isDeletedAny) {
                req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
            } else {
                req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
            }
        } else {
            // エラー時の再表示用データ作成
            ClassNumDao classNumDao = new ClassNumDao();
            SubjectDao subjectDao = new SubjectDao();
            List<Integer> entYearSet = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for (int j = year - 10; j <= year; j++) {
                entYearSet.add(j);
            }
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("class_num_set", classNumDao.filter(teacher.getSchool()));
            req.setAttribute("subject_set", subjectDao.filter(teacher.getSchool()));
            req.setAttribute("tests", testDao.filter(teacher.getSchool(), Integer.parseInt(entYear), classNum, subjectCd, no));

            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
        }
    }
}