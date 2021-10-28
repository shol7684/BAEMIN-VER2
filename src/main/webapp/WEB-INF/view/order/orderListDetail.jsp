<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp"%>
<link rel="stylesheet" href="/css/order/orderDetail.css">
<!-- <link rel="stylesheet" href="/css/layout/nav.css"> -->
<%@ include file="/WEB-INF/view/include/header.jsp"%>



<section class="title">
	<h1>주문 상세</h1>
</section>

<main>
	<div class="detail_box">
		<div class="order_cont">
			<div>${orderListDetail.deleveryStatus }</div>
			<div class="store_name">${orderListDetail.storeName }</div>

			<div class="order_info">
				<div>
					주문일시 :
					<fm:formatDate value="${orderListDetail.orderDate }"
						pattern="yyyy년 MM월 dd일 (E) a hh:mm" />
				</div>
				<div>주문번호 : ${orderListDetail.orderNum }</div>
			</div>
		</div>

		<ul class="order_menu">
			<c:set value="0" var="sum"></c:set>
			<c:set value="0" var="sum"></c:set>



			<c:forEach begin="0" end="${fn:length(cart) - 1}" var="i">
				<li>
					<div><span>${cart[i].foodName } ${amount[i] }개</span></div>
					
					<ul>
						<li>기본가격 <fm:formatNumber value="${cart[i].foodPrice }" />원</li>

						<c:set var="foodTotalPrice" value="${cart[i].foodPrice }" />
						<c:if test="${fn:length(cart[i].foodOptionName) != 0}">
							<c:forEach begin="0" end="${fn:length(cart[i].foodOptionName) -1 }" var="j">
								<li>
									<span>${cart[i].foodOptionName[j] }</span> 
									<span><fm:formatNumber value="${cart[i].foodOptionPrice[j] }" pattern="###,###" />원</span>
								</li>
								
								
								<c:set var="foodTotalPrice" value="${foodTotalPrice + cart[i].foodOptionPrice[j] }" />
							</c:forEach>
						</c:if>
						<li class="menu_price_sum"><fm:formatNumber value="${foodTotalPrice }" pattern="###,###" />원</li>
					</ul>
					<hr>
				</li>
			</c:forEach>
		</ul>


		<div class="price">
			<div>
				<span>총 주문금액 </span><span><fm:formatNumber value="${orderListDetail.totalPrice }" pattern="###,###" />원</span>
			</div>
			<div>
				<span>배달팁 </span><span><fm:formatNumber value="${orderListDetail.deleveryTip }" pattern="###,###" />원</span>
			</div>
			<div>
				<c:if test="${orderListDetail.usedPoint != 0 }">
					<span>포인트 사용 </span>
					<span>-<fm:formatNumber value="${orderListDetail.usedPoint }" pattern="###,###" />원</span>
				</c:if>
			</div>
			<hr>
		</div>

		<div class="total">
			<div>
				<span>총 결제금액 </span>
				<span class="sum"><fm:formatNumber value="${orderListDetail.totalPrice + orderListDetail.deleveryTip - orderListDetail.usedPoint  }" pattern="###,###" />원</span>
			</div>
			
			<div>
				<span>결제방법 </span><span>${orderListDetail.payMethod }</span>
			</div>
		</div>
		<hr>
		<hr>


		<div class="address">
			<div>배달주소</div>
			<ul>
				<li>${orderListDetail.deleveryAddress1 }</li>
				<li>${orderListDetail.deleveryAddress2 }</li>
				<li>${orderListDetail.deleveryAddress3 }</li>
			</ul>
			<hr>

		</div>

		<div>
			<div>전화번호</div>
			<div>${orderListDetail.phone }</div>
			<hr>

		</div>

		<div>
			<div>요청사항</div>
			<div>${orderListDetail.request }</div>
			<hr>

		</div>
	</div>
</main>



<!-- 하단 메뉴 -->
	<%-- <%@ include file="/WEB-INF/view/include/nav.jsp"%> --%>
<!-- 하단 메뉴 -->

<!-- 푸터 -->
	<%@ include file="/WEB-INF/view/include/footer.jsp"%>
<!-- 푸터 -->


</body>
</html>