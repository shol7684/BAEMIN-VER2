<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
	<link rel="stylesheet" href="/css/modal.css" >
	<link rel="stylesheet" href="/css/layout/nav.css" >
	<link rel="stylesheet" href="/css/order/orderList.css" >
    
<%@ include file="/WEB-INF/view/include/header.jsp" %>
    
    
	<div class="wrap">
    
		<c:if test="${!empty orderList }">
			<style>body {background: #fff; } </style> 
		    <section class="title">
		        <h1>주문 내역</h1>
		    </section>
		</c:if>    
		
		<c:if test="${empty orderList }" >
       		<img alt="이미지" src="/img/temp.png" class="temp_img"> 
       	</c:if> 
	       
	       
       <c:if test="${fn:length(orderList) >0 }"> 	
	    <main>
	        <div class="order_list">
	         	
	        	
	        	
        		<ul id="order_list">
        		
        		
	        	<c:forEach begin="0" end="${fn:length(orderList)-1 }" var="i">
                    <li>
                    	<div class="img_box">
                    		<a href="/store/detail/${orderList[i].storeId }" >
                    			<img src="${orderList[i].storeImg }" alt="이미지">
                   			</a>
                   		</div>
                    
                    
                    	<div class="info_box">
                    		<span>
                    			<fm:formatDate value="${orderList[i].orderDate }" pattern="M월d일" />
	                   			<span>${orderList[i].deleveryStatus}</span> 
                    		</span>

                           	<h2>
                           		<a href="/store/detail/${orderList[i].storeId }" >
                           			${orderList[i].storeName }
                           		</a>
                           	</h2>
                           	 
                            <span class="info">
	                       	 	<a href="/store/detail/${orderList[i].storeId }" >
	                       	 	
	                           		<c:set value="${fn:length(cartList[i] )}" var="cart" />
	                           		<c:if test="${cart > 1 }">
	                           			<span>${cartList[0][0].foodName } 외 ${cart -1 }개</span>
	                           		</c:if>
	                           		<c:if test="${cart <= 1 }">
		                                <span>${cartList[0][0].foodName } ${cartList[0][0].amount }개 </span>
	                           		</c:if>     
	                                <span><fm:formatNumber value="${orderList[i].totalPrice + orderList[i].deleveryTip - orderList[i].usedPoint }" pattern="###,###" /> 원</span>
		                        </a>
                             </span>
                        </div>
                        
                        <div class="review_btn_box">
	                        <button class="order_detail">상세보기</button>
	                        <c:if test="${!empty user }">
	                        
                    			<input type="hidden" class="order_num" value="${orderList[i].orderNum }">
	                        	<input type="hidden" class="store_id" value="${orderList[i].storeId }">
	                        	
	                        	<c:if test="${empty orderList[i].reviewContent }">
		                        	<button class="review regi">리뷰쓰기</button>
	                        	</c:if>
	                        	<c:if test="${!empty orderList[i].reviewContent }">
		                        	<button class="review modify">리뷰 수정하기</button>
		                        	<input type="hidden" value="${orderList[i].reviewContent }" class="review_content" >
		                        	<input type="hidden" value="${orderList[i].score }" class="review_score" >
		                        	<input type="hidden" value="${orderList[i].reviewImg}" class="review_img" name="store_img">
	                        	</c:if>
		                        	
	                        </c:if>
                        </div>
					</li>
        	</c:forEach>
        	
            </ul>
        </div>
        <c:set var="movePage" value="/orderList/" />
        <%@ include file="/WEB-INF/view/include/pageBox.jsp" %>
    </main>
    </c:if>
</div>

    <!-- 하단 메뉴 -->
	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="/WEB-INF/view/include/footer.jsp" %>
    <!-- 푸터 -->
	
	
		
	<!-- 리뷰 쓰기 모달 -->
    <div id="modal_bg"></div>

    <div class="review_modal modal">
    	<div id="modal_header">
			<button class="closeA"><i class="fas fa-times"></i></button>
			<h1>리뷰 쓰기</h1>
    	</div>
    	
	    <form action="/store/review" method="post"  enctype="multipart/form-data"> 
    	<div class="modal_box">
	    	<div class="score_box">
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i> 
	    	</div>
	    	
	    	<input type="hidden" name="score" class="score" >
	    	
	    	<div class="review_text">
	    		<textarea rows="10" cols="50" name="reviewContent" maxlength="500" ></textarea>
	    	</div>
	    	
	    	<div class="img_box">
	    		<label for="img">사진첨부</label>
	    			<input type="file" id="img" class="img" name="file" >
	    		
	    	
	    		<div>
	    			<img class="preview">
	    			<button type="button" class="img_close"><i class="fas fa-times"></i></button>
    			</div>
	    	</div>
    	</div>
    	
    	<div id="btn_box">
    		<input type="hidden" class="order_num" name="orderNum">
    		<input type="hidden" class="store_id" name="storeId">
 			<button type="button" class="closeB">취소</button>
 			<button type="submit" class="review_submit_btn" disabled >리뷰 작성</button>
    	</div>
    	
    	
    	</form>
    </div>
	<!-- 리뷰 쓰기 모달 -->
  
  
  
	<!-- 리뷰 수정하기 모달 -->
    <div class="review_modify_modal modal">
    	<div id="modal_header">
		    <button class="closeA"><i class="fas fa-times"></i></button>
	    	<h1>리뷰 수정하기</h1>
    	</div>

	    <form action="/store/reviewModify" method="post"  enctype="multipart/form-data">
    	<div class="modal_box">
	    	<div class="score_box">
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i> 
	    	</div>
	    	
	    	<input type="hidden" name="score" class="score" >
	    	
	    	<div class="review_text">
	    		<textarea rows="10" cols="50" name="reviewContent" maxlength="500" ></textarea>
	    	</div>
	    	
	    	<div class="img_box">
	    		<label for="img2">사진첨부</label>
    			<input type="file" id="img2" class="img" name="file" >
	    			
	    		<div>
	    			<img class="preview">
	    			<button type="button" class="img_close"><i class="fas fa-times"></i></button>
    			</div>
	    	</div>
    	</div>
    	
    	<div id="btn_box">
    		<input type="hidden" class="order_num" name="orderNum">
    		<input type="hidden" class="store_id" name="storeId">
 			<button type="button" class="closeB">취소</button>
 			<button type="submit" class="review_submit_btn" disabled >리뷰 수정하기</button>
    	</div>
    	
    	</form>
    </div>
	<!-- 리뷰 수정하기 모달 -->
	
	
	
	<script type="text/javascript" src="/js/order/orderList.js" ></script>
	<script type="text/javascript" src="/js/modal/modal.js" ></script>
	
	
</body>
</html>