<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
            <form action="SubjectUpdateExecute.action" method="post">
                <div class="mb-3">
                    <label class="form-label">科目コード</label>
                    <%-- コードは変更不可にするため、表示のみ。値はhiddenで送る --%>
                    <p class="form-control-plaintext px-3">${subject.cd}</p>
                    <input type="hidden" name="cd" value="${subject.cd}">
                </div>
                
                <div class="mb-3">
                    <label for="subject-name-input" class="form-label">科目名</label>
                    <input type="text" class="form-control" id="subject-name-input" name="name" 
                           value="${subject.name}" placeholder="科目名を入力してください" required>
                </div>
                
                <button type="submit" class="btn btn-primary">変更を保存する</button>
                <a href="SubjectList.action" class="btn btn-secondary">キャンセル</a>
            </form>
        </section>
    </c:param>
</c:import>