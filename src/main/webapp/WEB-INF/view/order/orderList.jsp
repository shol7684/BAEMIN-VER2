<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
 
<script type="text/javascript" src="/js/modal.js" ></script>
	<link rel="stylesheet" href="/css/modal.css" >
	<link rel="stylesheet" href="/css/layout/nav.css" >
	<link rel="stylesheet" href="/css/order/orderList.css" >
    
<%@ include file="/WEB-INF/view/include/header.jsp" %>

    
	<div class="wrap">
    
		<c:if test="${!empty orderList }">
		    <section class="title">
		        <h1>주문 내역</h1>
		    </section>
		</c:if>    
	
	    <main>
	        <div class="order_list">
	         	<c:if test="${empty orderList }" >
	        		<img alt="이미지" src="/img/temp.png" class="temp_img"> 
	        	</c:if> 
	        	
	        	
	        	<ul id="order_list">
	                <c:forEach items="${orderList }" var="orderList" >
	                    <li>
	                    	
	                    
	                    	<div class="delevery_state">
	                    		<span><fm:formatDate value="${orderList.orderDate }" pattern="M월d일" /> </span>
	                    		
	                    			<span>${orderList.deleveryStatus }</span> 
	                    		<c:if test="${orderList.deleveryStatus == '처리 중' }"> 
	                    		</c:if>
	                    		
	                    		 
	                    	</div>
	                    	
	                        <a href="/store/detail/${orderList.storeId }" class="store_move">
	                        	<span><img src="${orderList.storeImg }" alt="이미지"></span>
	                            <span class="inf">
	                            	<h2> ${orderList.storeName }</h2><br>
	                                <span><fm:formatNumber value="${orderList.totalPrice + orderList.deleveryTip - orderList.usedPoint }" pattern="###,###" /> 원</span>
	                             </span>
	                        </a>
	                        
	                        <div class="review_btn_box">
	                        	<input type="hidden" value="${orderList1.storeNum }" class="detail_store_num">
                    			<input type="hidden" class="order_num" value="${orderList.orderNum }">
		                        <button class="order_detail">상세보기</button>
		                        <c:if test="${!empty user }">
		                        
			                        <c:if test="${orderList1.reviewCount == 1 && orderList1.deleveryState == '배달 완료'   }">
			                        	<button class="review regi">리뷰쓰기</button>
			                        </c:if>
			                        <c:if test="${orderList1.reviewCount == 0 && orderList1.deleveryState == '배달 완료'   }">
			                        	<button class="review modify">리뷰 수정하기</button>
			                        </c:if>
			                        	<input type="hidden" class="store_num" value="${orderList1.storeNum }">
		                    			<input type="hidden" class="order_num" value="${orderList1.orderNum }">
		                    			<input type="hidden" class="store_img" value="${orderList1.storeImg }">
		                        </c:if>
	                        </div>
						</li>
					</c:forEach>
	            </ul>
	
	        </div>
	           
	    </main>
</div>

    <!-- 하단 메뉴 -->
	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="/WEB-INF/view/include/footer.jsp" %>
    <!-- 푸터 -->
	
	
		
    <div class="modal_bg"></div>

<!-- --------------------------------- 리뷰 쓰기 모달 --------------------------------- -->    
    <div class="review_modal modal">
    <form action="/store/review" method="post"  enctype="multipart/form-data">
    	<div id="modal_header">
    		<h1>리뷰 쓰기</h1>
		    <span class="closeA">×</span>
    	</div>
    	
    	<div class="modal_box">
    	
	    	<div class="score_box">
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i> 
	    	</div>
	    	
	    	
	    	
	    	<input type="hidden" name="score" value="" class="score" >
	    	
	    	<div class="review_text">
	    		<textarea rows="10" cols="50" name="content"></textarea>
	    	</div>
	    	
	    	<div class="img_box">
	    		<input type="file" class="img" name="img">
	    		<div>
	    			<img class="preview">
	    			<button type="button" class="img_close"> x</button>
    			</div>
	    	</div>
    	</div>
    	
    	<div class="btn_box">
 			<button type="button" class="closeB">취소</button>
    		<input type="submit" value="리뷰 작성"  class="submit_btn" disabled >
    		
    		<input class="store_num" type="hidden" value="" name="storeNum">
    		<input class="order_num" type="hidden" value="" name="orderNum">
    	</div>
    	
    	</form>
    </div>
<!-- --------------------------------- 리뷰 쓰기 모달 --------------------------------- -->  
  
<!-- --------------------------------- 리뷰 수정하기 모달 --------------------------------- -->    
    <div class="review_modify_modal modal">
    <form action="/store/reviewModify" method="post"  enctype="multipart/form-data">
    	<div id="modal_header">
	    	<h1>리뷰 수정하기</h1>
		    <span class="closeA">×</span>
    	</div>
    	
    	
    	<div class="modal_box">
    	
	    	<div class="score_box">
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i>
				<i class="far fa-star"></i> 
	    	</div>
	    	
	    	
	    	
	    	<input type="hidden" name="score" value="" class="score" >
	    	
	    	<div class="review_text">
	    		<textarea rows="10" cols="50" name="content"></textarea>
	    	</div>
	    	
	    	<div class="img_box">
	    		<input type="file" class="modify_img" name="img" value="" >
	    		<div>
	    			<img class="preview">
	    			<button type="button" class="img_close"> x</button>
    			</div>
    			
	    	</div>
    	</div>
    	
    	<div class="btn_box">
    		<button type="button" class="closeB">취소</button>
    		<input type="submit" value="리뷰 수정하기"  class="submit_btn" disabled >
    		
    		<input class="store_num" type="hidden" value="" name="storeNum">
    		<input class="order_num" type="hidden" value="" name="orderNum">
    		<input class="store_img" type="hidden" value="" name="modifyImg">
    	</div>
    	
    	</form>
    </div>
