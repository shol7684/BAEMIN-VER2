

$(document).ready(function() {


	let addOptionNum = 1;
	function optionHtml(){
		let html = 
			`<div class="option">
				<div>
					<div>옵션 ${addOptionNum} </div> 
					<div>가격</div> 
				</div>
				
				<div>
					<input type="hidden" name="optionId" >
					<input type="text" maxlength="30" name="foodOption" required >
					<input type="number" onkeypress="return lenthCheck(this,8);" pattern="\\d*" name="foodOptionPrice" required >
				</div>
				
				<div>
					<button type="button" class="add_option_cancle" ><i class="fas fa-times"></i></button>
				</div>
			</div> `;
			
		addOptionNum++;	
		return html;
	}





	// 답장하기 버튼
	$(".review_btn").click(function() {

		$(this).parents().siblings(".boss.input").stop().fadeToggle(0,function(){
		const top = $(this).offset().top;
			if($(this).css("display") == 'block') {
				$("html").animate({ scrollTop: top - 100 }, 200);
			}
		});
	})






	// 답장 등록하기
	$(".boss_comment_btn").off().click(function(){
		const bossComment = $(this).parent().siblings().find(".comment_area").val();
		const orderNum = $(this).siblings(".order_num").val();
		const inputTarget = $(this).parents().siblings(".boss");
		const closeTarget = $(this).parents(".boss.input"); 
		
		const data = {
			bossComment : bossComment,
			orderNum : orderNum
		}
		
		$.ajax({
			url: "/admin/bossComment",
			type: "POST",
			data: data
		})
		.done(function(result){
			let html = `<div class="boss_comment_box">
							<div class="nickname">사장님</div>
	                		<div class="boss_comment">${result }</div>
	                	</div>`;
			
			closeTarget.fadeToggle(0);
			inputTarget.html(html);
			closeTarget.find(".comment_area").val("");
		})
		.fail(function(){
			alert("실패");
		})
	})




	
	// 메뉴 추가 모달
	$(".add_menu").click(function(){
		openModal($(".add_menu_modal.menu_add"));
	})
	
	
	
	
	// 메뉴 추가 모달  옵션 추가하기
	$(".add_option").click(function(){
		const html = optionHtml();
		$(this).parents(".modal").find(".option_box").append(html);		
	})
	
	
	
	
	// 메뉴 추가 모달  옵션 삭제하기
	$(document).on("click", ".menu_add .add_option_cancle", function(){
		$(this).parents(".option").remove();
	})
	
	
	
	
	// 메뉴 수정기 모달  옵션삭제
	$(document).on("click", ".menu_modify .add_option_cancle", function(){
		const optionId = $(this).parents(".option").find("input[name='optionId']").val();
		const deleteTarget = $(this).parents(".option"); 
		
		$.ajax({
			url: "/admin/option",
			type: "DELETE",
			data: {optionId : optionId}
		})
		.done(function(result){
			deleteTarget.remove();
		})
		.fail(function(){
			alert("실패");
		})
	})






	// 매장 정보 수정
	$(".inf_modify").click(function(){
		openModal($(".store_reg_modal"));
		
		const category =$("#store_category").val();
		const openingTime =$("#store_opening_time").val();
		const closingTime =$("#store_closing_time").val();
		
		$("#category").val(category).prop("selected", true);
		$("#opening_time").val(openingTime).prop("selected", true);
		$("#closing_time").val(closingTime).prop("selected", true);
	})
	
	
	
	
	
	// 메뉴 삭제 체크박스 
	$(".menu_delete_checkbox").change(function(){
		if($(this).is(":checked")) {
    		$(this).siblings("i").css("color" , "#2AC1BC");
		} else {
    		$(this).siblings("i").css("color" , "unset");
		}
	})
    	
    	
    	

	// 메뉴 삭제
	$(".delete_menu").click(function(){
		const deleteNumber = []; // 삭제할 메뉴 번호
		const deleteIndex = []; // 삭제후 remove()할 인덱스
		
		$("input[name='deleteNumber']:checked").each(function(){
			deleteNumber.push($(this).val());
			deleteIndex.push($(this).parents("li").index());	
		})
		
		console.log("삭제 메뉴 = " + deleteNumber);
		console.log("삭제 인덱스 = " +  deleteIndex);
		
		if(deleteNumber == "" ) {
			swal("삭제 할 메뉴를 선택해주세요");
		} else {
			swal("삭제 할까요?", {
			  buttons: ["취소", "삭제"],
			})
			.then((value) => {
				
				if(value == true ) {
					const storeId = $("#store_id").val();
					
					$.ajax({
		 	    		url : "/admin/menu",
		 	    	    type : "DELETE",
		 	    	   	traditional : true,  
		 	    	    data: {deleteNumber : deleteNumber , storeId : storeId }
			    	})
			    	.done(function(){
						for(var i=deleteIndex.length-1;i>=0;i--) {
							$(".menu li").eq(deleteIndex[i]).remove();		
						}	
					})
					.fail(function(){
						swal("다시 시도해 주세요");
					})
				}
			});
		}
		
	}) // delete_menu





	// 메뉴 수정하기
	$(".menu > li .menu_box").click(function(){
		const modal = $(".add_menu_modal.menu_modify");
		const foodId = $(this).find(".food_id").val();
		$.ajax({
			url: "/foodOption",
			type: "get",
			data: {foodId : foodId}
		})
		.done(function(result){
			console.log(result);
			addOptionNum = 1;
			let html = "";
			for(var i=0;i<result.length;i++) {
				html += optionHtml();
			}
			modal.find(".option_box_div").html(html);
			
			for(var i=0;i<result.length;i++) {
				modal.find(".option").eq(i).find("input[name=foodOption]").val(result[i]["optionName"]);
				modal.find(".option").eq(i).find("input[name=foodOptionPrice]").val(result[i]["optionPrice"]);
				modal.find(".option").eq(i).find("input[name=optionId]").val(result[i]["id"]);
			}
				
		})
		.fail(function(){
			swal("에러가 발생했습니다");
			food.hide();
		}) // ajax
		
		const foodName = $(this).find(".food_name").val();
		let foodPrice = Number($(this).find(".food_price").val());
		const foodDec = $(this).find(".food_dec").val();
		
		modal.find("input[name=id]").val(foodId);
		modal.find("input[name=foodName]").val(foodName);
		modal.find("input[name=foodPrice]").val(foodPrice);
		modal.find("input[name=foodDec]").val(foodDec);
		
		openModal(modal);
	})







	$("#img").change(function(e){
		imgPreview(e, $(this));
	})
	
	$("#img2").change(function(e){
		imgPreview(e, $(this));
	})
	

	$(".img_close").click(function(){
		imgClose();
	})

})
