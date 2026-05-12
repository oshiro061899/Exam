<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section>
    <h2>学生別成績詳細</h2>

    <form action="TestListStudent.action" method="get" class="mb-4">
        <div class="row align-items-end">
            <div class="col-4">
                <label>学生番号</label>
                <input type="text" name="f1" class="form-control" value="${param.f1}" placeholder="例: 2210001">
            </div>
            <div class="col-2">
                <button type="submit" class="btn btn-primary">検索</button>
            </div>
        </div>
    </form>

    <c:if test="${not empty student}">
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">${student.studentName} さん (${student.studentNo})</h5>
                <p class="card-text">${student.entYear}年度入学 / ${student.classNum}クラス</p>
            </div>
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>科目名</th>
                    <th>回数</th>
                    <th>点数</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${tests}">
                    <tr>
                        <td>${t.subject.name}</td>
                        <td>第${t.no}回</td>
                        <td>${t.point}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</section>