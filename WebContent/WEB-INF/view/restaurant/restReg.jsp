<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="sectionContainerCenter">
	<div>
		<form action="/restaurant/regProc" method="post">
			<div><input type="text" name="nm" placeholder="가게명"></div>
			<div> 
				<input type="text" name="addr" placeholder="주소">
				<button>좌표 가져오기</button>
			</div>
			<input type="hidden1" name="lat" value="0">
			<input type="hidden1" name="lng" value="0">
			<div>
				카테고리:
				<select name="cd_category">
					<c:forEach items="${categoryList}" var="item">
						<option value="${item.cd}">${item.val}</option>
					</c:forEach>
				</select>
			</div>
		</form>
	</div>
</div>