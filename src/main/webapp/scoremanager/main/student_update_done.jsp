<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<p class="text-center" style="background-color:#66CC99">変更が完了しました</p>
			<div class="row mt-3">
					<div class="col-12 px-4">
						<a href="StudentList.action" class="text-decoration-none">学生一覧</a>
					</div>
					
					
			</div>
		</section>
	</c:param>
</c:import>