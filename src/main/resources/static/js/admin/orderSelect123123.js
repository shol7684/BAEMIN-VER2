console.log("location.host = " + location.host);

let ws;

connect();

function connect() {
	/*ws = new WebSocket("ws://localhost:8080/chating");*/
	ws = new WebSocket("ws://211.247.99.55:8080/chating");  

	ws.onopen = function(data) {
		//소켓이 열리면 동작
		console.log("소켯 오픈");
	}

	ws.onmessage = function(data) {
		//메시지를 받으면 동작
		console.log("메시지 도착");
		console.log(data.data);
		
		// 현재 주문수가 20이하일때 추가하기
		const orderCount = $(".order_list li").length; 
		console.log("orderCount = " + orderCount);

		const orderNum = data.data;

		$.ajax({
			url: "/admin/order-one",
			type: "GET",
			data: { orderNum, orderNum },
			success: function(result) {
				console.log(result);

				const count1 = result["orderListDetail"]["count1"];
				const count2 = result["orderListDetail"]["count2"];
				const count3 = result["orderListDetail"]["count3"];
				$(".wait_count").text(count1);
				$(".processing_count").text(count2);
				$(".aside_tab li").eq(2).find("div").eq(1).text(count3);

				let html = "";

				const orderListDetail = result["orderListDetail"];
				const amount = result["amount"];
				const cart = result["cart"];

				let foodInfo = " ";

				for (var j = 0; j < cart.length; j++) {
					let foodOptionName = "";
					if (cart[j]["foodOptionName"] != null) {
						foodOptionName = "[" + cart[j]["foodOptionName"] + "]";
					}
					foodInfo += cart[j]["foodName"] + foodOptionName + ", ";
				}

				if (foodInfo.endsWith(" ")) {
					foodInfo = foodInfo.substring(0, foodInfo.length - 2);
				}



				html += `<li class="order_box">
							<div class="time">
				    			<div>${moment(orderListDetail["orderDate"]).format("MM월 DD일")}</div>
				    			<div>${moment(orderListDetail["orderDate"]).format("hh시 mm분")}</div>
				    		</div>
			   	
				    		<div class="info">
	                      		<div style="font-weight: bold;">
	                   			<span>
	                  				<span>[메뉴  ${amount.length}개] ${orderListDetail["totalPrice"]}원</span> 
	                  				<span class="payMethod"> ${orderListDetail["payMethod"]}</span>
	                			</span>
	                   		</div>
			                        		
	                   		<div style="font-weight: bold;">${foodInfo} </div>
	                   		<div style="font-weight: bold;">${orderListDetail["deleveryAddress2"]}</div>
	                   		
	                   		<div>${orderListDetail["storeName"]}</div> 
			                 			
			                <div class="button_box">
	                      		<input type="hidden" value="${orderListDetail["orderNum"]}" class="order_num" >
	                      		<input type="hidden" value="${orderListDetail["deleveryAddress2"]}" class="delevery_address2" >
	                      		<input type="hidden" value="${orderListDetail["deleveryAddress3"]}" class="delevery_address3" >
	                      		<input type="hidden" value="${orderListDetail["phone"]}" class="phone" >
	                      		<input type="hidden" value="${orderListDetail["request"]}" class="request" >
	                      		<input type="hidden" value="${amount}" class="amount" >
	                      		<input type="hidden" value="${foodInfo}" class="food_info" >
	                      		<input type="hidden" value="${orderListDetail["userId"]}" class="user_id" >
			                 	<input type="button" value="${"주문 접수"}" class="order_accept btn">
			                 </div>
						</li>`;


				$(".order_list > ul").append(html);
			},
			fail: function() {
				alert("실패");
			}
		})


	}

	ws.onclose = function(event) {
		console.log("클로즈");
	}

	ws.onerror = function(event) {
		console.log("에러");
	}
}

