<%-- 科目削除完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>
			
			<div id="wrap_box">
				<%-- 完了メッセージ：登録・変更に合わせて緑系の背景 --%>
				<p class="text-center py-2" style="background-color:#8cc3a9">削除が完了しました</p>
				<br>
				<a href="SubjectList.action">科目一覧</a>
			</div>
			</div>
		</section>
	</c:param>
</c:import>