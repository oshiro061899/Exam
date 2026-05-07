<%-- 科目登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システムa
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			
			<form action="SubjectCreateExecute.action" method="get">
				<div class="mt-2 text-warning">${errors.get("2") }${errors.get("3") }</div>
				<div>
					<label for="cd">科目コード</label>
					<input class="form-control" type="text" id="cd" name="cd" value="${cd}" required maxlength="3" placeholder="科目コードを入力してください"/>
				</div>
				
				<div>
					<label for="name">科目名</label>
					<input class="form-control" type="text" id="name" name="name" value="${name}" required maxlength="30" placeholder="科目名を入力してください"/>
				</div>
				
				<div class="mx-auto py-2">
					<button type="submit" class="btn btn-primary" id="create-button" name="end">登録</button>
				</div>
			</form>
			
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>