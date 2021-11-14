
$(document).ready(function(){

        
       alert("js 삭제");


	let size = $(window).width();
	
	$(window).resize(function(){
		size = $(window).width();
		console.log(size);
	})
	
	
	/* ----------------------------- 이미지 미리보기----------------------------- */		
		imgPreview(document.querySelector("#modify_img")); // 정보수정 모달	
    	
		imgPreview(document.querySelector(".img")); // 메뉴 추가
    	
		imgPreview(document.querySelector(".modify_img")); // 메뉴 수정
	/* ----------------------------- 이미지 미리보기----------------------------- */		
		
    
    /* ----------------------------- 사장님 댓글 달기 ----------------------------- */	           
                           	
	
	$(".reply_btn").click(function(){ // 댓글 달기 댓글 수정하기 버튼
		$(this).parent().parent().parent().siblings(".boss.input").stop().fadeToggle(200);
	}); 
	
	
	
	$(".boss_reply").click(function(){ // 댓글입력 후 댓글달기 버튼
		
		$(this).parent().parent().fadeToggle(0);
			
		
		const orderNum = $(this).siblings($(".order_num")).val();
		const content = $(this).parent().parent().find(".comment_area").val();
		
		const prevComment = $(this).parent().parent().parent().find(".boss");
		const commentArea = $(this).parent().parent().parent().find(".temp");
		const reviewBtn = $(this).parent().parent().parent().find(".reply_btn");
		
		console.log("orderNum =" + orderNum);
		if(content == null || content == "") {
			return false;
		}
		
  		let data = { orderNum : orderNum, content : content };
	  				
		$.ajax({
	  		url : "/admin/bossComment",
	  	    type : "post",
	  	    data: data,
	  	    success : function(result){
		
			 	prevComment.css("display" , "none"); // 기존 댓글 숨기기
		   	 
	  			let ht = `<div class="boss">
	      					<div class="nickname">사장님</div>
	       	  				<div class="boss_content">${result }</div>
	       		   	      </div>`; 
		       		   
	           			
	  			commentArea.html(ht); 
	  			
	        	$(".boss.input textarea").val("");
	        	
	        	reviewBtn.html("댓글 수정하기");
			
	  		}, // success
	  		fail : function(){
  				alert("실패");
  			}	
	   	 }); // ajax
	   	 
   	
	});        
            
	/* ----------------------------- 사장님 댓글 달기 ----------------------------- */  
	

 	
	
	/* ----------------------------- 메뉴 수정하기 ----------------------------- */
	
	const menuList = $(".menu > li .in"); //메뉴 리스트
	
	menuList.click(function(){
		const storeNum = $(".store_num").val();
		const storeName = $(".store_name").val();
		
		const menuNum = $(this).find("#menu_num").val();
		const menuName = $(this).find("#menu_name").val();
		const menuPrice = $(this).find("#menu_price").val();
		const menuDec = $(this).find("#menu_dec").val();
		const menuImg = $(this).find("#menu_img").val();
		const menuThumb = $(this).find("#menu_thumb").val();
		
		/* let optionNumber = 0; */
	
		

		
		
		$(".add_cart").text("메뉴 수정하기");
		$(".add_cart").addClass("modify_menu")
		$(".add_cart").unbind();
		$(".add_cart").removeClass("add_cart");
		
		const modifyMenu = $(".modify_menu");
		const modifyMenuModal = $(".add_menu_modal.modify");
		
		
		
		
		modifyMenu.off("click").on("click" ,function(){
			let	optionNumber = $(".menu_option").length ;
			console.log( "optionNumber =" + optionNumber);			
			
			
			console.log("메뉴수정 모달" );
			
			openModal(modifyMenuModal,size);
			 
			
			modifyMenuModal.find("input[name=menuNum]").val(menuNum);
			modifyMenuModal.find("input[name=menuThumb]").val(menuThumb);
			modifyMenuModal.find("input[name=menuImg]").val(menuImg);
			modifyMenuModal.find("#menu_name").val(menuName);
			modifyMenuModal.find("#menu_price").val(menuPrice);
			modifyMenuModal.find("#menu_dec").val(menuDec); 
			
			
			
			
			
			let ht = "";

	 		ht =  `<div class="option"> 
	    		       <div>
	    				 <span>옵션  ${ 1 } </span>
	    				 <input type="text" name="optionName" >
					</div>
				 	<div>
	    				<span>가격</span>
	    				<input type="text" name="optionPrice" >
					</div>
				 </div>`; 
			
			modifyMenuModal.find("#option_box").html(ht); 
			ht = "";
			
			if(optionNumber > 0) {
				console.log("옵션 추가=" );
				for(var i=0;i<optionNumber ; i++) {
					var menuOption = $(".menu_option").eq(i).val();
					var optionPrice = $(".option_price").eq(i).val();
					var optionNum = $(".option_num").eq(i).val();
					
	
					console.log("menuOption=" + menuOption);
					console.log("optionPrice=" + optionPrice);
					
						
					ht +=  `<div class="option"> 
			    			 <div>
			    				 <span>옵션 ${(i+1) } </span>
			    				 <input type="text" name="optionName" value="${menuOption }" >
			    				 <input type="hidden" name="optionNum" value="${optionNum }" >
							</div>
						 	<div>
			    				<span>가격</span>
			    				<input type="text" name="optionPrice" value="${optionPrice }" >
							</div>
						 </div>`;
						
					modifyMenuModal.find("#option_box").html(ht);
				} // for
			} // if
			
			/* 옵션 추가 */	
		 	$(".add_option").off("click").on("click" ,function(){
		 		optionNumber +=1;
				
		 		if(optionNumber == 1) {
		 			optionNumber = 2;
		 		}
		 		let ht = "";
		 	
	 			ht =  `<div class="option"> 
		    			 <div>
		    				 <span>옵션  ${optionNumber } </span>
		    				 <input type="text" name="optionName">
						</div>
					 	<div>
		    				<span>가격</span>
		    				<input type="text" name="optionPrice">
						</div>
					 </div>`;
					 
				$(".modify #option_box").append(ht);
		 	});
		 	/* 옵션 추가 */
			
		}) // modifyMenu.click(function(){	
	
	
		}) // muenuList

	
	
	
	
	/* ----------------------------- 정보 수정 모달창 ----------------------------- */
	
    
	const modifyBtn = $(".inf_modify");
      
     modifyBtn.click(function(){
        const modifyModal =  $(".store_reg_modal");
		const categoryIndex = $(".category").val() % 100; 
	    		
		const businessHour1 = $(".business_hour1").val();    	   
		const businessHour2 = $(".business_hour2").val();
		
    	$("#cate option").eq(categoryIndex).attr("selected" , true);
    	$("#business_hour1 option").eq(businessHour1).attr("selected" ,true);
    	$("#business_hour2 option").eq(businessHour2).attr("selected" ,true);
    	   
    	openModal(modifyModal,size);
    	    
     });
/* ----------------------------- 정보 수정 모달창 ----------------------------- */
    
    
  
/* ----------------------------- 메뉴 추가 모달창 ----------------------------- */
	const addMenu = $(".add_menu");

    addMenu.click(function(){
    	const addMenuModal = $(".add_menu_modal.register ");
        
        openModal(addMenuModal,size);
        

        /* 옵션 추가 */	
     	let optionNumber =1 ;
     		
     	$(".add_option").click(function(){
     		optionNumber +=1;
    		let ht = "";
    		
    		ht =  `<div class="option plusOption"> 
	       				<div>
	        				<span>옵션  ${optionNumber } </span>
	        				<input type="text" name="optionName">
	    				</div>
    			 		<div>
	        				<span>가격</span>
	        				<input type="text" name="optionPrice">
	    				</div>
	    		  </div>`;
    			
    		$(".register #option_box").append(ht);
     	});
     	/* 옵션 추가 */
    
    });	
/* ----------------------------- 메뉴 추가 모달창 ----------------------------- */
    
    

/* ----------------------------- 메뉴 삭제하기 ----------------------------- */
      
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
				
				console.log(value);
				if(value == true ) {
					$.ajax({
		 	    		url : "/deleteMenu",
		 	    	    type : "post",
		 	    	   	traditional : true,  
		 	    	    data: {deleteNumber : deleteNumber , storeNum : $("#store_num").val() },
		 	    	    success : function(){
		 	    	    	location.href =  $("#store_num").val(); 
		 	    	    	
		 	    		}, // success
		 	    		fail : function(){
							swal("다시 시도해 주세요");
						}
		 	    		
			    	}); // ajax
				}
			});
		}
		
	}) // delete_menu
      
/* ----------------------------- 메뉴 삭제하기 ----------------------------- */
    		
		
		
		
            
            
            
            
            
 });