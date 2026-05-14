<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<%-- 検索フィルターエリア --%>
			<div class="row border mx-3 mb-3 py-3 align-items-center rounded" id="filter">
				
				<%-- 【A：科目情報検索】 --%>
				<form action="TestListSubject.action" method="get" class="col-12 mb-4">
					<div class="row align-items-center">
						<div class="col-2 text-center">科目情報</div>
						<div class="col-2">
							<label class="form-label" for="f1">入学年度</label>
							<select class="form-select" name="f1" id="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-2">
							<label class="form-label" for="f2">クラス</label>
							<select class="form-select" name="f2" id="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-4">
							<label class="form-label" for="f3">科目</label>
							<select class="form-select" name="f3" id="f3">
								<option value="0">--------</option>
								<c:forEach var="sub" items="${subjects}">
									<option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-2 text-center">
							<button class="btn btn-secondary mt-4" id="filter-button">検索</button>
						</div>
					</div>
					<c:if test="${not empty filter_error}">
				        <div class="row mt-2">
				            <div class="col-2"></div>
				            <div class="col-10">
				                <small style="color: #f39800;">${filter_error}</small>
				            </div>
				        </div>
					</c:if>
				</form>

				<%-- 横線 --%>
				<div class="col-12"><hr class="my-2"></div>

				<%-- 【B：学生情報検索】 --%>
				<form action="TestListStudent.action" method="get" class="col-12 mt-3">
					<div class="row align-items-center">
						<div class="col-2 text-center">学生情報</div>
						<div class="col-8">
							<label class="form-label" for="f4">学生番号</label>
							<input type="text" class="form-control" name="f4" id="f4" 
								   value="${f4}" placeholder="学生番号を入力してください" required>
						</div>
						<div class="col-2 text-center">
							<button class="btn btn-secondary mt-4" id="filter-button">検索</button>
						</div>
					</div>
				</form>
			</div>

			<%-- 結果表示エリア --%>
			<div class="mx-3 mt-4">
				<c:choose>
					<%-- ケース1：科目別（クラス単位）の成績表示 --%>
					<c:when test="${not empty scoreMap}">
						<div class="mb-2 fw-bold">科目：${subject.name}</div>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>入学年度</th>
									<th>クラス</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th class="text-center">1回</th>
									<th class="text-center">2回</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="entry" items="${scoreMap}">
									<c:set var="sNo" value="${entry.key}" />
									<c:set var="points" value="${entry.value}" />
									<c:set var="student" value="${studentMap[sNo]}" />
									<tr>
										<td>${student.entYear}</td>
										<td>${student.classNum}</td>
										<td>${sNo}</td>
										<td>${student.studentName}</td>
										<td class="text-center">${points[0] != -1 ? points[0] : "-"}</td>
										<td class="text-center">${points[1] != -1 ? points[1] : "-"}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>

					<%-- ケース2：学生個人別の成績表示 --%>
					<c:when test="${not empty student}">
						<div class="mb-2">
							<span>氏名：${student.studentName}（${student.studentNo}）</span>
						</div>
						<c:choose>
							<c:when test="${not empty tests}">
								<table class="table table-hover">
									<thead>
										<tr>
											<th class="w-40">科目名</th>
											<th class="text-end">科目コード</th>
											<th class="text-center">回数</th>
											<th class="text-center">点数</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="t" items="${tests}">
											<tr>
												<td>${t.subject.name}</td>
												<td class="text-end">${t.subject.cd}</td>
												<td class="text-center">${t.no}</td>
												<td class="text-center">${t.point}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								<div class="mt-2">成績情報が存在しませんでした</div>
							</c:otherwise>
						</c:choose>
					</c:when>

					<%-- 何も検索されていない初期状態 --%>
					<c:otherwise>
						<div class="mt-3 fw-bold" style="color: #009edb;">
							科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</section>
	</c:param>
</c:import>