<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
	
	
    <!-- <script type="text/javascript" src="/js/imgPreview.js" ></script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js" integrity="sha512-qTXRIMyZIFb8iQcfjXWCO8+M5Tbc38Qi5WzdPOYZHIlZpzBHG3L3by84BBBOiRGiEb7KKtAOAs5qYdUiZiQNNQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<link rel="stylesheet" href="/css/admin/admin.css" >
<link rel="stylesheet" href="/css/modal.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>

	<div class="move_top">위로</div>

<div class="wrap">
    <section class="tab">
         <ul class="box">
			<li class="store_management"><a href="/admin/storeManagement">매장 관리</a></li>
			<li class="sales_check"><a href="#">매출 확인</a></li>
			<li class="home"><a href="/">홈으로</a></li>
		</ul>
    </section>


    <aside>
        <ul class="aside_tab">
            <li class="active">
            	<div>접수 대기</div>
            	<div></div>
           	</li>
            <li>
            	<div>처리 중</div>
            	<div></div>
            </li>
            
            <li>
            	<div>완료</div>
            	<div></div>
            </li>
            <!-- <li>주문 조회</li> -->
         </ul>
    </aside>



    <main>
        <div id="cont_box">
            <ul>
                <!-- 주문접수 > 접수 대기 -->
                <li class="order_list first">
                    <ul>
                        <!-- 주문목록 1개 -->
                        <!-- <li class="order_box">
						</li> -->
		 						
                        <!-- 주문목록 1개 -->
                    </ul>
                </li>
                   <!-- 주문접수 > 접수 대기 -->


                    <!-- 주문접수 > 처리 중  -->
				<!-- <li class="order_list second">
					<ul>
                   </ul>
                </li>  -->
               
                    <!-- 주문접수 > 처리 중 -->

                    <!-- 주문접수 > 완료 -->
				<!-- <li class="order_list third">
					<ul>
					</ul>
				</li> -->
                    <!-- 주문접수 > 완료 -->
							
						
                    <!-- 주문접수 > 주문조회 -->
   
                    <!-- 주문접수 > 주문조회 -->
            </ul>
        </div>
	</main>
</div>

   	<!-- 푸터 -->
	<%@ include file="/WEB-INF/view/include/footer.jsp" %>
	<!-- 푸터 -->

	

	<div class="order_cancle_modal modal">
		<div id="modal_header">
			<h1>주문거부</h1>
			<button class="closeA">×</button>
		</div>
		
		<div class="modal_box">
			<h2>주문거부 사유를 선택해주세요</h2>
			<ul>
				<li data-value = "배달불가지역" >배달불가지역</li>
				<li data-value = "재료소진" >재료소진</li>
			</ul>
			<ul>
				<li data-value = "배달지연" >배달지연</li>
				<li data-value = "기타" >기타</li>
			</ul>
		</div>
		
		<div class="btn_box">
	        <button class="cancle">취소</button>
	        <button class="order_cancle_btn">거부하기</button>
	    </div>
	</div>

<!-- 주문 접수 모달 -->
<%@ include file="/WEB-INF/view/admin/orderAcceptModal.jsp" %>

<!-- 주문 접수 배달시간 설정 모달 -->
<%@ include file="/WEB-INF/view/admin/deleveryTimerModal.jsp" %>


<script type="text/javascript" src="/js/modal/modal.js" ></script>
<script type="text/javascript" src="/js/admin/adminMain.js" ></script>


</body>
</html>