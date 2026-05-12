<%-- 成績登録JSP --%>
<%-- 成績削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>
	<c:param name="content">
		<div id="wrap_box">
			<section class="me-4">

				<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
					成績管理
				</h2>
				
				<c:if test="${not empty search_error}">
					<div class="text-warning small mb-1">
						${search_error}
					</div>
				</c:if>

				<%-- 検索フォーム --%>
				<form action="TestRegist.action" method="get">
					<div class="row border mx-3 mb-3 py-3 align-items-end rounded">
						<div class="col-2">
						
							<label class="form-label" for="f1">入学年度</label>
							<select class="form-select" id="f1" name="f1">
								<option value="">----------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-2">
							<label class="form-label" for="f2">クラス</label>
							<select class="form-select" id="f2" name="f2">
								<option value="">----------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-3">
							<label class="form-label" for="f3">科目</label>
							<select class="form-select" id="f3" name="f3">
								<option value="">----------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-2">
							<label class="form-label" for="f4">回数</label>
							<select class="form-select" id="f4" name="f4">
								<option value="">----------</option>
								<c:forEach begin="1" end="10" var="count">
									<option value="${count}" <c:if test="${count == f4}">selected</c:if>>${count}</option>
								</c:forEach>
							</select>
							
						</div>
						<div class="col-2 text-center">
							<button class="btn btn-secondary">検索</button>
						</div>
					</div>
				</form>
	
				<%-- 検索結果 --%>
				<c:if test="${tests != null}">
					<div class="mx-3 mb-2">
						科目：
						<c:forEach var="subject" items="${subject_set}">
							<c:if test="${subject.cd == f3}">${subject.name}</c:if>
						</c:forEach>
						（${f4}回）
					</div>
	
					<form action="TestRegistExecute.action" method="post">
						<input type="hidden" name="ent_year" value="${f1}">
						<input type="hidden" name="subject_cd" value="${f3}">
						<input type="hidden" name="class_num" value="${f2}">
						<input type="hidden" name="no" value="${f4}">
	
						<table class="table table-hover">
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
							    <th style="width: 200px;">点数 / 削除</th>
							</tr>
							<c:forEach var="test" items="${tests}">
								<tr>
									<td>${f1}</td>
									<td>${test.classNum}</td>
									<td>
										 ${test.student.studentNo}
										<input type="hidden" name="student_no" value="${test.student.studentNo}">
									</td>
									<td>${test.student.studentName}</td>
									<td>
										<%-- エラーメッセージ表示 --%>
										<div class="text-warning small mb-1">
											${errors[test.student.studentNo]}
										</div>

										<div class="d-flex align-items-center gap-2">
											<%-- 点数入力 --%>
											<input type="text" name="point" 
												value="${test.no > 0 ? test.point : ''}"
												class="form-control form-control-sm" style="width: 70px;">
										
											<%-- 削除ボタン --%>
											<div class="btn-group-toggle">
												<label class="btn btn-sm btn-outline-danger py-1 px-2" style="font-size: 0.8rem;">
													<input type="checkbox" name="delete" 
														value="${test.student.studentNo}" 
														style="display:none;" 
														onchange="this.parentElement.classList.toggle('active'); this.parentElement.classList.toggle('btn-danger'); this.parentElement.classList.toggle('btn-outline-danger');">
														削除
												</label>
											</div>
										</div>
									</td>
							    </tr>
							</c:forEach>
						</table>
	
						<div class="mt-3">
							<button class="btn btn-secondary">
								登録または削除して終了
							</button>
						</div>
					</form>
				</c:if>
			</section>
		</div>
	</c:param>
</c:import>