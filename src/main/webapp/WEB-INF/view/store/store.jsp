<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/link.jsp" %>

<link rel="stylesheet" href="/css/store/store.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>


    <!-- 콘텐츠 -->
    <main>
        <div class="container">
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




           <div class="option">
                
                <ul>    
                	<li>기본순</li>
                    <li>배달 빠른 순</li>
                    <li>배달팁 낮은 순</li>
                    <li>별점 높은 순</li>
                    
                </ul> 
           </div>
           
           

            <div class="box">
				
				<c:if test="${empty storeList }">
					<img alt="이미지" src="/img/temp2.png">
				</c:if>
				
                <ul class="store">
                	
                	<c:forEach items="${storeList }" var="storeList"  varStatus="status">
                
                    <li >
	                    <div>
	                        <a href="/store/detail/${storeList.id }">   
	                                
	                            <img src="${storeList.storeThumb }" alt="이미지">
	                            
	                            
	                            
	                            <div class="inf">
	                              ${storeList.openingTime } ${storeList.closingTime } ${storeList.score }
	                                <h2>${storeList.storeName }</h2>
	                          <%--   <c:if test="${(storeList.busiHours1 < storeList.busiHours2 && !(storeList.busiHours1 < time && time < storeList.busiHours2)) || 
	                            			  (storeList.busiHours1 > storeList.busiHours2 && !(storeList.busiHours1 <= time && time <= 24)) }">
	                            	<div>영업준비중</div>
	                            </c:if> --%>
	                            
	                            
	                            
	                            
	                                <div>
	                                	<span>평점 ${storeList.score }</span>
	                                	
	                                	<span class="score_box">
	                                	
	                                	<c:set var="score" value="${Math.round(storeList.score) -1 }" />
	                                	
	                                	<c:if test="${score <= 0}" >
	                                		<c:set var="score" value="0" />
	                                		
	                                	</c:if>
	                                	
	                                	
	                                	<c:forEach begin="0" end="4" var="i">
	                                		<c:if test="${score >= i }">
												<i class="fas far fa-star"></i>
	                                		</c:if>
	                                		
	                                		<c:if test="${score < i }">
												<i class="far fa-star"></i>
	                                		</c:if>
					                   	</c:forEach>
					                   	
				               			</span>
	                                </div>
	                              <%--
	                                
	                                <div><span>리뷰 ${storeList.reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${storeList.bossComment }</span></div>
	                                <div><span>배달시간 ${storeList.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${storeList.minDelevery }"  pattern="###,###" />원</span></div>
	                                <div>배달팁 <fm:formatNumber value="${storeList.deleveryTip }"  pattern="###,###" />원</div>
	                                
	                                 --%>
	                                   <div><span>리뷰 13</span><span>ㅣ</span><span>사장님 댓글 15</span></div>
	                                <div><span>배달시간 ${storeList.deleveryTime }분</span><span>최소주문금액 <fm:formatNumber value="${storeList.minDelevery }"  pattern="###,###" />원</span></div>
	                                <div>배달팁 <fm:formatNumber value="${storeList.deleveryTip }"  pattern="###,###" />원</div>
	                                 
	                            </div>
	                        </a>
                        </div>
                    </li>
                    
                    </c:forEach>
                    
                    <li class="temp">
                    </li>
                </ul>
            </div>

        </div>
    </main>
    
    
     <!-- 콘텐츠 -->
      
     
    <!-- 하단 메뉴 -->
	<%@ include file="../include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="../include/footer.jsp" %>
    <!-- 푸터 -->


<%-- 	<input type="hidden" value="${category }" class="cate">
	<input type="hidden" value="${address.address1 }" class="address">
	<input type="hidden" value="${storeList[0].storeName }" class="store_list" >
 --%>


    
</body>
</html>