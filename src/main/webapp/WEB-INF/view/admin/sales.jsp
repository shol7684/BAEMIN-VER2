<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/admin/sales.css" >
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js" integrity="sha512-qTXRIMyZIFb8iQcfjXWCO8+M5Tbc38Qi5WzdPOYZHIlZpzBHG3L3by84BBBOiRGiEb7KKtAOAs5qYdUiZiQNNQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<%@ include file="/WEB-INF/view/include/header.jsp" %>
  
  
	<div id="tab">
		<section class="tab">
	         <ul class="box">
				<li class=""><a href="/admin/main">주문접수</a></li>
				<li class="store_management"><a href="/admin/storeManagement">매장 관리</a></li>
				<li class="sales"><a href="/admin/salesManagement">매출 확인</a></li>
				<li class="order_search"><a href="#">주문번호 검색</a></li>
				<li class="home"><a href="/">홈으로</a></li>
			</ul>
	    </section>
    </div>
    
	<main>
		<button class="year_btn">연 매출</button>
		<button class="month_btn">월 매출</button>
		<button class="week_btn">주간 매출</button>
		<input type="month"name="date" id="date">
		<button class="other_month_search">검색</button>
	
		<h1>1월 총 합계 </h1>
		
		<div>(단위 : 만원)</div>
		<div class="graph_box">
			<ul>
				<li>
					<div class="sales"></div>
					<div class="graph"></div>
					<span class="graph_date"></span>
				</li>
				
			</ul>
		
			<div class="graph_background">
				<div></div>
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	
</main>

 <script src="/js/admin/sales.js"></script> 

<script type="text/javascript">
	


	
	/* 	
	// 일별 매출
	$(".day_btn").click(function(){
		const date = new Date();
		
		const month = date.getMonth() +1;
		
       	salesStatusDay(month);
	})

	// 기본페이지 일별 매출
	$(".day_btn").click();
	
	// 다른달 검색
	$(".other_month_search").click(function(){
		const month = Number($("#date").val().substring(5,7));
		
		salesStatusDay(month,$("#date").val());
		
	})
	
	
	// 월별 매출
	$(".month_btn").click(function(){
		const date = new Date();
		
		const year = date.getFullYear();
		const month = date.getMonth() +1;
		const day = date.getDate();
		
		const monthArr = ["JAN" , "FEB" , "MAR" , "APR" , "MAY" , "JUN" , "JUL" , "AUG" , "SEP" ,"OCT" , "NOB" , "DEC" ];
		
		$("main ul").html("");
		
		let ht ="";
		for(var i=0;i<12;i++) {
			 ht += `<li>
						<div class=\${monthArr[i]}>
							<span></span>
						</div>
						<span class="graph_date">\${i+1}월</span>
					</li>`;
		}
		
		$("main ul").html(ht);
		
		
		$.ajax({
    		url : "/admin/salesStatusDayMonth",
    	    type : "post",
    	    success : function(result){
    	    	
    	    	const total = result["TOTAL"]; // 연 총 합계
    	    	
    	    	$("main h1").html(`\${year}년 총 합계 : \${total.toLocaleString()}원`);
    	    	
    	    	for(var i =0;i<monthArr.length;i++) {
    	    		
    	    		const totalMonth = result[monthArr[i]]; // 월별 합계
    	    		const avg = totalMonth / total * 100; // 월별 평균
    	    		
    	    		if(result[monthArr[i]] != null) {
	    	    		$(`.\${monthArr[i]}`).css("height" , avg+"%");
	    	    		$(`.\${monthArr[i]}`).find("span").html((totalMonth / 10000).toFixed(1).toLocaleString());
    	    		}
    	    	}
			
    		} // success
    	}); // ajax
		
		
	})
	
	
	
	

	function salesStatusDay(month,date){
		$.ajax({
    		url : "/admin/salesStatusDay",
    	    type : "post",
    	    data : {date : date},
    	    success : function(result){
    	    	
    	    	const date = new Date();
    			const year = date.getFullYear();
    			
    			const lastDate = new Date(year,month,0 );
    			const lastDay = lastDate.getDate();
    			
    	    	$("main ul").html("");
    	    	
    	    	let ht ="";
    	    	
       			for(var i=0;i<lastDay;i++) {
       				 ht += `<li>
								<div class="graph">
									<span></span>
								</div>
       							<span class="graph_date">\${i+1}</span>
       						</li>`;
       			}
       			
       			$("main ul").html(ht);
       			
       			
    	    	if(result != "") {
	       			
					const total = result[0]["total"];
					
	       	    	$("main h1").html(`\${month}월 총 합계 : \${total.toLocaleString()}원`);
	    	    	
					for(var i=0;i<result.length;i++) {
	       	    		
						const orderDate = result[i]["orderDate"]; 
	        	    	const dayTotal = result[i]["dayTotal"];
	        	    	const avg = dayTotal / total * 100;
	        	    	const dateIndex = Number(orderDate.substring(orderDate.length-2) -1);
						
						$("main li").eq(dateIndex).find(".graph").css("height" , avg +"%");
						$("main li").eq(dateIndex).find(".graph").find("span").html((dayTotal / 10000).toFixed(1).toLocaleString());
    	    		}
					
       	    	} else {
       	    		$("main li").find(".graph").css("height" , "3px");
					$("main li").find(".graph").find("span").html("");
					
					$("main h1").html(`\${month}월 총 합계 : 0원`);
       	    	}
			
    		} // success
    	}); // ajax
	}
 */
</script>

</body>
</html>