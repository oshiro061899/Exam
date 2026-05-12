<%-- 成績参照学生別一覧 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <%-- 1. 検索フィルターエリア (検索窓) --%>
            <div class="border mx-3 mb-3 py-3 rounded" id="filter" style="background-color: #fff;">
                
                <%-- 【A：科目情報検索】 --%>
                <form action="TestList.action" method="get" class="px-3 mb-4">
                    <div class="row align-items-end">
                        <div class="col-1 text-nowrap fw-bold mb-2">科目情報</div>
                        <div class="col-3">
                            <label class="form-label" for="student-f1-select">入学年度</label>
                            <select class="form-select" name="f1" id="student-f1-select">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label" for="student-f2-select">クラス</label>
                            <select class="form-select" name="f2" id="student-f2-select">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <label class="form-label" for="subject-select">科目</label>
                            <select class="form-select" name="f3" id="subject-select">
                                <option value="0">--------</option>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary w-100" id="filter-button">検索</button>
                        </div>
                    </div>
                </form>

                <hr class="mx-3">

                <%-- 【B：学生情報検索】 --%>
				<form action="TestStudentList.action" method="get" class="px-3 mt-3">
				    <div class="row align-items-end">
				        <div class="col-1 text-nowrap fw-bold mb-2">学生情報</div>
				        <div class="col-9">
				            <label class="form-label" for="student-f4-input">学生番号</label>
				            <%-- name="f4" を使用（Action側と一致させる） --%>
				            <input type="text" class="form-control" name="f4" id="student-f4-input" 
				                   value="${f4}" placeholder="学生番号を入力してください">
				        </div>
				        <div class="col-2">
				            <button class="btn btn-secondary w-100" id="filter-button">検索</button>
				        </div>
				    </div>
				</form>
            </div>

            <%-- 2. 検索結果表示エリア (検索窓の外・下側に配置) --%>
            <div class="mx-3 mt-4 px-2">
                <c:choose>
                    <%-- ケース1：科目別（クラス単位）の成績表示 --%>
                    <c:when test="${not empty tests && not empty subject}">
                        <div class="mb-3 h5 fw-bold">科目：${subject.name}</div>
                        <table class="table table-hover">
                            <thead class="table-light border-top">
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
                                <c:forEach var="t" items="${tests}">
                                    <tr>
                                        <td>${t.entYear}</td>
                                        <td>${t.classNum}</td>
                                        <td>${t.studentNo}</td>
                                        <td>${t.studentName}</td>
                                        <td class="text-center">${not empty t.getPoint(1) ? t.getPoint(1) : "-"}</td>
                                        <td class="text-center">${not empty t.getPoint(2) ? t.getPoint(2) : "-"}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>

                    <%-- ケース2：学生個人別の成績表示 --%>
                    <c:when test="${not empty tests && not empty student}">
                        <div class="mb-3 h5 fw-bold">氏名：${student.studentName}（${student.studentNo}）</div>
                        <table class="table table-hover">
                            <thead class="table-light border-top">
                                <tr>
                                    <th>科目名</th>
                                    <th>科目コード</th>
                                    <th class="text-center">回数</th>
                                    <th class="text-center">点数</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="t" items="${tests}">
                                    <tr>
                                        <td>${t.subject.name}</td>
                                        <td>${t.subject.cd}</td>
                                        <td class="text-center">${t.no}</td>
                                        <td class="text-center">${t.point}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>

                    <%-- 何も検索されていない、または結果がない状態 --%>
                    <c:otherwise>
                        <div class="mt-4 text-center py-5 border rounded bg-light">
                            <c:choose>
                                <c:when test="${not empty f1 or not empty f4}">
                                    <span class="text-danger">成績情報が存在しませんでした。</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </c:param>
</c:import>