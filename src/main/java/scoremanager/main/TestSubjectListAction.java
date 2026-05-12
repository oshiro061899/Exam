package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestSubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. パラメータ取得（この時点では文字列）
        String schoolCd = request.getParameter("school_cd");
        String entYearStr = request.getParameter("ent_year"); // f1などの名前なら合わせる
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");

        // 2. 数値変換用の変数を準備（初期値0）
        int entYear = 0;
        int no = 0;

        // 3. Nullチェックと変換（ここが修正ポイント！）
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }
        if (noStr != null && !noStr.isEmpty()) {
            no = Integer.parseInt(noStr);
        }

        // 4. 検索条件が揃っている場合のみDAOを呼び出す
        if (entYear != 0 && subjectCd != null) {
            // School オブジェクト作成
            School school = new School();
            school.setSchoolCd(schoolCd);

            // DAO 呼び出し
            TestDao dao = new TestDao();
            List<Test> list = dao.filter(school, entYear, classNum, subjectCd, no);

            // JSP に渡すデータをセット
            request.setAttribute("list", list);
        }

        // 検索条件を保持するためにセット（jspのvalue属性などで使う）
        request.setAttribute("ent_year", entYear);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);
        request.setAttribute("no", no);

        // 5. 表示するJSPを指定して移動する
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}