<%-- 成績登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section class="me-4">

			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				成績管理
			</h2>

			<%-- 検索フォーム --%>
			<form action="TestRegist.action" method="get">

				<div class="row border mx-3 mb-3 py-3 align-items-end rounded">

					<%-- 入学年度 --%>
					<div class="col-2">
						<label class="form-label" for="f1">
							入学年度
						</label>

						<select class="form-select" id="f1" name="f1">

							<option value="">----------</option>

							<c:forEach var="year" items="${ent_year_set}">

								<option
									value="${year}"
									<c:if test="${year == f1}">
										selected
									</c:if>
								>
									${year}
								</option>

							</c:forEach>

						</select>
					</div>

					<%-- クラス --%>
					<div class="col-2">

						<label class="form-label" for="f2">
							クラス
						</label>

						<select class="form-select" id="f2" name="f2">

							<option value="">----------</option>

							<c:forEach var="num" items="${class_num_set}">

								<option
									value="${num}"
									<c:if test="${num == f2}">
										selected
									</c:if>
								>
									${num}
								</option>

							</c:forEach>

						</select>
					</div>

					<%-- 科目 --%>
					<div class="col-3">

						<label class="form-label" for="f3">
							科目
						</label>

						<select class="form-select" id="f3" name="f3">

							<option value="">----------</option>

							<c:forEach var="subject" items="${subject_set}">

								<option
									value="${subject.cd}"
									<c:if test="${subject.cd == f3}">
										selected
									</c:if>
								>
									${subject.name}
								</option>

							</c:forEach>

						</select>
					</div>

					<%-- 回数 --%>
					<div class="col-2">

						<label class="form-label" for="f4">
							回数
						</label>

						<select class="form-select" id="f4" name="f4">

							<option value="">----------</option>

							<c:forEach begin="1" end="10" var="count">

								<option
									value="${count}"
									<c:if test="${count == f4}">
										selected
									</c:if>
								>
									${count}
								</option>

							</c:forEach>

						</select>
					</div>

					<%-- 検索ボタン --%>
					<div class="col-2 text-center">

						<button class="btn btn-secondary">
							検索
						</button>

					</div>

				</div>

			</form>

			<%-- 検索結果 --%>
			<c:if test="${tests != null}">

				<div class="mx-3 mb-2">

					科目：
					<c:forEach var="subject" items="${subject_set}">

						<c:if test="${subject.cd == f3}">
							${subject.name}
						</c:if>

					</c:forEach>

					（${f4}回）

				</div>

				<form
					action="TestRegistExecute.action"
					method="post"
				>

					<input
						type="hidden"
						name="subject_cd"
						value="${f3}"
					>

					<input
						type="hidden"
						name="class_num"
						value="${f2}"
					>

					<input
						type="hidden"
						name="no"
						value="${f4}"
					>

					<table class="table table-hover">

						<tr>

							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>

						</tr>

						<c:forEach var="test" items="${tests}">

							<tr>

								<td>${f1}</td>

								<td>${test.classNum}</td>

								<td>

									${test.student.studentNo}

									<input
										type="hidden"
										name="student_no"
										value="${test.student.studentNo}"
									>

								</td>

								<td>
									${test.student.studentName}
								</td>

								<td>

									<input
										type="text"
										name="point"
										value="${test.point}"
										class="form-control"
									>

								</td>

							</tr>

						</c:forEach>

					</table>

					<div class="mt-3">

						<button class="btn btn-secondary">
							登録して終了
						</button>

					</div>

				</form>

			</c:if>
		</section>

	</c:param>

</c:import>