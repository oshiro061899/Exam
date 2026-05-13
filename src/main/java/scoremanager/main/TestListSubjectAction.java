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

        // 全てのパラメータが揃っている場合のみ検索を実行
        if (entYearStr != null && classNum != null && subjectCd != null && 
            !entYearStr.equals("0") && !classNum.equals("0") && !subjectCd.equals("0")) {
            
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sDao.get(subjectCd, teacher.getSchool());

            if (subject != null) {
                TestListSubjectDao tDao = new TestListSubjectDao();
                
                // --- データ加工ロジック ---
                
                // 1. DAOで全件取得
                List<Test> rawTests = tDao.filter(entYear, classNum, subject);

                // 2. 学生ごとに成績をまとめる（StudentNo -> [第1回の点数, 第2回の点数]）
                Map<String, int[]> scoreMap = new LinkedHashMap<>(); 
                Map<String, Student> studentMap = new LinkedHashMap<>();

                for (Test t : rawTests) {
                    String sNo = t.getStudent().getStudentNo();
                    if (!scoreMap.containsKey(sNo)) {
                        scoreMap.put(sNo, new int[]{-1, -1}); // 初期値 -1 (未受験)
                        studentMap.put(sNo, t.getStudent());
                    }
                    
                    // 第1回なら配列の0番目、第2回なら1番目に点数をセット
                    if (t.getNo() == 1) scoreMap.get(sNo)[0] = t.getPoint();
                    if (t.getNo() == 2) scoreMap.get(sNo)[1] = t.getPoint();
                }

                // 3. JSPへ渡す
                req.setAttribute("scoreMap", scoreMap);
                req.setAttribute("studentMap", studentMap);
                req.setAttribute("subject", subject); // 表示用の科目名
            }
        }

        // 入力値を保持
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        // フォワード先を test_list.jsp に合わせる（以前提示されたファイル名）
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}