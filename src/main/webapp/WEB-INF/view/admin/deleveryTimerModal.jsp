<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/css/admin/deleveryTimerModal.css" >


<div class="delevery_timer_modal modal">
	<div id="modal_header">
		<h1>접수 대기</h1>
		<button class="closeA">×</button>
	</div>

	<div class="modal_box">
		<h2>배달 시간을 선택해주세요</h2>
		
		<section>
			<ul>
				<li data-time=20>20분</li>
				<li data-time=30 class="select">30분</li>
				<li data-time=40>40분</li>
				<li data-time=50>50분</li>
				<li data-time=60>60분</li>
				<li data-time=90>90분</li>
			</ul>
		</section>
	</div>
	
	<div id="btn_box">
        <button class="closeB">취소</button>
        <button class="accept">접수하기</button>
    </div>
	    
</div>

