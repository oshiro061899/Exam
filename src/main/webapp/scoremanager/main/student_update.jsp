<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<section class="me-4">
			<%-- No.1 画面タイトル --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<%-- 中略（タイトル部分など） --%>

			<form action="StudentUpdateExecute.action" method="post"> <%-- 更新用のActionへ --%>
			    <div class="container-fluid">
			
			        <%-- 入学年度（表示のみ） --%>
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">入学年度</label>
			                <%-- inputに変更 --%>
			                <input class="form-control" type="text" name="ent_year" value="${student.entYear}" readonly>
			            </div>
			        </div>
			
			        <%-- 学生番号（表示のみ） --%>
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">学生番号</label>
			                <input class="form-control" type="text" name="no" value="${student.studentNo}" readonly>
			            </div>
			        </div>
			
			        <%-- 氏名 --%>
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">氏名</label>
			                <input class="form-control" type="text" name="name" value="${student.studentName}" 
			                       maxlength="30" placeholder="氏名を入力してください" required>
			            </div>
			        </div>
			
			        <%-- クラス --%>
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">クラス</label>
			                <select class="form-select" name="class_num" required>
			                    <c:forEach var="num" items="${class_num_set}">
			                        <%-- 現在のクラスを選択状態にする(selected) --%>
			                        <option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
			                    </c:forEach>
			                </select>
			            </div>
			        </div>
			        
			        <%-- 在学中チェックボックス --%>
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <div class="form-check">
			                    <%-- student_update.jsp --%>
								<input class="form-check-input" type="checkbox" name="is_attend" id="isAttendCheck"
								       <c:if test="${student.attend}">checked</c:if>><%-- 	boolean型の getter	--%>
			                    <label class="form-check-label" for="isAttendCheck">在学中</label>
			                </div>
			            </div>
			        </div>
			        
			        <%-- 変更ボタン --%>
			        <div class="row mt-4">
			            <div class="col-12 px-4">
			                <button type="submit" class="btn btn-primary">変更</button>
			            </div>
			        </div>

			</form>
<%-- 以下、戻るリンクなど略 --%>			
		</section>
	</c:param>
</c:import>