<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

    <link rel="stylesheet" href="/css/modal.css" > 
    <link rel="stylesheet" href="/css/store/detail.css" >
    
    
<%@ include file="/WEB-INF/view/include/header.jsp" %>

	
 	<!-- 메인 -->
	<%@ include file="/WEB-INF/view/store/storeDetail.jsp" %>
 	<!-- 메인 -->
 	
    <!-- 푸터 -->
    <%@ include file="/WEB-INF/view/include/footer.jsp" %>
    <!-- 푸터 -->
    
	<!-- 메뉴 모달 -->
	<%@ include file="/WEB-INF/view/include/foodModal.jsp" %>
    <!-- 메뉴 모달 -->
    
	<!-- 메뉴 추가하기 모달 -->
	<%@ include file="/WEB-INF/view/admin/addMenuModal.jsp" %>
    <!-- 메뉴 추가하기 모달 -->
    
	<!-- 매장 정보 수정하기 모달 -->
	<%@ include file="/WEB-INF/view/admin/storeModifyModal.jsp" %>
    <!-- 매장 정보 수정하기 모달 -->
	
<script>


function submitCheck() {
	console.log("체크");
	
	var a = $("input[name=foodOption]").val();
	var b = $("input[name=foodOptionPrice]").val();
	
	console.log("a = " + a);
	console.log("b = " + b);
	return true;
}


function lenthCheck(e, length) {
	console.log(e.value.length);
	console.log(length);
	
	if(e.value.length >= length) {
		return false;
	}
	
	$(this).off().focusout(function(){
		if(e.value.length > length) {
			e.value = "";
		}
	})
	
	return true;
}

</script>
	
	
	<link rel="stylesheet" href="/css/admin/addMenuModal.css" >
	<link rel="stylesheet" href="/css/admin/storeRegiModal.css" >
 	<script type="text/javascript" src="/js/store/storeDetail.js" ></script>
 	<script type="text/javascript" src="/js/admin/adminDetail.js" ></script>
    <script type="text/javascript" src="/js/openModal.js" ></script> 
    <script type="text/javascript" src="/js/modal.js" ></script> 
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3fe0c2eaecb263f09df91a81c2ec64a0&libraries=services,clusterer,drawing"></script>
</body>
</html>