<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="recMenuContainer">
	<c:forEach items="${recommendMenuList}" var="item">
		<div class="recMenuItem">
			<div class="pic">
				<c:if test="${item.menu_pic != null and item.menu_pic != ''}">
					<img src="/res/img/restaurant/${data.i_rest}/${item.menu_pic}">
				</c:if>
			</div>
			<div class="info">
				<div class="nm">${item.menu_nm}</div>
				<div class="price">${item.menu_price}</div>
			</div>
		</div>
	</c:forEach>
</div>
<div id="sectionContainerCenter">
	<div>
		<c:if test="${loginUser.i_user == data.i_user}">
			<div>
				<a href="/restaurant/restMod?i_rest=${data.i_rest}"><button>수정</button></a>
				<button onclick="isDel()">삭제</button>

				<form id="recFrm" action="/restaurant/addRecMenusProc" method="post"
					enctype="multipart/form-data">
					<div>
						<button type="button" onclick="addRecMenu()">메뉴추가</button>
					</div>
					<input type="hidden" name="i_rest" value="${data.i_rest}">
					<div id="recItem"></div>
					<div>
						<input type="submit" value="등록">
					</div>
				</form>
			</div>
		</c:if>
		<div class="restaurant-detail">
			<div id="detail-header">
				<div class="restaurant_title_wrap">
					<span class="title">
						<h1 class="restaurant_name">${data.nm}</h1>
					</span>
				</div>
				<div class="status_branch_none">
					<span class="cnt hit">${data.cntHits}</span> <span
						class="cnt favorite">${data.cntFavorite}</span>
				</div>
			</div>
			<div>
				<table>
					<caption>레스토랑 상세정보</caption>
					<tbody>
						<tr>
							<th>주소</th>
							<td>${data.addr}</td>
						</tr>
						<tr>
							<th>카테고리</th>
							<td>${data.cd_category_nm}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
	var idx = 0;

	function addRecMenu() {
		var div = document.createElement('div')
		var inputNm = document.createElement('input')
		inputNm.setAttribute("type", "text")
		inputNm.setAttribute("name", "menu_nm")
		var inputPrice = document.createElement('input')
		inputPrice.setAttribute("type", "number")
		inputPrice.setAttribute("name", "menu_price")
		var inputPic = document.createElement('input')
		inputPic.setAttribute("type", "file")
		inputPic.setAttribute("name", "menu_pic_" + idx++)

		div.append('메뉴: ')
		div.append(inputNm)
		div.append('가격: ')
		div.append(inputPrice)
		div.append('사진: ')
		div.append(inputPic)

		recItem.append(div)
	}

	addRecMenu()

	function isDel() {
		if (confirm('삭제하시겠습니까')) {
			location.href = '/restaurant/restDel?i_rest=${data.i_rest}'
		}
	}
</script>