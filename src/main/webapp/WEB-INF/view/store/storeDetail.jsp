<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>  

 <div id="wrap">

	    <nav>
	        <h1>${store.storeInfo.storeName }</h1>
	
               <div class="inf">
                   <div>
	                   <span class="score_box">
	                   		<c:forEach begin="0" end="4" var="i">
	                   			<c:if test="${store.storeInfo.score >= i }">
			                   		<i class="far fas fa-star"></i>
	                   			</c:if>
	                   			<c:if test="${store.storeInfo.score < i }">
			                   		<i class="far fa-star"></i>
	                   			</c:if>
	                   		</c:forEach>
	                   		<span>${store.storeInfo.score }</span>
               			</span><br>
	                   
                  		<c:if test="${storeVO.goodCheck == 0 }">
		                   <span><i class="far fa-heart" ></i> 찜 </span>
		                   <span class="count">${storeVO.dibsCount }</span>
	                   </c:if>
	                   
	                   <c:if test="${storeVO.goodCheck != 0 }">
		                   <span><i class="fas fa-heart" ></i> 찜 </span>
		                   <span class="count">${storeVO.dibsCount }</span>
	                   </c:if> 
		                   <span class="good"></span>
	                   
                   </div>
                   <div><span> 리뷰 ${store.storeInfo.reviewCount } ㅣ </span><span>사장님 댓글 ${store.storeInfo.bossCommentCount }</span></div>
                   
                   <div>최소주문금액 <fm:formatNumber value="${store.storeInfo.minDelevery }" pattern="###,###" /> 원</div>
                   <div>예상 배달시간 ${store.storeInfo.deleveryTime  }분</div>
                   <div>배달팁 <fm:formatNumber value="${store.storeInfo.deleveryTip }" pattern="###,###" />원</div>
                   
                   <c:if test="${adminPage && role == 'ROLE_ADMIN'  }">
                   <div id="admin_button_box">
	                   <button class="inf_modify">정보 수정</button>
	                   <button class="add_menu">메뉴 추가</button>
	                   <button class="delete_menu">메뉴 삭제</button>
                   </div>
                   </c:if>
	    
               </div>
	    </nav>
	         
	<!-- ---------------------------------- 모바일 카트 ---------------------------------- -->
   	<div class="m_cart_img">
   		<div class="m_cart_img_box">
   			<i class="fas fa-shopping-cart"></i>
   			<span class="m_cart_count"></span>
   		</div>
   	</div>
  	<!-- ---------------------------------- 모바일 카트 ---------------------------------- -->
    
	<!---------------------------------------- 장바구니 -------------------------------------- -->    
	    
	    <aside id="cart">
	        <div class="cart">	
	            <h2>장바구니</h2>
	            <i class="far fa-trash-alt deleteAll" ></i>
	            
	            <div class="cart_list">
		            <ul>
		            	<!-- 
						<li>
							<h3>메뉴</h3>
   							<div>가격</div>
   							<div>수량 : 0 </div>
   							<div> 옵션  </div>
   							<div>합계 : 0원</div>
   							<button class="cancle_btn"> ｘ </button> 
			            </li>
   							-->
		            </ul>
	            </div>
	            
	            <div class="order_btn_box">
	            	<div class="total">장바구니가 비었습니다.</div>
		            <button class="order_btn" disabled>주문하기</button>
	            </div>
	        </div>
	        
	    </aside>
	<!---------------------------------------- 장바구니 -------------------------------------- -->    
	   

	    <main>
	    	<div class="offset"></div>
	        <ul class="tab ">
	            <li class="select">메뉴</li>
	            <li>정보</li>
	            <li>리뷰</li>
	        </ul>
	
	<!-- ----------------------------------- 메뉴 탭 ------------------------------------- -->
	        <ul class="menu">
	        <c:forEach items="${store.foodList }" var="foodList" >
	            <li>
	            <c:if test="${adminPage && role == 'ROLE_ADMIN' }">
	                <input type="checkbox" class="menu_delete_checkbox" name="deleteNumber" value="${foodList.id }">
                </c:if>
                
	                <div class="in">
	                    <h2>${foodList.foodName } </h2>
	                    <div><img src="${foodList.foodImg }" alt="이미지"></div>
	                    <div><fm:formatNumber value="${foodList.foodPrice }" pattern="###,###" />원 </div>
	                    
	                     <input type="hidden" value="${foodList.storeId }" name="storeId" >
			             <input type="hidden" value="${foodList.id }" name="foodId" id ="food_id"   >
			             <input type="hidden" value="${foodList.foodName }" name="foodName" id ="food_name" >
			             <input type="hidden" value="${foodList.foodPrice }" name="foodPrice" id ="food_price"   >
			             <input type="hidden" value="${foodList.foodDec }" name="foodDec" id ="food_dec"   >
			             <input type="hidden" value="${foodList.foodImg }" name="foodImg" id ="food_img"   >
			             <input type="hidden" value="${foodList.foodThumb }" name="foodThumb" id ="food_thumb"   >
	                </div>
	             </li>
	        </c:forEach>     
	        </ul>
			<!-- ----------------------------------- 메뉴 탭 ------------------------------------- -->
		
		
			<!-- ----------------------------------- 정보 탭 ------------------------------------- -->
	       <ul class="info" >
	
				<li>
					<div>
						<h2>찾아 오시는 길</h2>
						
						<div id="map_box">
							<div id="map"></div>
							
							<div id="position_box">
								<button class="storePosition" ><i class="far fa-dot-circle"></i> 가게 위치로</button>
								<button class="userPosition"> <i class="far fa-dot-circle"></i> 내 위치로</button>
							</div>
						</div>
						
						<h2>위치안내</h2>
						<div>${store.storeInfo.storeAddress2 }  ${store.storeInfo.storeAddress3 }</div>
					</div>
				</li>
				
	            <li>
	            	<div>
		               <h2>가게 소개</h2>
		               <div> ${store.storeInfo.storeDes }</div>
	               </div>
	            </li>
	            
	            <li>
	            	<div>
		            	<h2>영업 정보</h2>
		            	
		            	<div class="info_detail_title">
		            		<div>상호명</div>
		            		<div>영업시간</div>
		            		<div>전화번호</div>
		            		
		            	</div>
		            	
		            	<div class="info_detail">
		            		<div>${store.storeInfo.storeName }</div>
		            		<div><span>${store.storeInfo.openingTime }시 ~</span><span>${store.storeInfo.closingTime }시 </span></div>
		            		<div>${store.storeInfo.storePhone }</div>
		            		
		            	</div>
	            	</div>
	            </li>
	            
	            <li>
	            	<div>
		            	<h2>가계 통계</h2>
		            	<div class="info_detail_title">
			            	<div>최근 주문수</div>
			            	<div>전체 리뷰 수</div>
			            	<div>찜</div>
		            	</div>
		            	
		            	<div class="info_detail">
		            		<div>${store.storeInfo.orderCount }</div>
		            		<div>${store.storeInfo.reviewCount }</div>
		            		<div>${storeVO.dibsCount } 1222313213</div>
		            	</div>
		            </div>	
	            </li>
	            
	            
	        </ul>
		<!-- ----------------------------------- 정보 탭 ------------------------------------- -->    
	
		
		<!-- ----------------------------------- 리뷰 탭 ------------------------------------- -->        
	        <ul class="comment" >
	        	<li>
	        		<div class="score_info">
	        			<div>
	        				<div class="score">${store.storeInfo.score }</div>
		                   		
	        				<div>
		        				<c:forEach begin="0" end="4" var="i">
		        					<c:if test="${store.storeInfo.score >= i }">
		        						<i class="fas fa-star"></i>
		        					</c:if>
		        				
		        					<c:if test="${store.storeInfo.score < i }">
		        						<i class="far fa-star"></i>
		        					</c:if>
		        				</c:forEach>
	        				</div>
	        			</div>
	        			
	        			<div class="score_count">
	        				<div> 
	        					<div>5점</div>
	        					
	        					<div class="graph_box">
	        						<div class="graph_background"></div>
	        						<div class="graph five"></div>
	        					</div>
	        					
	        					<div class="review_count">${storeVO.fiveScore }</div>
	        				</div>
	        				
	        				<div> 
	        					<div>4점</div>
	        					<div class="graph_box">
	        						<div class="graph_background"></div>
	        						<div class="graph four"></div>
	        					</div>
	        					<div class="review_count">${storeVO.fourScore }</div>
	        				</div>
	        				
	        				<div> 
	        					<div>3점</div>
	        					<div class="graph_box">
	        						<div class="graph_background"></div>
	        						<div class="graph three"></div>
	        					</div>
	        					<div class="review_count">${storeVO.threeScore }</div>
	        				</div>
	        				
	        				<div> 
	        					<div>2점</div>
	        					<div class="graph_box">
	        						<div class="graph_background"></div>
	        						<div class="graph two"></div>
	        					</div>
	        					<div class="review_count">${storeVO.twoScore }</div>
	        				</div>
	        				
	        				<div> 
	        					<div>1점</div>
	        					<div class="graph_box">
	        						<div class="graph_background"></div>
	        						<div class="graph one"></div>
	        					</div>
	        					<div class="review_count">${storeVO.oneScore }</div>
	        				</div>
	        				
	        				
	        			</div>
	        		
	        		</div>
	        	</li>
	        
	        
	        
	        	<c:forEach items="${store.reviewList }" var="reviewList">
	            <li>
	            	<div class="client">
	            		
	            		<div class="review_header">
	            			<div>
				                <div class="nickname">${reviewList.nickname }</div>
				                <div>
				                	
				                	<c:forEach begin="0" end="4" var="i">
				                		<c:if test="${i < reviewList.score }">
					                		<i class="fas far fa-star"></i>
				                		</c:if>
				                		
				                		<c:if test="${i >= reviewList.score }">
					                		<i class="far fa-star"></i>
				                		</c:if>
				                	</c:forEach>
				                	
				                	<span><fm:formatDate value="${reviewList.regiDate }" pattern="yyyy-MM-dd" /> </span>
				                </div>
		                	</div>
		                	
		                	
		                	 <c:if test="${role == 'ROLE_ADMIN' }">
			                 <div>
			                
			                	<c:if test="${!empty reviewList.bossComment}">
			                		<button class="review_btn comment_modify">댓글 수정하기</button>
			                	</c:if>
			                	
			                	<c:if test="${empty reviewList.bossComment}">
			                		<button class="review_btn comment_write" >답장하기</button>
			                	</c:if> 
			                	<input type="hidden" value="${reviewList.orderNum }" class="order_num">
			                </div>
			                </c:if>
			                
			                
		                </div> 
			                
		                <div>
			                <c:if test="${!empty reviewList.reviewImg }">
			                	<div><img src="${reviewList.reviewImg }" alt="이미지" class="review_img"></div>
			                </c:if>
		                	<div>${reviewList.reviewContent } </div>
		                </div>
	                </div>
	                
	                
	                <div class="boss">
		                <c:if test="${!empty reviewList.bossComment }">	
			                <div class="boss_comment_box">
			                	<div class="nickname">사장님</div>
			                	<div class="boss_comment">${reviewList.bossComment }</div>
			                </div>
		                </c:if>
	                </div>
	                
	                <!-- <div class="temp"></div> -->
	                
	                 <div class="boss input">
	               	 	<div class="boss_comment_box">
	        		 		<div class="nickname">사장님</div>
	        				<div class="boss_comment">
		        				<textarea class="comment_area" spellcheck="false"></textarea>
	        				</div>
	        				
		        			<div>
		        				<button class="boss_comment_btn reply" >댓글 달기</button>
		        				<input type="hidden" value="${reviewList.orderNum }" class="order_num">
		        			</div>
	        			</div>
	       			</div>
	                
	                
	            </li>
			</c:forEach>
	
	        </ul>
	<!-- ----------------------------------- 리뷰 탭 ------------------------------------- -->
		</main>
	</div>






	
	<input type="hidden" value="${store.storeInfo.id }" id="store_id">
	<input type="hidden" value="${store.storeInfo.minDelevery }" id="min_delevery">
	<input type="hidden" value="${store.storeInfo.deleveryTip }" id="delevery_tip">
	<input type="hidden" value="${store.storeInfo.storeName }" id="store_name">
	
	
    <input type="hidden" value="${storeVO.goodCheck }" class="dibs_check">
	<input type="hidden" value="${userId  }" class="user_id">
	<input type="hidden" value="${storeVO.cate }" class="category"> 
	<input type="hidden" value="${storeVO.busiHours1 }" class="business_hour1"> 
	<input type="hidden" value="${storeVO.busiHours2 }" class="business_hour2"> 
	<input type="hidden" value="${storeVO.storeAddress2 }" class="store_address">
	<input type="hidden" value="${address.address2 }" class="user_address">
	
	<input type="hidden" value="${storeVO.reviewCount }" id="review_count">
	<input type="hidden" value="${storeVO.fiveScore }" id="five_score">
	<input type="hidden" value="${storeVO.fourScore }" id="four_score">
	<input type="hidden" value="${storeVO.threeScore }" id="three_score">
	<input type="hidden" value="${storeVO.twoScore }" id="two_score">
	<input type="hidden" value="${storeVO.oneScore }" id="one_score">
	<input type="hidden" value="${storeVO.score }" id="score">

	
 


     <script>
      

    </script>
 

 