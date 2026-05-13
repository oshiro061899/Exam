<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- 共通デザイン（CSS読み込みを含む）をインポート --%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- 背景色をつけて他の画面とデザインを統一 --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>
            
            <div class="border mx-3 mb-3 py-3 rounded bg-white">
                <div class="px-3 mb-4">
                    <p class="text-danger fw-bold">以下の科目を削除します。よろしいですか？</p>
                    <hr>
                    <div class="row mb-2">
                        <div class="col-2 fw-bold">科目コード</div>
                        <div class="col-10">${subject.cd}</div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-2 fw-bold">科目名</div>
                        <div class="col-10">${subject.name}</div>
                    </div>
                </div>
            </div>

            <%-- 削除実行フォーム --%>
            <div class="mx-3 mt-4">
                <form action="SubjectDeleteExecute.action" method="post">
                    <input type="hidden" name="cd" value="${subject.cd}">
                    
                    <button type="submit" class="btn btn-danger me-2">削除</button>
                    <a href="SubjectList.action" class="btn btn-secondary">キャンセル</a>
                </form>
            </div>
        </section>
    </c:param>
</c:import>