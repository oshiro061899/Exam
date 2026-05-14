package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. プルダウンデータの準備
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();
        
        List<Integer> entYearSet = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) entYearSet.add(i);

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", cDao.filter(teacher.getSchool()));
        req.setAttribute("subjects", sDao.filter(teacher.getSchool()));

        // 2. 検索パラメータ取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // 3. 検索処理
        if (entYearStr != null || classNum != null || subjectCd != null) {
            // いずれかが未選択の場合のチェック
            if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0") || 
                classNum == null || classNum.isEmpty() || classNum.equals("0") || 
                subjectCd == null || subjectCd.isEmpty() || subjectCd.equals("0")) {
                
                req.setAttribute("filter_error", "入学年度とクラスと科目を選択してください");
                req.getRequestDispatcher("test_list.jsp").forward(req, res);
                
            } else {
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = sDao.get(subjectCd, teacher.getSchool());

                if (subject != null) {
                    TestListSubjectDao tDao = new TestListSubjectDao();
                    List<Test> rawTests = tDao.filter(entYear, classNum, subject);

                    // 結果が空の場合のエラーメッセージ
                    if (rawTests == null || rawTests.isEmpty()) {
                        req.setAttribute("search_error", "学生情報が存在しませんでした。");
                    } else {
                        // データがある場合はMapに加工
                        Map<String, int[]> scoreMap = new LinkedHashMap<>(); 
                        Map<String, Student> studentMap = new LinkedHashMap<>();

                        for (Test t : rawTests) {
                            String sNo = t.getStudent().getStudentNo();
                            if (!scoreMap.containsKey(sNo)) {
                                scoreMap.put(sNo, new int[]{-1, -1});
                                studentMap.put(sNo, t.getStudent());
                            }
                            if (t.getNo() == 1) scoreMap.get(sNo)[0] = t.getPoint();
                            if (t.getNo() == 2) scoreMap.get(sNo)[1] = t.getPoint();
                        }

                        req.setAttribute("scoreMap", scoreMap);
                        req.setAttribute("studentMap", studentMap);
                        req.setAttribute("subject", subject);
                    }
                }
            }
        }

        // 4. 入力値の保持と画面遷移
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}