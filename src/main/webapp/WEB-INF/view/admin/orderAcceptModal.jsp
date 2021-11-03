<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/orderAcceptModal.css" >
    
    
	<div id="modal_bg"></div>
	
 	<div class="order_accept_modal modal">
		<div id="modal_header">
			<h1>접수 대기</h1>    
			<button class="closeA"><i class="fas fa-times"></i></button>
		</div>
             
		<div class="modal_box">
			<section>
				<article class="delevery_info">
					<h2>배달 정보</h2>
					
					<button class="order_cancle_btn">주문거부하기</button>
					
	                <div class="info_area">
	                    <div>
	                    	<div>배송지</div>
	                    	<div>연락처</div>
	                    </div>
	                    
	                    <div class="delevery_address">
	                    	<div>천안시 서북구</div>
	                    	<div>상세주소</div>
	                    	<div>01012345678</div>
	                    </div>
	                </div>
	            </article>
	
	            <article class="request">
	                <h2>요청사항</h2>
	                <div>수저 필요없어요</div>    
	            </article>
	            
	            <article class="menu">
	                <h2>주문 상세</h2>
	                <ul>
	                    <!-- <li>
	                    	<span>메뉴 이름  1000원 1개</span>
	                    </li>  -->
	                </ul>    
	            </article>
	        </section>
		</div>
	        
	    <div id="btn_box">
	        <button class="closeB">취소</button>
	        <button class="delevery_timer_btn">주문 접수</button>
	    </div>
</div>

