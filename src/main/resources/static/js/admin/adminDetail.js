

$(document).ready(function() {

	let size = $(window).width();

	$(window).resize(function() {
		size = $(window).width();
	})
	

	
	
	
	/*답장하기 버튼*/
	$(".review_btn").click(function() {
		cs($(this).siblings(".order_num").val());

		$(this).parent().parent().parent().siblings(".boss.input").stop().fadeToggle(0,function(){
		const top = $(this).offset().top;
			if($(this).css("display") == 'block') {
				$("html").animate({ scrollTop: top - 100 }, 200);
			}
		});
	})


	/*답장 등록하기*/
	$(".boss_comment_btn").off().click(function(){
		const bossComment = $(this).parent().siblings().find(".comment_area").val();
		const orderNum = $(this).siblings(".order_num").val();
		const inputTarget = $(this).parent().parent().parent().siblings(".boss");
		
		const closeTarget = $(this).parent().parent().parent(".boss.input"); 
		
		
		const data = {
			bossComment : bossComment,
			orderNum : orderNum
		}
		
		$.ajax({
			url: "/admin/bossComment",
			type: "POST",
			data: data,
			success: function(result){
				
				let html = `<div class="boss_comment_box">
								<div class="nickname">사장님</div>
		                		<div class="boss_comment">${result }</div>
		                	</div>`;
				
				closeTarget.fadeToggle(0);
				inputTarget.html(html);
				closeTarget.find(".comment_area").val("");
				
				cs(clickBtn);
			},
			fail: function(){
				alert("실패");
			}
		})
	})


	
	// 메뉴 추가 모달
	$(".add_menu").click(function(){
		const modal = $(".add_menu_modal");
		openModal(modal, size);
	})
	
	
	let addOptionNum = 2;
	$(".add_option").click(function(){
		let html = `<div class="option">
						<div>
							<span>옵션 ${addOptionNum} </span> <input type="text" maxlength="30" name="foodOption">
						</div>
						<div>
							<span>가격</span> <input type="number" onkeypress="return lenthCheck(this,8);" pattern="\d*" name="foodOptionPrice">
						</div>
					</div>`;
					
		$(".option_box").append(html);		
		addOptionNum++;	
					
	})
	
	
	







	// 체크박스 체크 확인용 임시
	$(".menu_delete_checkbox").change(function(){
		if($(this).is(":checked")){
			console.log($(this).siblings().find("#menu_num").val());
		}
			
	})




	
	$(".delete_menu").click(function(){
		const deleteNumber = [];
		
		$("input[name='deleteNumber']:checked").each(function(){
			deleteNumber.push($(this).val());	
		})
		
		console.log(deleteNumber);
		
		if(deleteNumber == "" ) {
			swal("삭제 할 메뉴를 선택해주세요");
		} else {
			swal("삭제 할까요?", {
			  buttons: ["취소", "삭제"],
			}).then((value) => {
				
				if(value == true ) {
					const storeId = $("#store_id").val();
					
					$.ajax({
		 	    		url : "/admin/menu",
		 	    	    type : "DELETE",
		 	    	   	traditional : true,  
		 	    	    data: {deleteNumber : deleteNumber , storeId : storeId },
		 	    	    success : function(){
		 	    	    	location.href = storeId; 
		 	    	    	
		 	    		}, // success
		 	    		fail : function(){
							swal("다시 시도해 주세요");
						}
			    	}); // ajax
				}
			});
		}
		
	}) // delete_menu







	function cs(value) {
		console.log(value);
	}
})
