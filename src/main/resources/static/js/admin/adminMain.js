

const listArr = ["주문접수 대기 중", "처리 중", "완료"];
let nowList = listArr[0];

// 주문 완료 메세지 받기

const socket = new SockJS('/websocket');

const stompClient = Stomp.over(socket);

stompClient.connect({}, function() {

	stompClient.subscribe('/topic/order-complete', function(message) {
		// 화면에 출력중인 view 갯수 
		const list = $(".order_list li").length;
		
		if(list < 20) {
			orderList(listArr[0]);
		}
	});
});
	
	
	
let size = window.innerWidth;

$(window).off().resize(function() {
	size = window.innerWidth
	$("header .menu_tab").removeClass("active");
	if(size > 1024) {
		$(".tab").show();
	} else {
		$(".tab").hide();
	}
})

$(".menu_tab").click(function(){
	if($(".tab").css("display") == "none") {
		$(this).addClass("active");
		$(".tab").fadeIn();
	} else {
		$(this).removeClass("active");
		$(".tab").fadeOut();
	}
});



$(".aside_tab li").click(function(){
	$(".order_list").html("");
	$(".aside_tab li").removeClass("active");
	$(this).addClass("active");
	const index = $(this).index() 
	nowList = listArr[index];
	
	orderList(nowList);
	run = false;
	page = 2;
})


let winHeight = 0;
let docHeight = 0;
let page = 2;
let run = false;

$(window).scroll(function(){
	winHeight = $(window).height();
	docHeight = $(document).height();
	
	const top = $(window).scrollTop();
	
	if(docHeight <= winHeight + top + 10 ) {
		if(run) {
			return;
		}
		run = true;
		
		orderList(nowList, page);
		page ++;
		
	} // if
}) // scroll


orderList(listArr[0]);

function orderList(list, nextPage){
	let url = "/admin/orderList";
	let page = 1;
	
	if(nextPage) {
		page = nextPage;
		url = "/admin/orderNextPage"
	}
	
	$.ajax({
		url: url,
		type: "get",
		data: {
			list : list,
			page : page
		}	
	})
	.done(function(result){
		if(result != "") {
			const count1 = result[0]["orderListDetail"]["count1"];
			const count2 = result[0]["orderListDetail"]["count2"];
			$(".wait_count").text(count1);
			$(".processing_count").text(count2);
			waitCount = count1;
		}
		
		const html = htmlWrite(result, list);
		
		console.log("nextPage = " + nextPage);
		if(nextPage) {
			$(".order_list").append(html);	
		} else {
			$(".order_list").html(html);	
		}
		
		if(result != "") {
			run = false;
		}
		
	})
	.fail(function(){
		alert("에러가 발생했습니다");
	})	 // ajax
}	


function htmlWrite(result, list){
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
		
		let btnValue = "";
		let btnClass = "";
		if(list == '주문접수 대기 중') {
			btnValue = "주문 접수";
			btnClass = "order_accept";
		} else if(list == '처리 중') {
			btnValue = "완료";
			btnClass = "complete";
		}
		
		html += 
			`<li class="order_box">
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
	            </div>     	
	            		
                <div class="button_box">
              		<input type="hidden" value="${orderListDetail["orderNum"] }" class="order_num" >
              		<input type="hidden" value="${orderListDetail["deleveryAddress2"] }" class="delevery_address2" >
              		<input type="hidden" value="${orderListDetail["deleveryAddress3"] }" class="delevery_address3" >
              		<input type="hidden" value="${orderListDetail["phone"] }" class="phone" >
              		<input type="hidden" value="${orderListDetail["request"] }" class="request" >
              		<input type="hidden" value="${amount }" class="amount" >
              		<input type="hidden" value="${foodInfo}" class="food_info" >
              		<input type="hidden" value="${orderListDetail["userId"] }" class="user_id" >
                 	<input type="button" value="${btnValue}" class="${btnClass} btn">
                 </div>
			</li>`;
	}
	return html;
}








let orderNum = 0;
let userId = 0;
	
$(document).on("click", ".order_accept", function(){
	const modal = $(".order_accept_modal");
	const orderIndex = $(this).parent().parent().parent("li").index();
	console.log("orderIndex = " + orderIndex);
	
	
	orderNum = $(this).siblings(".order_num").val();
	userId =  $(this).siblings(".user_id").val();
	
	const deleveryAddress2 = $(this).siblings(".delevery_address2").val();
	let deleveryAddress3 = $(this).siblings(".delevery_address3").val();
	let request = $(this).siblings(".request").val();
	const phone = $(this).siblings(".phone").val();
	const amount = $(this).siblings(".amount").val().split(",");
	const foodInfo = $(this).siblings(".food_info").val().split(", ");
	
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
	
	
	
	// 배달시간 설정 모달
	$(".delevery_timer_btn").off().click(function(){
		openModal($(".delevery_timer_modal"), size);
	})
 		
	// 시간 설정	
	$(".delevery_timer_modal li").off().click(function(){
		$(".delevery_timer_modal li").removeClass("select");
		$(this).addClass("select");
	})
		
	// 주문수락 완료	
	$(".accept").off().click(function(){
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
			type: "PATCH"
		})
		.done(function(){
			$(".order_list li").eq(orderIndex).remove();
			$(".delevery_timer_modal li").removeClass("select");
			$(".delevery_timer_modal section li[data-time=30]").addClass("select");
			
			updateCount();
			swal("주문접수완료");
			closeModal();
		})
		.fail(function(){
			swal("실패");
		})
		
	})
	
	
	// 주문 거부하기
	$(".order_cancle_btn").off().click(function(){
		openModal($(".order_cancle_modal"), size);
		
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
				type: "PATCH",
				data: data
			})
			.done(function(){
				$(".order_list li").eq(orderIndex).remove(); 
				updateCount();
				swal("취소완료");
				closeModal();
				
			})
			.fail(function(){
				swal("실패");
			})
		})
	})
})



	

// 배달 완료	
$(document).on("click", ".complete", function(){
	const orderIndex = $(this).parent().parent().parent("li").index();
	orderNum = $(this).siblings(".order_num").val();
	userId =  $(this).siblings(".user_id").val();
	console.log(userId);
	console.log(orderNum);
	console.log(orderIndex);
	const data = {
		userId : userId,
		orderNum : orderNum
	}
	
	swal("배달 완료후 눌러주세요", {
		  buttons: ["취소", "완료"],
	})
	.then(function(value){
		if(!value) {
			return;
		}
		
		$.ajax({
			url: "/admin/orderComplete",
			type: "PATCH",
			data: data
		})
		.done(function(result){
			console.log(result);
			$(".order_list > li").eq(orderIndex).remove();
		})
		.error(function(){
			swal("에러");
		})
	}) 
})
	
	
$(".move_top").click(function(){
	$("html").animate({ scrollTop: 0 }, 100);
})
	
	
	
function updateCount(){
	const waitCount = Number($(".wait_count").text());
	const procCount = Number($(".processing_count").text());
	$(".wait_count").text(waitCount - 1 );
	$(".processing_count").text(procCount + 1 );
}



function closeModal() {
	$("#modal_bg").hide();
	$(".modal").css("top", "100%");
	$(".modal_box").scrollTop(0);
	$("body").css("overflow", "visible");
	$("input[type='checkBox']").prop("checked", false);
	
	$(".delevery_timer_modal li").removeClass("select");
	$(".delevery_timer_modal section li[data-time=30]").addClass("select");
	$(".order_cancle_modal li").removeClass("select");
	
	
};
	
