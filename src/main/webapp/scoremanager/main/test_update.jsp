<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績変更</h2>
            
            <form action="TestUpdateExecute.action" method="post">
                <div class="row mb-3">
                    <div class="col-3">学籍番号: ${test.student.studentNo}</div>
                    <div class="col-3">氏名: ${test.student.studentName}</div>
                    <div class="col-3">科目: ${test.subject.name}</div>
                    <div class="col-3">${test.no}回目</div>
                </div>

                <%-- 更新に必要なキー情報はhiddenで送信 --%>
                <input type="hidden" name="student_no" value="${test.student.studentNo}">
                <input type="hidden" name="subject_cd" value="${test.subject.cd}">
                <input type="hidden" name="num" value="${test.no}">

                <div class="mb-3">
                    <label for="point-input" class="form-label">点数</label>
                    <input type="number" class="form-control" id="point-input" name="point" 
                           value="${test.point}" min="0" max="100" required>
                </div>

                <button type="submit" class="btn btn-primary">変更を保存する</button>
                <a href="TestList.action" class="btn btn-secondary">キャンセル</a>
            </form>
        </section>
    </c:param>
</c:import>