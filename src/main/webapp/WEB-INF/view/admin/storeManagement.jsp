<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/modal.css" >
<link rel="stylesheet" href="/css/admin/storeManagement.css" >
<link rel="stylesheet" href="/css/admin/storeregiModal.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>

	<div id="tab">
		<section class="tab">
	         <ul class="box">
				<li class="store_management"><a href="/admin/main">관리자 홈</a></li>
				<li class="sales_check"><a href="#">매출 확인</a></li>
				<li class="home"><a href="/">홈으로</a></li>
			</ul>
	    </section>
    </div>
    
    <section class="box">
	    <div class="store_reg_btn"><button>매장 등록</button></div>
    </section>
    
    
 <!-- 콘텐츠 -->
    <main>
        <div class="container">
        <%-- 
            <div class="category">
                <ul>
                    <li data-category ='100' onclick="location.href='/store/100/${address1 }'"><span>피자</span></li>
                    <li data-category ='103' onclick="location.href='/store/103/${address1 }'"><span>분식</span></li>
                    <li data-category ='104' onclick="location.href='/store/104/${address1 }'"><span>카페/디저트</span></li>
                    <li data-category ='105' onclick="location.href='/store/105/${address1 }'"><span>돈까스/일식</span></li>
                    <li data-category ='101' onclick="location.href='/store/101/${address1 }'"><span>치킨</span></li>
                    <li data-category ='106' onclick="location.href='/store/106/${address1 }'"><span>중국집</span></li>
                    <li data-category ='107' onclick="location.href='/store/107/${address1 }'"><span>족발/보쌈</span></li>
                    <li data-category ='108' onclick="location.href='/store/108/${address1 }'"><span>야식</span></li>
                    <li data-category ='109' onclick="location.href='/store/109/${address1 }'"><span>한식</span></li>
                    <li data-category ='102' onclick="location.href='/store/102/${address1 }'"><span>패스트푸드</span></li>
                </ul>
            </div>
 	--%>

            <div class="box">
				<h1>매장 리스트</h1>
				<c:if test="${empty storeList }">
					<img alt="이미지" src="/img/temp2.png">
					<style>main .box {background: #F6F6F6; max-width: 100%; }</style>
				</c:if>
				
				
				
                <ul class="store">
                	<c:forEach items="${storeList }" var="storeList"  varStatus="status">
                    <li >
	                    <div>
	                        <a href="/admin/detail/${storeList.id }">   
	                                
	                            <img src="${storeList.storeImg }" alt="이미지">
	                           
	                            <div class="inf">
	                              ${storeList.openingTime } ${storeList.closingTime } ${storeList.score }
	                                <h2>${storeList.storeName }</h2>
	                                <div>
	                                	<span>평점 ${storeList.score }</span>
	                                	
	                                	<span class="score_box">
	                                	
					                	<c:forEach begin="0" end="4" var="i">
				                   			<c:if test="${storeList.score >= i }">
						                   		<i class="far fas fa-star"></i>
				                   			</c:if>
				                   			<c:if test="${storeList.score < i }">
						                   		<i class="far fa-star"></i>
				                   			</c:if>
				                   		</c:forEach>
					                   	
				               			</span>
	                                </div>
                                    <div><span>리뷰 ${storeList.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${storeList.bossCommentCount }</span></div>
	                                <div><span>배달시간 ${storeList.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${storeList.minDelevery }"  pattern="###,###" />원</span></div>
	                                <div>배달팁 <fm:formatNumber value="${storeList.deleveryTip }"  pattern="###,###" />원</div>
	                                 
	                            </div>
	                        </a>
                        </div>
                    </li>
                    
                    </c:forEach>
                </ul>
            </div>

        </div>
        
        <ul class="page_box">
        	<c:if test="${page.nowPage > page.pageCount}">
        		<li><a href="/admin/storeManagement/${page.prevPage }">이전</a></li>
        	</c:if> 
        	<c:forEach begin="${page.pageNum }" end="${page.pageNum + page.pageCount-1 }" var="i">
        	
        		<c:if test="${i <= lastPage }">
        			
        			<c:if test="${i != page.nowPage }">
	        			<li><a href="/admin/storeManagement/${i }">${i }</a></li>
        			</c:if>
        			<c:if test="${i == page.nowPage }">
        				<li><a class="now_page" href="/admin/storeManagement/${i }">${i }</a></li>
        			</c:if>
        			
        		</c:if>
        	</c:forEach>
        	
       		<c:if test="${page.pageNum + page.pageCount < lastPage  }">
	        	<li><a href="/admin/storeManagement/${page.nextPage }">다음</a></li>
       		</c:if>
        </ul>
    </main>
    
    
     <!-- 콘텐츠 -->
  <%--    
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
				
				 <div class="box">
					 <ul class="store">
	                	<c:forEach items="${storeList }" var="storeList"  varStatus="status">
	                    <li >
		                    <div>
		                        <a href="/store/detail/${storeList.id }">   
		                                
		                            <img src="${storeList.storeImg }" alt="이미지">
		                            
		                            <div class="inf">
		                              ${storeList.openingTime } ${storeList.closingTime } ${storeList.score }
		                                <h2>${storeList.storeName }</h2>
		                                <div>
		                                	<span>평점 ${storeList.score }</span>
		                                	
		                                	<span class="score_box">
		                                	
						                	<c:forEach begin="0" end="4" var="i">
					                   			<c:if test="${storeList.score >= i }">
							                   		<i class="far fas fa-star"></i>
					                   			</c:if>
					                   			<c:if test="${storeList.score < i }">
							                   		<i class="far fa-star"></i>
					                   			</c:if>
					                   		</c:forEach>
						                   	
					               			</span>
		                                </div>
	                                    <div><span>리뷰 ${storeList.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${storeList.bossCommentCount }</span></div>
		                                <div><span>배달시간 ${storeList.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${storeList.minDelevery }"  pattern="###,###" />원</span></div>
		                                <div>배달팁 <fm:formatNumber value="${storeList.deleveryTip }"  pattern="###,###" />원</div>
		                                 
		                            </div>
		                        </a>
	                        </div>
	                    </li>
	                    
	                    </c:forEach>
	                </ul>
				</div>
				 --%>
				
		
				<%-- <ul id="store_list">
		       
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
					                	</c:forEach>
			                        
			                        
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
		
		        </ul> --%>
		        
		        
		        
		        
		        
		    </div>
		</div>
	</section>

</div>





<%@ include file="/WEB-INF/view/admin/storeRegiModal.jsp" %>
    <script type="text/javascript" src="/js/modal/modal.js" ></script> 


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
