<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=511c45bf18e47387a8bc808594cfc94b"></script>
	<div id="mapContainer" style="width:100%; height:100%"></div>
</div>

<script>
const container = document.getElementById('mapContainer'); //지도를 담을 영역의 DOM 레퍼런스
const options = { //지도를 생성할 때 필요한 기본 옵션
	center: new kakao.maps.LatLng(35.8484577,128.5713276), //지도의 중심좌표.
	level: 5 //지도의 레벨(확대, 축소 정도)
};

const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
</script>