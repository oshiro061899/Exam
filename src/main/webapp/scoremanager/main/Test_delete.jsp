<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section>
    <h2>成績削除</h2>
    <div class="alert alert-danger">以下の成績を削除します。よろしいですか？</div>
    
    <table class="table">
        <tr><th>学生番号</th><td>${test.student.studentNo}</td></tr>
        <tr><th>氏名</th><td>${test.student.studentName}</td></tr>
        <tr><th>科目</th><td>${test.subject.name}</td></tr>
        <tr><th>回数</th><td>第${test.no}回</td></tr>
        <tr><th>点数</th><td>${test.point}点</td></tr>
    </table>

    <form action="TestDeleteExecute.action" method="post">
        <%-- 削除の特定に必要な3つの情報をhiddenで送る --%>
        <input type="hidden" name="student_no" value="${test.student.studentNo}">
        <input type="hidden" name="subject_cd" value="${test.subject.cd}">
        <input type="hidden" name="no" value="${test.no}">
        
        <button type="submit" class="btn btn-danger">削除実行</button>
        <a href="TestList.action" class="btn btn-secondary">キャンセル</a>
    </form>
</section>