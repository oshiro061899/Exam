<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/base.jsp" %> <!-- 共通ヘッダー等 -->

<section>
    <h2>成績一覧（科目）</h2>

    <form action="TestListSubject.action" method="get" class="mb-4">
        <div class="row g-3 align-items-end">
            <div class="col-md-3">
                <label>入学年度</label>
                <select name="f1" class="form-select">
                    <option value="">選択してください</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <label>クラス</label>
                <select name="f2" class="form-select">
                    <option value="">選択してください</option>
                    <c:forEach var="cNum" items="${class_num_set}">
                        <option value="${cNum}" <c:if test="${cNum == f2}">selected</c:if>>${cNum}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label>科目</label>
                <select name="f3" class="form-select">
                    <option value="">選択してください</option>
                    <c:forEach var="sub" items="${subjects}">
                        <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-secondary">検索</button>
            </div>
        </div>
    </form>

    <c:if test="${not empty tests}">
        <p>科目：${selected_subject.name}</p>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>1回</th>
                    <th>2回</th>
                </tr>
            </thead>
            <tbody>
                <%-- 
                   注意: DAOで学生×回数分データが来るため、
                   本来はMap等で整理すべきですが、ここでは簡易的に表示
                --%>
                <c:forEach var="t" items="${tests}" varStatus="status">
                    <%-- 同じ学生の2行目を飛ばす等の制御が必要な場合はここで行う --%>
                    <tr>
                        <td>${t.student.entYear}</td>
                        <td>${t.student.classNum}</td>
                        <td>${t.student.studentNo}</td>
                        <td>${t.student.studentName}</td>
                        <td>${t.no == 1 ? t.point : '-'}</td>
                        <td>${t.no == 2 ? t.point : '-'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</section>