	
	$(".amount_box button").click(function(){
		const amount = $(this).siblings("#amount");
		const cartId = $(this).parent().parent().parent("li").index() / 2;
		let foodPrice = $(this).parent().siblings(".sum");
		let clickBtn = "";
		
		if($(this).hasClass("plus")){
			clickBtn = "plus";
		} else {
			if(amount.val() <= 1) {
				return;
			}
			clickBtn = "minus";
		}
		
		$.ajax({
    		url : "/cartAmount",
    	    type : "PATCH",
    	    data : {cartNum : cartId, clickBtn : clickBtn },
    	    success : function(result){
				foodPrice.text(result["totalPriceList"][cartId].toLocaleString() + "원");
    	    	amount.val(result["amountList"][cartId]);
    	    	
    	    	const menuTotalPrice = result["menuTotalPrice"];
    	    	const deleveryTip = result["deleveryTip"];
    	    	
    	    	$(".order_price").text("주문금액 : " + menuTotalPrice.toLocaleString() + "원");
    	    	$(".total").text((menuTotalPrice + deleveryTip).toLocaleString() +  "원 결제하기");
    	    	
    		},
    		fail : function(){
    			alert("다시 시도해주세요");
    		}
    	}); // ajax
		
	})



	
	if($("#user_id").val() != ""){
	
		$(".point_click").click(function(){
			$(".point_input_box").fadeToggle(200);
		});
		
		$(".use_point").click(function(){
			
			const point = Number($(".point_input").val());
			/*const deleveryTip = Number($("#delevery_tip").val());*/
			const total = Number($("#total").val());
			
			
			if(point != 0) {
				$(".total").html("");
				
				
				const html = (total - point).toLocaleString() +"원 결제하기";
				         
				         
				$(".total").html(html);
				
				$(".point_dis").show();
				$(".point_dis").html("포인트 할인 -" + point.toLocaleString() + "원");
			}
		});
		
		
		
		
	 	$(".point_input").focusout(function(){
	 		const total = Number($("#total").val());
			const userPoint = Number($("#point").val());
			
			if($(this).val() > userPoint)
				$(this).val(userPoint);
			if($(this).val() > total)
				$(this).val(total);
			if($(this).val() < 0)
				$(this).val(null);
		});
		
	} 
	else {
		 swal("", {
			  buttons: ["비회원으로 주문하기", "로그인"],
			})
			.then((value) => {
				 if(value == true) {
					 location.href = "/login";
				 }
			});
		
		
		 $(".point_area .point").css("border" , "1px solid #ddd"); 
		 $(".point_area .point").css("cursor" , "default"); 
		 $(".point_area span").css("color" , "#ddd"); 
		 $(".point_area span").css("cursor" , "default"); 

	}			

	/*function check(){
		if($(".point_input").val() == null || $(".point_input").val() == "" ) {
			$(".point_input").val(0);
			return true;
		} 
	};*/
	
	
	function orderCheck(){
		
		
		
		return true;
	}
	
	

		
	
	

	