<!-- --------------------------------- 리뷰 쓰기 모달 --------------------------------- -->    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    <script type="text/javascript">
	$(document).ready(function(){
		
		/* let orderList = "${orderList[0].storeName }";
		if(orderList != null && orderList != "") {
			$("body").css("background" , "#fff");
		} */
		
		
		var link = document.location.href;
		
		console.log(link);
			
		
		
		$(".submit_btn").attr("disabled" , true);
		

		
/* --------------------------------- 리뷰 등록 --------------------------------- */		
		$(".review").click(function(){
			var size = $(window).width();
			
			console.log(size);
			
			
			if($(this).hasClass("regi")) {
				console.log("ok");
				modal = $(".review_modal");
			} else {
				modal = $(".review_modify_modal");
			}
			
			modalBg.show();
			
			if(size > 767) {
				modal.show().css("top","0%");
			} else {
				modal.show().css("transition","0.2s").css("top","0%");
			}				
			
			
			$("body").css("overflow","hidden");
			
			var storeNum = $(this).siblings(".store_num").val();
			var orderNum =  $(this).siblings(".order_num").val();
			console.log(storeNum);
			console.log(orderNum);
			
			$(".store_num").val(storeNum);
			$(".order_num").val(orderNum);
			
			
		});
		
		var text = $(".review_text textarea");
		var score = $(".review_modal .score");
		
		text.keyup(function(){
			inputCheck("review");
		})
		
/* --------------------------------- 리뷰 등록 --------------------------------- */

/* --------------------------------- 작성 했는지 체크 --------------------------------- */

		
	function inputCheck() {
		console.log(2);
		var text = $(".review_text textarea");
		var score = $(".score");
		
		if(text.val() != null && score.val() != "" ) {
			$(".submit_btn").css("background" , "#30DAD9");
			$(".submit_btn").attr("disabled" , false);
			console.log(3);
			
		} else {
			$(".submit_btn").css("background" , "#ddd");
			$(".submit_btn").attr("disabled" , true);
			console.log(4);
		} 
	}
/* --------------------------------- 작성 했는지 체크 --------------------------------- */		
		
/* --------------------------------- 별점 주기 --------------------------------- */		
	var score = 0;
	
	$(".score_box i").click(function(){
		score = $(this).index() + 1 ;
		
		$(".score_box i").removeClass("fas");
		$(this).addClass("fas").prevAll().addClass("fas");
		
		$(".score").val(score);
		
		inputCheck(); 
	});
/* --------------------------------- 별점 주기 --------------------------------- */	
		
/* -----------------------------이미지 미리보기----------------------------- */
	var file = document.querySelector(".img");
	/*  var file = $(".img_box .img"); */

	file.onchange = function(e) {
	  var fileReader = new FileReader();
	  fileReader.readAsDataURL(e.target.files[0]);


	  fileReader.onload = function(e) { 
		$(".img_box div").css("display" ,"block");
		console.log(e.target.result);
	    $(".preview").attr("src" , e.target.result);

	    fileReader.onload;
	  }
	}
	
	$(".img_close").click(function(){
		imgClose();
	});
		
/* -----------------------------이미지 미리보기----------------------------- */		
/* -----------------------------리뷰 수정 이미지 미리보기----------------------------- */		
	var modifyImg  = document.querySelector(".modify_img"); 

	modifyImg.onchange = function(e) {
	  var fileReader = new FileReader();
	  fileReader.readAsDataURL(e.target.files[0]);


	  fileReader.onload = function(e) { 
		$(".img_box div").css("display" ,"block");
		console.log(e.target.result);
	    $(".preview").attr("src" , e.target.result);

	    fileReader.onload;
	  }
	}
	
	$(".img_close").click(function(){
		$(".preview").attr("src" , "");
		$(".img_box div").css("display" ,"none");
	});
/* -----------------------------리뷰 수정 이미지 미리보기----------------------------- */		
	var modal = $(".modal");
	var closeA = $(".closeA");
	var closeB = $(".closeB");
	var modalBg = $(".modal_bg");
     closeB.click(function(){
    	 closeModal();
     });
     
     modalBg.click(function(){
    	 closeModal();	
     });
     
    closeA.click(function(){
      	 closeModal();
     });		

		
		
		
	function imgClose(){
		$(".preview").attr("src" , "");
		$(".img_box div").css("display" ,"none");
	}				
			
			
    function closeModal(){
		modal.scrollTop(0);
        modalBg.hide();
        modal.css("top" , "100%");
        /* $(".modal .btn_box").css("position" , "static"); */
        
        imgClose();
        
        text.val("");
        score = 0;
        $(".score_box i").removeClass("fas");
        
		$(".submit_btn").css("background" , "#ddd");
		$(".submit_btn").attr("disabled" , true);
        
        $("body").css("overflow" , "visible");
	};
 
 $(".order_detail").click(function(){
	 var orderNum = $(this).siblings(".order_num").val();
	 var storeNum = $(this).siblings(".detail_store_num").val();
	 console.log(orderNum);
	 location.href = "/orderListDetail/" + orderNum; 
 });
 
	}); // ready
    </script>	
	
	
	
	
	
	
	
</body>
</html>