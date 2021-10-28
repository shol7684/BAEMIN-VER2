<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css" >
<link rel="stylesheet" href="/css/store/search.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>



	<main>
	  	<form action="/store/search" method="get" onsubmit="return check()" >
		  	<div class="input_box">
			  	<div>
					<label for="submit">
						<i class="fas fa-search"></i>
					</label>
					<input type="submit" id="submit">
				</div>
				<div>	
					<input type="text" class="search" name="keyword" value="${keyword }" placeholder="어떤 가게를 찾으시나요?">
					<div class="info">현재 주소지를 기준으로 검색됩니다.</div>
					<input type="hidden" value="${BMaddress1 }" name="address1" id="deleveryAddress1">
					<%@ include file="/WEB-INF/view/include/modifyAddress.jsp" %> 
				</div>
				<div>
					<button type="button" class="word_delete"><i class="fas fa-times"></i></button>
				</div>
			
			</div>
		</form>
		
		<div class="search_word_head">
			<h2>최근 검색어</h2>
			<button>전체삭제</button>
		</div>
		<div class="search_word">
			<ul>
				<c:forEach items="${searchKeyword }" var="searchKeyword">
				
					<li>
						<span>${searchKeyword }</span>
						<button><i class="fas fa-times"></i></button>
					</li>
					
				</c:forEach>
			</ul>
		</div>
		
		
		 <div class="box">

                <ul class="store">
                	
                	<c:forEach items="${store }" var="store" >
                
                    <li >
	                    <div>
	                        <a href="/store/detail/${store.id }">           
	                            <img src="${store.storeImg }" alt="이미지">
	                            <div class="inf">
	                                <h2>${store.storeName }</h2>
	                                <div>
	                                	<span>평점 ${store.score }</span>
	                                	
	                                	<span class="score_box">
						                   	<c:forEach begin="0" end="4" var="i">
						                   		<c:if test="${store.score >= i }">
						                   			<i class="fas fa-star"></i>
						                   		</c:if>
						                   		<c:if test="${store.score < i }">
						                   			<i class="far fa-star"></i>
						                   		</c:if>
						                   	</c:forEach>
				               			</span>
	                                </div>
	                             
	                                
	                                <div><span>리뷰 ${store.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${store.bossCommentCount }</span></div>
	                                <div><span>배달시간 ${store.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${store.minDelevery }"  pattern="###,###" />원</span></div>
	                                <div>배달팁 <fm:formatNumber value="${store.deleveryTip }"  pattern="###,###" />원</div>
	                            </div>
	                        </a>
                        </div>
                    </li>
                    
                    </c:forEach>
                    
                    <li class="temp">
                    </li>
                </ul>
            </div>
	
	</main>

  
  
	<%@ include file="/WEB-INF/view/include/nav.jsp" %>

	<%@ include file="/WEB-INF/view/include/footer.jsp" %>
  

  	<script type="text/javascript" src="/js/store/search.js" ></script>
  
</body>
</html>