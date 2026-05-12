<%-- 科目別成績一覧・登録 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理（科目別）</h2>

            <%-- 1. 検索条件エリア --%>
            <div class="border mx-3 mb-3 py-3 rounded" id="filter" style="background-color: #fff;">
                <form action="TestSubjectList.action" method="get" class="px-3">
                    <div class="row align-items-end">
                        <div class="col-3">
                            <label class="form-label">入学年度</label>
                            <select class="form-select" name="ent_year">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label">クラス</label>
                            <select class="form-select" name="class_num">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <select class="form-select" name="subject_cd">
                                <option value="0">--------</option>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.cd}" <c:if test="${sub.cd == subject_cd}">selected</c:if>>${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label">回数</label>
                            <select class="form-select" name="no">
                                <option value="1" <c:if test="${no == 1}">selected</c:if>>1回目</option>
                                <option value="2" <c:if test="${no == 2}">selected</c:if>>2回目</option>
                            </select>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary w-100">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <%-- 2. 成績表示・入力エリア --%>
            <div class="mx-3 mt-4 px-2">
                <c:choose>
                    <c:when test="${not empty list}">
                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <span class="h5 fw-bold">科目：${list[0].subject.name} （${no}回目）</span>
                        </div>
                        
                        <%-- 実行Actionへの送信フォーム --%>
                        <form action="TestSubjectListExecute.action" method="post">
                            <%-- 検索条件を隠しデータで送信 --%>
                            <input type="hidden" name="subject_cd" value="${subject_cd}">
                            <input type="hidden" name="no" value="${no}">

                            <table class="table table-hover">
                                <thead class="table-light border-top">
                                    <tr>
                                        <th>入学年度</th>
                                        <th>クラス</th>
                                        <th>学生番号</th>
                                        <th>氏名</th>
                                        <th class="text-center" style="width: 150px;">点数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${list}">
                                        <tr>
                                            <td>${t.student.entYear}</td>
                                            <td>${t.student.classNum}</td>
                                            <td>
                                                ${t.student.no}
                                                <input type="hidden" name="student_no" value="${t.student.no}">
                                            </td>
                                            <td>${t.student.name}</td>
                                            <td class="text-center">
                                                <input type="number" name="point" class="form-control text-center" 
                                                       value="${t.point >= 0 ? t.point : ''}" min="0" max="100">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div class="mt-3 text-end">
                                <button type="submit" class="btn btn-primary px-5">登録して保存</button>
                            </div>
                        </form>
                    </c:when>

                    <c:otherwise>
                        <div class="mt-4 text-center py-5 border rounded bg-light">
                            <span class="text-muted">条件を選択して検索してください</span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </c:param>
</c:import>