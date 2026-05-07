<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- ベースとなるデザイン（ヘッダーなど）を読み込む場合はここに記載 --%>

<section class="me-4">
    <h2 class="h3 mb-3 fw-norma">科目削除</h2>
    
    <div class="bg-light p-3 mb-3">
        <p>以下の科目を削除します。大丈夫ですか？</p>
        <hr>
        <div class="row">
            <div class="col-2">科目コード</div>
            <div class="col-10">${subject.cd}</div>
        </div>
        <div class="row">
            <div class="col-2">科目名</div>
            <div class="col-10">${subject.name}</div>
        </div>
    </div>

    <%-- 削除実行サーブレットへ送るフォーム --%>
    <form action="SubjectDeleteExecute.action" method="post">
        <%-- どの科目を消すか特定するために、hiddenでコードを送る --%>
        <input type="hidden" name="cd" value="${subject.cd}">
        
        <button type="submit" class="btn btn-danger">削除</button>
        <a href="SubjectList.action" class="btn btn-secondary">キャンセル</a>
    </form>
</section>