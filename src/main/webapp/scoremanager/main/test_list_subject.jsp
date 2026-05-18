<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="content">
	    <section class="me-4">
	        <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
	
	        <%-- 検索フィルターエリア --%>
	        <div class="row border mx-3 mb-3 py-3 align-items-center rounded" id="filter">
	            
	            <%-- A：科目情報検索 --%>
	            <form action="TestListSubject.action" method="get" class="col-12 mb-4">
	                <div class="row align-items-center">
	                    <div class="col-2 text-center">科目情報</div>
	                    <div class="col-2">
	                        <label class="form-label">入学年度</label>
	                        <select name="f1" class="form-select">
	                            <option value="0">--------</option>
	                            <c:forEach var="year" items="${ent_year_set}">
	                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
	                            </c:forEach>
	                        </select>
	                    </div>
	                    <div class="col-2">
	                        <label class="form-label">クラス</label>
	                        <select name="f2" class="form-select">
	                            <option value="0">--------</option>
	                            <c:forEach var="cNum" items="${class_num_set}">
	                                <option value="${cNum}" <c:if test="${cNum == f2}">selected</c:if>>${cNum}</option>
	                            </c:forEach>
	                        </select>
	                    </div>
	                    <div class="col-4">
	                        <label class="form-label">科目</label>
	                        <select name="f3" class="form-select">
	                            <option value="0">--------</option>
	                            <c:forEach var="sub" items="${subjects}">
	                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
	                            </c:forEach>
	                        </select>
	                    </div>
	                    <div class="col-2 text-center">
	                        <button type="submit" class="btn btn-secondary mt-4">検索</button>
	                    </div>
	                </div>
	            </form>
	
	            <%-- 区切り線 --%>
	            <div class="col-12"><hr class="my-2"></div>
	
	            <%-- B：学生情報検索 --%>
	            <form action="TestListStudent.action" method="get" class="col-12 mt-3">
	                <div class="row align-items-center">
	                    <div class="col-2 text-center">学生情報</div>
	                    <div class="col-8">
	                        <label class="form-label">学生番号</label>
	                        <input type="text" name="f4" class="form-control" placeholder="学生番号を入力してください" value="${f4}">
	                    </div>
	                    <div class="col-2 text-center">
	                        <button type="submit" class="btn btn-secondary mt-4">検索</button>
	                    </div>
	                </div>
	            </form>
	        </div>
	
	        <%-- 結果表示エリア --%>
	        <div class="mx-3 mt-4">
        		<%-- エラー表示 --%>
                <c:if test="${not empty search_error}">
                    <p class="mt-2 mb-0"">
                        ${search_error}
                    </p>
                </c:if>
	            <c:if test="${not empty scoreMap}">
	                <div class="mb-2" id="subject-info">科目：${subject.name}</div>
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
	            </c:if>
	        </div>
	    </section>
	</c:param>
</c:import>