<%-- エラーJSP --%>
<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		エラーページ
	</c:param>

	<c:param name="content">
		<div id="wrap_box" class="me-4">
			<section class="me-4">
				<div class="mx-3" style="min-height: 400px;">
					<p>エラーが発生しました</p>
				</div>
			</section>
		</div>
	</c:param>
</c:import>

