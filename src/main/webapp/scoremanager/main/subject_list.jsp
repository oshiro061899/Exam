<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			
			<%-- 
				※元の学生JSPにあった「入学年度」「クラス」「在学中」の絞り込みは、
				Subjectクラスのプロパティに存在しないため削除しています。
				必要に応じて「科目名検索」などを追加してください。
			 --%>

			<c:choose>
				<c:when test="${subjects.size() > 0 }">
					<div>検索結果：${subjects.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
							<th>科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="subject" items="${subjects }">
							<tr>
								<%-- Beanの getCd() と getName() に対応 --%>
								<td><c:out value="${subject.cd }" /></td>
								<td><c:out value="${subject.name }" /></td>
								
								<%-- 変更・削除リンク（主キーである科目コードをパラメーターとして渡す） --%>
								<td><a href="SubjectUpdate.action?cd=${subject.cd }">変更</a></td>
								<td><a href="SubjectDelete.action?cd=${subject.cd }">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>科目情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>