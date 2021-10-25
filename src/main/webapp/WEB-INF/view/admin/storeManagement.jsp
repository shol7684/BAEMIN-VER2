<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/modal.css" >
<link rel="stylesheet" href="/css/admin/storeManagement.css" >
<link rel="stylesheet" href="/css/admin/storeregiModal.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>


<div class="wrap">
	<section class="tab">
         <ul class="box">
			<li class="store_management"><a href="/admin/main">관리자 홈</a></li>
			<li class="sales_check"><a href="#">매출 확인</a></li>
			<li class="home"><a href="/">홈으로</a></li>
		</ul>
    </section>
    
	<section class="manage">                
		<div class="store">
			<div class="store_reg_btn"><button>매장 등록</button></div><hr>
			<div class="store_list">
				<h1>매장 리스트</h1><hr>
		
				<ul id="store_list">
		       
				<c:forEach items="${storeList }" var="storeList">
					<li>
						<div>
			                <a href="/admin/detail/${storeList.id }">
			                	<span class="store_img_box">
				                    <img src="${storeList.storeImg }" alt="이미지">
			                	</span>
			                
			                    <span class="inf">
									<h2>${storeList.storeName }</h2>
										<span>평점 ${storeList.score }</span>
				                        <c:forEach begin="0" end="4" var="i">
					                		<c:if test="${i <= storeList.score }">
						                		<i class="fas far fa-star"></i>
					                		</c:if>
					                		<c:if test="${i > storeList.score }">
						                		<i class="far fa-star"></i>
					                		</c:if>
					                	</c:forEach><br>
			                        
			                        
			                        <span>
			                        	<span>리뷰 ${storeList.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${storeList.bossCommentCount }</span>
			                        </span>
			                        
			                        <span>
			                      	 	<span>예상배달시간 ${storeList.deleveryTime }분</span>
			                       		<span>최소주문금액 <fm:formatNumber value="${storeList.minDelevery }" pattern="###,###" />원</span>
			                        </span>
			                       <span>
				                       <span>배달팁 <fm:formatNumber value="${storeList.deleveryTip }" pattern="###,###" />원</span>
				                       <span>영업시간 ${storeList.openingTime }시 ~ ${storeList.closingTime }시 </span>
			                       </span>
			                       
			                   </span>
			               </a>
		               </div>
		           </li>
		      </c:forEach>
		
		        </ul>
		    </div>
		</div>
	</section>

</div>





<%@ include file="/WEB-INF/view/admin/storeRegiModal.jsp" %>
    <script type="text/javascript" src="/js/openModal.js" ></script> 
    <script type="text/javascript" src="/js/modal.js" ></script> 


	<script>

	const storeRegBtn = $(".store_reg_btn  button");  //매장등록 버튼
	const storeRegModal =  $(".store_reg_modal"); 
	
	let size = $(window).width();
	
	$(window).resize(function(){
		size = $(window).width();
		console.log(size);
	})
	
	storeRegBtn.click(function(){
		openModal(storeRegModal,size);
		
	});
	
	
	
	/*폰번호 길이제한 11자*/
function lenthCheck(e, length) {
	
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
	  


</body>
</html>
