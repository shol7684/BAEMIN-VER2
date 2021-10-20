<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<!-- <link rel="stylesheet" href="/css/layout/nav.css"> -->
<link rel="stylesheet" href="/css/order/order.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>
	
	
	<!-- <script>
		if("${cartMap}" == "") {
			alert(123);
		}
	</script> -->
	
	<input type="hidden" value="${user.id }" id="user_id">
	
   
	<section class="title">
		<h1>주문하기</h1>
	</section>
	
	<main>
		
	<form action="/order" method="post" onsubmit="return orderCheck()" >
	<ul>
		<li>
			<div class="order_info">
				<h2>${cartMap.storeName }</h2><hr>
				<h2>주문정보</h2>
				
				<ul>
					<c:forEach begin="0" end="${fn:length(cartMap.cartList) -1 }" var="j"  >
					
						<li>
							<input type="hidden" value="${cartList1.menuName }" name="menuName">
							<input type="hidden" value="${cartList1.menuPrice }" name="menuPrice">
							<input type="hidden" value="${cartList1.amount }" name="amount">
						
						
							<div>${cartMap.cartList[j].foodName }</div>
							<div class="price">ㆍ기본가격 <fm:formatNumber value="${cartMap.cartList[j].foodPrice }"  pattern="###,###" />원</div>
						
							
							<c:if test="${fn:length(cartMap.cartList[j].foodOptionName) > 0 }">
								<c:forEach  begin="0" end="${fn:length(cartMap.cartList[j].foodOptionName) -1 }" var="i"  >
								
									<div class="menu_option"> 
										<span>ㆍ${cartMap.cartList[j].foodOptionName[i]  }</span>
										<span><fm:formatNumber  value="${cartMap.cartList[j].foodOptionPrice[i] }" pattern="###,###" />원</span>
									</div>
															
								</c:forEach> 
							</c:if>
							
							<div class="amount">
								<div class="sum">
									<fm:formatNumber value="${cartMap.totalPriceList[j] }" pattern="###,###" />원
								</div>
								<!-- 메뉴 하나 총합 -->
								<span class="amount_box">
				                    <button type="button" class="minus">-</button>
				                    <input type="number" id="amount" min="1" value="${cartMap.amountList[j] }" readonly >
				                    <button type="button" class="plus">+</button>
			                   </span>
							</div>
							
						</li><hr>
						
					</c:forEach>
					
				</ul>
			</div>
		</li>
	
		<li class="delevery_cont">
		
			<div class="delevery_info">
				<h2>배달정보 </h2>
				<div class="address1">주소 : ${BMaddress2} <button type="button">주소 변경하기</button></div>
				
				<input type="hidden" value="${BMaddress1 }" name="deleveryAddress1"> 
				<input type="hidden" value="${BMaddress2 }" name="deleveryAddress2"> 
				
				
				<div>상세 주소</div>
				<div class="input_area"><input type="text" value="${BMaddress3 }"  name="deleveryAddress3"> </div>
				 
				<div>전화번호</div>
				<c:if test="${!empty user  }">
					<div class="input_area"> <input type="number" value="${user.phone }" name="phone" required readonly> </div>
				</c:if>
				<c:if test="${empty user  }">
					<div class="input_area"> <input type="number" value="01028857684" name="phone" required> </div>
				</c:if>
			</div>
		<hr>
		</li>
		
		<li class="request">
			<div>요청사항</div>
				<textarea rows="5" cols="50" name="request" maxlength="500"  ></textarea> 
			<hr>
		</li>
		
		
		<li>
			<h2>결제수단</h2>
				<input type="radio" checked="checked" value="신용카드" name="payMethod" >신용카드
				<input type="radio" value="현장결제" name="payMethod">현장결제
			<hr>
		</li>
		
		<li class="point_area">
			<h2>포인트</h2>
			
			<div class="point">
				<div class="point_click">
					<c:if test="${!empty user  }">
						<span><fm:formatNumber value="${user.point }"  pattern="###,###" />원 사용 가능</span>
						<input type="hidden" value="${user.point }" id="point">
					</c:if>
					
					<c:if test="${empty user  }">
						<span >로그인후 사용 가능합니다.</span>
					</c:if>
					
					<span class="icon"> <i class="fas fa-chevron-down"></i>  </span>
				</div>
				
				<div class="point_input_box" >
					<span>
						<input type="number" name="usedPoint" value="0" min="0" class="point_input" placeholder="사용 할 포인트"  >
						<button class="use_point" type="button">사용하기</button>
					</span>
				</div>
			
			</div><hr>
				
		</li>
		
		<li class="pay">
		
			<div class="order_price">주문금액 : <fm:formatNumber value="${cartMap.menuTotalPrice }"  pattern="###,###" />원</div>
			<div>배달팁 <fm:formatNumber value="${cartMap.deleveryTip }"  pattern="###,###" />원 </div> 
			
			<div class="point_dis"><span>포인트 할인 </span><span></span> </div>
				
			<div class="total">
				<fm:formatNumber value="${cartMap.menuTotalPrice + cartMap.deleveryTip}"  pattern="###,###" />원 결제하기
			</div>
			
			<input type="hidden" value="${cartMap.menuTotalPrice + cartMap.deleveryTip}" name="total" id="total"> 
			<input type="hidden" value="${cartMap.deleveryTip }" name="deleveryTip" id="delevery_tip"> 
			
			
			<input type="submit" value="주문하기" class="order_btn">
		</li>
		
		
		
		</ul>
		
	</form>
	
		
	</main>

	



    <!-- 하단 메뉴 -->
   	<%-- <%@ include file="/WEB-INF/view/include/nav.jsp" %> --%>
    <!-- 하단 메뉴 -->

	<!-- 푸터 -->
	<%@ include file="/WEB-INF/view/include/footer.jsp" %>
	<!-- 푸터 -->
	<script type="text/javascript" src="/js/order.js" ></script>
</body>
</html>

