<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/modal.css" >
<link rel="stylesheet" href="/css/admin/storeManagement.css" >
<link rel="stylesheet" href="/css/admin/storeregiModal.css" >
<link rel="stylesheet" href="/css/store/store-li.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>

	<div id="tab">
		<section class="tab">
	         <ul class="box">
				<li class=""><a href="/admin/main">주문접수</a></li>
				<li class="store_management"><a href="/admin/storeManagement">매장 관리</a></li>
				<li class="sales"><a href="/admin/salesManagement">매출 확인</a></li>
				<li class="order_search"><a href="#">주문번호 검색</a></li>
				<li class="home"><a href="/">홈으로</a></li>
			</ul>
	    </section>
    </div>
    
    <section class="box">
    	<form action="/admin/storeManagement">
		    <div class="store_reg_btn">
		    	<button type="button">매장 등록</button>
	    	</div>
		    
		    <div class="store_search">
		    	<span>매장 검색</span>
		    	<input type="text" maxlength="33" name="keyword">
		    	<button>검색</button>
	    	</div>
		    
	    </form>
    </section>
    
    
 <!-- 콘텐츠 -->
    <main>
        <div class="container">

            <div class="box">
				<h1>매장 리스트</h1>
				
				<c:if test="${nosuch }">
					<div class="nosuch">검색 결과가 없습니다</div>
				</c:if>
				
                <ul class="store">
                	<c:set var="store_admin" value="/admin" />
                	<c:forEach items="${storeList }" var="storeList" >
                    <%@ include file="/WEB-INF/view/store/store-li.jsp" %>
                    
                    </c:forEach>
                </ul>
            </div>

        </div>
        
        <c:set var="movePage" value="/admin/storeManagement/" />
        <%@ include file="/WEB-INF/view/include/pageBox.jsp" %>
    </main>
    


	<%@ include file="/WEB-INF/view/admin/storeRegiModal.jsp" %>
	
     


	<script>


let size = $(window).width();

$(window).off().resize(function() {
	size = $(window).width();
	
	if(size > 1023) {
		$(".tab").show();
	} 
})
	

$(".menu_tab").click(function(){
	if(size < 1024) {
		$(".tab").stop().fadeToggle();	
	}
});


// 매장 등록	
$(".store_reg_btn button").click(function(){
	openModal($(".store_reg_modal"),size);
	
});

// 이미지 미리보기
$(".img").change(function(e){
	imgPreview(e,$(this));
})


$(".img_close").click(function(){
	imgClose();
})
	
	
	
function check(){
	const address1 = $("#address1").val();
	const address2 = $("#address2").val();
	
	if(!address1 || !address2) {
		swal("주소를 입력해주세요");
		return false;
	}
	return true;
}
	
	
	
	</script>
	  <script type="text/javascript" src="/js/modal/modal.js" ></script>
	<script src="/js/util/util.js"></script>
	

</body>
</html>
