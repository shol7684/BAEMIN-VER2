

$(document).ready(function() {
const minDelevery = Number($("#min_delevery").data("min_delevery"));
		
	let size = $(window).width();

	$(window).resize(function() {
		size = $(window).width();
	})


	// 다른가게에서 담은 상품인지 확인
	let cartStoreId = null;


	// 찜하기
	$(".inf i").click(function(){
		let likes ="";
		
		if($(this).hasClass("far")) {
			$(this).removeClass("far").addClass("fas");
			likes = "on";
		} else {
			$(this).removeClass("fas").addClass("far");
			likes = "off";
		}
		
		const data = {
			id : $("#store_id").val(),
			likes : likes
		}
		$.ajax({
			url: "/store/likes",
			type: "POST",
			data: data,
			success: function(result){
				
				if(result == 0) {
					
				} else {
					
					let likesCount = $(".likes_count").data("count");
					
					if(likes == "on") {
						$(".likes_count").text(likesCount+1);
						$(".likes_count").data("count", likesCount+1 );
					} else {
						$(".likes_count").text(likesCount-1);
						$(".likes_count").data("count", likesCount-1 );
					}
				}
			} 
		}) // ajax
	}) // 찜





	
	// 장바구니에 담은 메뉴가격 총합
	let menuTotalPrice = 0;

	// 메뉴 클릭시 모달창 
	const food = $(".menu > li .in");
	food.click(function() {
		
		const foodId = $(this).find("#food_id").val();
		
		$.ajax({
			url: "/foodOption",
			type: "get",
			data: {foodId : foodId},
			success: function(result) {
				
				if(result.length == 0) {
					$("#option").hide();
				} else {
					$("#option").show();
				}
				
				let html = "";
				
				for(var i=0;i<result.length;i++) {
					html += `<li>
		                <div class="option_box">
		                	<span>
	                			<i class="fas fa-check-square"></i>
             	 				<input type="checkbox" class="menu_option" name="option" value="${result[i]["optionName"] }"> ${result[i]["optionName"] }
                				<input type="hidden" class="option_price" value="${result[i]["optionPrice"] }">
    	            			<input type="hidden" class="option_id" value="${result[i]["id"] }">
                 	 		</span>
                			<span>${result[i]["optionPrice"] } 원</span>
	                	</div>
	              	</li>`;
				}
				
				$("#option ul").html(html);
				
			}, // success
			fail: function(){
				alert("에러가 발생했습니다");
				food.hide();
			}
		}); // ajax
		
		
		
		
		
		const addCartModal = $(".food_modal");
		const foodImg = $(this).find("#food_img").val();
		const foodName = $(this).find("#food_name").val();
		let foodPrice = Number($(this).find("#food_price").val());
		const foodDec = $(this).find("#food_dec").val();
		const amount = $("#amount").val();
		const totalPrice = amount * foodPrice;

		$(".menu_img").attr("src", foodImg);
		$(".menu_name").text(foodName);
		$(".menu_dec").text(foodDec);
		$(".price").text(Number(foodPrice).toLocaleString() + '원');
		$(".total_price").text(Number(totalPrice).toLocaleString() + '원');
		
		$(".add_cart_food_name").val(foodName);
		$(".add_cart_food_price").val(foodPrice);
		$(".add_cart_food_id").val(foodId);

		openModal(addCartModal, size);
		




		// 수량 증가 감소
		$(".minus").off().click(function() {
			if (1 < Number($("#amount").val())) {
				$("#amount").val(Number($("#amount").val()) - 1);
			}
			const amount = Number($("#amount").val());
			const totalPrice = amount * foodPrice;
			$(".total_price").text(Number(totalPrice).toLocaleString() + '원');
		})
		
		$(".plus").off().click(function() {
			$("#amount").val(Number($("#amount").val()) + 1);
			const amount = $("#amount").val();
			const totalPrice = amount * foodPrice;
			$(".total_price").text(Number(totalPrice).toLocaleString() + '원');
		})
		
		
		// 옵션 체크박스 변경
		$(document).off().on("click" , ".option_box i", function(){
			const optionPrice = Number($(this).siblings(".option_price").val());
			
			if($(this).siblings(".menu_option").is(":checked")) {
				$(this).siblings(".menu_option").prop("checked" ,false);
				$(this).css("color" , "#ddd");
				
				foodPrice -= optionPrice; 
				
			} else {
				$(this).siblings(".menu_option").prop("checked" , true);
				$(this).css("color" , "#30DAD9");
				
				foodPrice += optionPrice; 
			}
			
			const amount = Number($("#amount").val());
			const totalPrice = amount * foodPrice;
			$(".total_price").text(Number(totalPrice).toLocaleString() + '원');
		})
		
		
		
	}) // 메뉴 클릭
	
	
	
	
	
	
	// 장바구니 담기
	$(".add_cart").click(function(){
		const storeId = $("#store_id").val();
		if(cartStoreId != null && storeId != cartStoreId ) {
			swal({
				buttons: ["취소", "담기"],
				title: "장바구니에는 같은 가게의 메뉴만 담을 수 있습니다",
				text: "선택하신 메뉴를 장바구니에 담을 경우 이전에 담은 메뉴가 삭제됩니다"
			})
			.then((value) => {
				if (value == true) {
					deleteCartAll();
					addCart($(this));
					
					$(".add_cart_alarm").show();
					setTimeout(function(){
						$(".add_cart_alarm").hide();
					},1000);
					
					closeModal();
				}
			});			
		} else {
			addCart($(this));
			
			$(".add_cart_alarm").show();
			setTimeout(function(){
				$(".add_cart_alarm").hide();
			},1000);
			
			closeModal();
		
		}
	}) // 장바구니 담기
	
	
	
	function addCart(addCart){
		
		// 선택한 추가옵션 배열에 저장
		const storeId = $("#store_id").val();
		const foodOptionName = new Array();
		const foodOptionPrice = new Array();
		const foodOptionId = new Array();
		
		const amount = addCart.parent().siblings(".modal_box").find("#amount").val() ;
		const foodName = addCart.siblings(".add_cart_food_name").val();
		const foodPrice = addCart.siblings(".add_cart_food_price").val();
	 	const foodId = addCart.siblings(".add_cart_food_id").val();
	 	
	 	const deleveryTip = $("#delevery_tip").val();
	 	const storeName = $("#store_name").val();
			
			
		// 선택된 추가옵션 가져오기 
		$("input[name='option']:checked").each(function() {
			const optionName = $(this).val();
			const optionId = $(this).siblings(".option_id").val();
			const optionPrice = $(this).siblings(".option_price").val();  
			
			foodOptionName.push(optionName);
			foodOptionId.push(optionId);
			foodOptionPrice.push(optionPrice);
		})
		
		const data = {
			storeId : storeId,
			deleveryTip: deleveryTip,
			storeName : storeName,
			foodId : foodId,
			foodName : foodName,
			foodPrice : foodPrice,
			amount : amount,
			foodOptionName : foodOptionName,
			foodOptionId : foodOptionId,
			foodOptionPrice : foodOptionPrice
		}
		
		$.ajax({
			url: "/addCart",
			type: "post",
			data : data,
			traditional : true,
			success: function(result) {
				
				cartList(result);
				
				// 밖에 있으니 작동이 안되서 추가
				// 장바구니 1개 삭제
				$(document).off().on("click", ".cancle_btn", function() {
					const index = $(this).parent().index();
					$.ajax({
						url: "/cartOne",
						type: "DELETE",
						data: {index : index},
						success: function(result) {
							if(result == "") {
								cartReset();
								return;
							}
							cartList(result);
							$(".m_cart_count").css("display" , "block");
							$(".m_cart_count").text(result["cartId"].length);
						},
						fail: function() {
							swal("에러가 발생했습니다");
						}
					})
				}); // 장바구니 1개 삭제
			}, // success
			fail: function(){
				swal("에러가 발생했습니다");
			}
		}); // ajax
	} // addCart
	
	
	
	
	
	
	
	// 장바구니 1개 삭제
	$(document).on("click", ".cancle_btn", function() {
		const index = $(this).parent().index();
		$.ajax({
			url: "/cartOne",
			type: "DELETE",
			data: {index : index},
			success: function(result) {
				if(result == "") {
					cartReset();
					return;
				}
				cartList(result);
			},
			fail: function() {
				swal("에러가 발생했습니다");
			}
		})
	}); // 장바구니 1개 삭제
	
	

	
		
	
	// 가게 입장시 카트리스트 불러오기
	$.ajax({
		url: "/cartList",
		type: "get",
		success: function(result){
			if(result == "" ) {
				cartReset();
				return;
			}
			cartList(result);
		},
		fail: function() {
			swal("에러가 발생했습니다");
		}
	})





	// 장바구니 전부 삭제
	$("#cart i").click(function(){
		deleteCartAll();
	})	

	function deleteCartAll(){
		$.ajax({
			url: "/cartAll",
			type: "DELETE",
			success: function(){
				cartReset();
			},
			fail: function(){
				swal("에러가 발생했습니다");
			}
		})
	}







	function cartList(result){
		console.log(result);
		
		let html = "";
		for(var i=0;i<result["cartId"].length;i++) {
			let optionHtml = "";
			
			if(result["cartList"][i]["foodOptionName"] != null ) {
				
				for(var j=0;j<result["cartList"][i]["foodOptionName"].length;j++) {
					const optionName = result["cartList"][i]["foodOptionName"][j];
					const optionPrice = Number(result["cartList"][i]["foodOptionPrice"][j]).toLocaleString();
					
					optionHtml += `<div class="cart_menu_option"> ${optionName }  ${optionPrice }원</div>`;
				}
			}
			
			html += `<li> 
						<h3>${result["cartList"][i]["foodName"]  }</h3>
						<div>${result["cartList"][i]["foodPrice"].toLocaleString()}원</div>
						<div>수량 : ${result["amountList"][i]}</div>
						<div>${optionHtml} </div>
						<div>합계 : ${result["totalPriceList"][i].toLocaleString() }원</div>
						<button class="cancle_btn"> ${"ｘ"} </button>
					</li>`; 
					 // 장바구니 추가하면 장바구니 리스트 변경
			
			
		}
		menuTotalPrice = result["menuTotalPrice"];
		
		$(".cart ul").html(html);
		$(".total").html("총 합계 : " + result["menuTotalPrice"].toLocaleString() + "원");

		minDeleveryCheck(menuTotalPrice);
		
		$(".m_cart_count").css("display" , "block");
		$(".m_cart_count").text(result["cartId"].length);
		
		console.log("cartStoreId2= " + result["storeId"]);
		
		cartStoreId = result["storeId"];
		
	}	



	// 주문금액이 최소주문금액 이상이어야 주문가능
	function minDeleveryCheck(menuTotalPrice) {
		
		const minDelevery = Number($("#min_delevery").data("min_delevery"));
		
		console.log("min_delevery = "  +minDelevery );
		
		if(minDelevery <= menuTotalPrice) {
			$(".order_btn").attr("disabled", false); 
			$(".order_btn").css("background", "#30DAD9");
			$(".order_btn").text("주문하기");
		} else {
			$(".order_btn").css("background", "#ddd");
			$(".order_btn").attr("disabled", true); 
			$(".order_btn").text(minDelevery + "원 이상 주문할 수 있습니다");
			
		}
		
		
		// 최소주문금액 알림 추가
	}


	// css로 display none시 카카오 맵 깨짐
	$("main ul.info").hide();
	// 탭 눌렀을때 색변경 콘텐츠 변경
	$("ul.tab > li").click(function() {

		const index = $(this).index() + 1;

		$("ul.tab > li").removeClass("select");
		$(this).addClass("select");

		$("main  ul").eq(1).hide();
		$("main  ul").eq(2).hide();
		$("main  ul").eq(3).hide();
		$("main  ul").eq(index).show();

		const offset = $(".offset").offset();
		const scrollPosition = $(document).scrollTop();

		if (offset["top"] < scrollPosition) {
			$("html").animate({ scrollTop: offset.top }, 100);
		}

	});





	function cartReset() {
		$(".cart ul").html("");
		$(".total").html("장바구니가 비었습니다.");
		$(".order_btn").css("background", "#ddd");
		$(".order_btn").attr("disabled", true); 
		$(".order_btn").text("주문하기");
		menuTotalPrice = 0;
		
		$(".m_cart_count").css("display" , "none");
		$(".m_cart_count").text("");
	};
	
	
	
	
	// 주문하기
	$(".order_btn").click(function() {
		location.href = "/order";
	});
	// 모바일 주문하기
	$(".m_cart_img_box").click(function() {
		location.href = "/order";
	});
	
	



});