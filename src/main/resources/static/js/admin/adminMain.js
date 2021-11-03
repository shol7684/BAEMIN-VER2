

$(document).ready(function(){
	
let size = $(window).width();

$(window).resize(function() {
	size = $(window).width();
})


$(".aside_tab li").click(function(){
	$(".aside_tab li").removeClass("active");
	$(this).addClass("active");
	const index = $(this).index() 
	orderList(index);
})




orderList(0);

function orderList(index){
	const list = ["주문접수 대기 중", "처리 중", "완료"];
	
	$.ajax({
		url: "/admin/orderList",
		type: "get",
		data: {list : list[index]},
		success: function(result) {
			
			if(result != "") {
				const count1 = result[0]["orderListDetail"]["count1"];
				const count2 = result[0]["orderListDetail"]["count2"];
				const count3 = result[0]["orderListDetail"]["count3"];
				$(".wait_count").text(count1);
				$(".processing_count").text(count2);
				$(".aside_tab li").eq(2).find("div").eq(1).text(count3);
			}
			
			let html = "";
			for(var i=0;i<result.length;i++) {
				const orderListDetail = result[i]["orderListDetail"];
				const amount = result[i]["amount"];
				const cart = result[i]["cart"];
				
				let foodInfo =" ";
				for(var j=0;j<cart.length;j++) {
					let foodOptionName = "";
					if(cart[j]["foodOptionName"] != null) {
						foodOptionName = "[" + cart[j]["foodOptionName"] + "]"; 
					}
					foodInfo += cart[j]["foodName"] + foodOptionName + ", ";
				}
				
				if(foodInfo.endsWith(" ")) {
					foodInfo = foodInfo.substring(0, foodInfo.length-2);
				}
				
				
				html += `<li class="order_box">
						<div class="time">
			    			<div>${moment(orderListDetail["orderDate"]).format("MM월 DD일")}</div>
			    			<div>${moment(orderListDetail["orderDate"]).format("HH시 mm분")}</div>
			    		</div>
		   	
			    		<div class="info">
                      		<div style="font-weight: bold;">
	                   			<span>
	                  				<span>[메뉴  ${amount.length}개] ${orderListDetail["totalPrice"]}원</span> 
	                  				<span class="payMethod"> ${orderListDetail["payMethod"] }</span>
	                			</span>
                   			</div>
		                        		
	                   		<div style="font-weight: bold;">${foodInfo } </div>
	                   		<div style="font-weight: bold;">${orderListDetail["deleveryAddress2"] }</div>
	                   		
	                   		<div>${orderListDetail["storeName"] }</div> 
			                 			
			                <div class="button_box">
	                      		<input type="hidden" value="${orderListDetail["orderNum"] }" class="order_num" >
	                      		<input type="hidden" value="${orderListDetail["deleveryAddress2"] }" class="delevery_address2" >
	                      		<input type="hidden" value="${orderListDetail["deleveryAddress3"] }" class="delevery_address3" >
	                      		<input type="hidden" value="${orderListDetail["phone"] }" class="phone" >
	                      		<input type="hidden" value="${orderListDetail["request"] }" class="request" >
	                      		<input type="hidden" value="${amount }" class="amount" >
	                      		<input type="hidden" value="${foodInfo}" class="food_info" >
	                      		<input type="hidden" value="${orderListDetail["userId"] }" class="user_id" >
			                 	<input type="button" value="${"주문 접수"}" class="order_accept btn">
			                 </div>
			            </div>     
					</li>`;
			}
			$(".order_list").html(html);
		},
		error: function() {
			alert("에러가 발생했습니다");
		}
	}) // ajax
	
}	


let orderNum = 0;
let userId = 0;
	
$(document).on("click", ".order_accept", function(){
	const modal = $(".order_accept_modal");
	const orderIndex = $(this).parent().parent().parent("li").index();
	
	orderNum = $(this).siblings(".order_num").val();
	userId =  $(this).siblings(".user_id").val();
	
	const deleveryAddress2 = $(this).siblings(".delevery_address2").val();
	let deleveryAddress3 = $(this).siblings(".delevery_address3").val();
	let request = $(this).siblings(".request").val();
	const phone = $(this).siblings(".phone").val();
	const amount = $(this).siblings(".amount").val().split(",");
	const foodInfo = $(this).siblings(".food_info").val().split(", ");
	
	console.log($(this).siblings(".food_info").val());
	
	if(deleveryAddress3 =="null") deleveryAddress3 = "";
	if(request =="null") request = "";
	
	const addressHtml = `<div>${deleveryAddress2}</div>
                    	<div>${deleveryAddress3}</div>
                    	<div>${phone}</div>`
	
	menuHtml = "";
	for(var i=0;i<foodInfo.length;i++) {
		menuHtml += `<li>${foodInfo[i] }  ${amount[i]}개 </li>`;
	}
	
	modal.find(".delevery_address").html(addressHtml);
	modal.find(".request > div").text(request);
	modal.find(".menu ul").html(menuHtml);
	
	
	openModal(modal, size);
	
	console.log(orderIndex);
	
	
	
	// 배달시간 설정 모달
	$(".delevery_timer_btn").off().click(function(){
		const modal = $(".delevery_timer_modal");
		openModal(modal, size);
		
		console.log("orderNum = " + orderNum);
	})
 		
	// 시간 설정	
	$(".delevery_timer_modal li").off().click(function(){
		$(".delevery_timer_modal li").removeClass("select");
		$(this).addClass("select");
		
		console.log($(".select").data("time"));
		console.log($(".select").data());
	})
		
	// 주문수락 완료	
	$(".accept").off().click(function(){
		cs(userId);
		const time = $(".delevery_timer_modal .select").data("time");
		
		if(!time) {
			swal("시간을설정해주세요");
			return;
		}
		
		const data = {
			orderNum : orderNum,
			time : time,
			userId : userId
		}
		
		$.ajax({
			url: "/admin/orderAccept",
			data: data,
			type: "POST",
			success: function(){
				$(".delevery_timer_modal li").removeClass("select");
				$(".delevery_timer_modal section li[data-time=30]").addClass("select");
				
				const waitCount = Number($(".wait_count").text());
				const procCount = Number($(".processing_count").text());
				$(".wait_count").text(waitCount - 1 );
				$(".processing_count").text(procCount + 1 );
				
				swal("주문접수완료");
				closeModal();
			},
			error: function() {
					swal("실패");
				},
			complete: function(result){
				if(result.status != 200) {
					swal("해당 주문번호가 없습니다");
				} else {
					$(".order_list > li").eq(orderIndex).remove();
				}
			}
		})
	})
	
	
	// 주문 거부하기
	$(".order_cancle_btn").off().click(function(){
		const modal = $(".order_cancle_modal");
		
		openModal(modal, size);
		
		let cancleReason = "";
		
		// 거부사유 선택
		$(".order_cancle_modal li").off().click(function(){
			$(".order_cancle_modal li").removeClass("select");	
			$(this).addClass("select");
			cancleReason = $(this).data("reason");
		})
		
		// 거부하기
		$(".order_cancle").off().click(function(){
			if(!cancleReason) {
				swal('주문거부 사유를 선택해주세요');
				return;
			}
			
			const data = {
				orderNum : orderNum,
				cancleReason : cancleReason,
				userId : userId
			}
			
			$.ajax({
				url: "/admin/orderCancle",
				type: "POST",
				data: data,
				success: function(result){
					$(".order_cancle_modal li").removeClass("select");
					
					const waitCount = Number($(".wait_count").text());
					const procCount = Number($(".processing_count").text());
					$(".wait_count").text(waitCount - 1 );
					$(".processing_count").text(procCount + 1 );
					swal("취소완료");
					closeModal();
				},
				error: function() {
					swal("실패");
				},
				complete: function(result){
					if(result.status != 200) {
						swal("해당 주문번호가 없습니다");
					} else {
						$(".order_list > li").eq(orderIndex).remove();
					}
				}
			})
			
			
		})
	})
})
	
	
	
$(".move_top").click(function(){
	$("html").animate({ scrollTop: 0 }, 100);
})
	

	


function cs(value){
	console.log(value);
}
	


})