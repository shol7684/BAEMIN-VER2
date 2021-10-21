<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
	
	
    <!-- <script type="text/javascript" src="/js/imgPreview.js" ></script> -->
    
    
    
    
    <!-- <link rel="stylesheet" href="/css/storeRegiModal.css" > -->
    <link rel="stylesheet" href="/css/admin/admin.css" >
    <link rel="stylesheet" href="/css/modal.css" >
<%@ include file="/WEB-INF/view/include/header.jsp" %>


    <!-- 매장 등록 모달창 -->
	<%-- <%@ include file="/WEB-INF/view/include/storeRegiModal.jsp" %> --%>
 	<!-- 매장 등록 모달창 -->

 	<!-- -------------------------------------------------------------------------------------------- -->
		
	

<div class="wrap">
<!-- ------------------------ 상단 메뉴 ------------------------->
    <section class="tab">
         <ul class="box">
			<li class="store_management"><a href="/admin/storeManagement">매장 관리</a></li>
			<li class="sales_check"><a href="#">매출 확인</a></li>
			<li class="home"><a href="/">홈으로</a></li>
		</ul>
    </section>

<!-- ------------------------ 상단 메뉴 ------------------------->

<!-- ------------------------ 왼쪽 메뉴 ------------------------->
    <aside>
        <ul class="aside_tab">
            <li class="active">
            	<div>접수 대기</div>
           	</li>
           	
            <li>처리 중</li>
            <li>완료</li>
            <!-- <li>주문 조회</li> -->
         </ul>
    </aside>
<!-- ------------------------ 왼쪽 메뉴 ------------------------->



    <main>
        <div id="cont_box">
            <ul>
                <!-- 주문접수 > 접수 대기 -->
                <li class="order_list first">
                    <ul>
                        <!-- 주문목록 1개 -->
                        <li class="order_box">
						</li>
		 						
                        <!-- 주문목록 1개 -->
                    </ul>
                </li>
                   <!-- 주문접수 > 접수 대기 -->


                    <!-- 주문접수 > 처리 중  -->
				<li class="order_list second">
					<ul>
                   </ul>
                </li> 
               
                    <!-- 주문접수 > 처리 중 -->

                    <!-- 주문접수 > 완료 -->
				<li class="order_list third">
					<ul>
					</ul>
				</li>
                    <!-- 주문접수 > 완료 -->
							
						
                    <!-- 주문접수 > 주문조회 -->
   
                    <!-- 주문접수 > 주문조회 -->
            </ul>
        </div>
        </main>
<!-- ------------------------ 매장관리 눌렀을대 ------------------------->
        
        
<!-- ------------------------ 매장관리 눌렀을대 ------------------------->
    
</div>

   	<!-- 푸터 -->
     <footer>
		<%@ include file="/WEB-INF/view/include/footer.jsp" %>
    </footer> 
	<!-- 푸터 -->

	<!-- ------------------------접수모달 ------------------------->
 	<div class="accept_modal modal">
		<div id="modal_header">
			<h1>접수 대기</h1>    
			<button class="closeA">×</button>
		</div>
             
		<div class="accept_modal_box">
			<section>
				<article class="delevery_info">
					<h2>배달 정보</h2>
					<div class="order_cancle"><button>주문거부하기</button></div>
	                <div class="info_area">
	                    <div>
	                        <span>배송지</span>
	                        <span>천안시 서북구 두정동</span>
	                        <span>상세주소 ...................</span>
	                    </div>
		                    
	                    <div>
	                        <span>연락처 010 2885 7684</span>
	                    </div>
	                </div>
	            </article>
	
	            <article class="request">
	                <h2>요청사항</h2>
	                <div>
	                    <textarea>수저 필요없어요</textarea>
	                </div>    
	            </article>
	            <article class="menu">
	                <h2>주문 상세</h2>
	                <ul>
	                    <li>
	                    </li>
	                    
	                </ul>    
	            </article>
	        </section>
		</div>
	        
	    <div class="btn_box">
	        <button class="cancle">취소</button>
	        <button class="accept_next">주문 접수</button>
	    </div>
</div>

<!-- ------------------------접수모달 ------------------------->

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




<!-- ------------------------ 배달시간 ------------------------->
    <div class="delevery_timer modal"> 
    	<div id="modal_header">
	        <h1>접수 대기</h1>
	        <button class="closeA">×</button>
        </div>
             
        <section>
            <article>
                <h2>배달 시간을 선택해주세요</h2>
            </article>

            <article>
                <ul>
                    <li data-value = 20>20분</li>
                    <li data-value = 30 id="select">30분</li>
                    <li data-value = 40>40분</li>
                    <li data-value = 50>50분</li>
                    <li data-value = 60>60분</li>
                    <li data-value = 90>90분</li>
                </ul>
            </article>

            <article>
                <div>
                    <button class="cancle">취소</button>
                    <button id="accept">접수하기</button>
                </div>
                
            </article>
        </section> 
    </div>

<!-- ------------------------ 배달시간 ------------------------->
<script type="text/javascript" src="/js/openModal.js" ></script>
 	<script type="text/javascript" src="/js/admin/adminMain.js" ></script>

</body>
</html>