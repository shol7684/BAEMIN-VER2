<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css" >
<link rel="stylesheet" href="/css/store/likes.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>

	<div class="wrap">
    	<c:if test="${!empty likesList }">
	    	<style>body {background: #fff; }</style>
		    <section class="title">
		        <h1>찜</h1>
		    </section>
		</c:if>	    

	    <main>
	    	<div class="box">
	    	<c:if test="${empty likesList }">
		    	<div class="temp"><img alt="이미지" src="/img/jjim.png"> </div>
		    </c:if>
		    
			    <ul class="store">
	               	<c:forEach items="${likesList }" var="likesList">
	                <li >
	                    <div>
	                        <a href="/store/detail/${likesList.id }">   
	                                
	                            <img src="${likesList.storeImg }" alt="이미지">
	                            
	                            <div class="inf">
	                              ${likesList.openingTime } ${likesList.closingTime } ${likesList.score }
	                                <h2>${likesList.storeName }</h2>
	                            
	                                <div>
	                                	<span>평점 ${likesList.score }</span>
	                                	
	                                	<span class="score_box">
	                                	
					                	<c:forEach begin="0" end="4" var="i">
				                   			<c:if test="${likesList.score >= i }">
						                   		<i class="far fas fa-star"></i>
				                   			</c:if>
				                   			<c:if test="${likesList.score < i }">
						                   		<i class="far fa-star"></i>
				                   			</c:if>
				                   		</c:forEach>
					                   	
				               			</span>
	                                </div>
	                                
	                               	<div><span>리뷰 ${likesList.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${likesList.bossCommentCount }</span></div>
	                                <div><span>배달시간 ${likesList.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${likesList.minDelevery }"  pattern="###,###" />원</span></div>
	                                <div>배달팁 <fm:formatNumber value="${likesList.deleveryTip }"  pattern="###,###" />원</div>
	                            </div>
	                        </a>
						</div>
					</li>
	                </c:forEach>
				</ul>
			</div>
		</main>
	</div>

    <!-- 하단 메뉴 -->
   	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    <!-- 하단 메뉴 -->
	
	<!-- 푸터 -->
	<%@ include file="/WEB-INF/view/include/footer.jsp" %>
	<!-- 푸터 -->
	
</body>
</html>