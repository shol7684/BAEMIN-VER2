$(document).ready(function(){
   	 
	$(".home").click(function(){
		location.href = "/";
    });  //홈으로 이동
        
        
	let size = $(window).width();
	
	$(window).resize(function(){
		size = $(window).width();
		console.log(size);
	})
        
        
        
        
        
        
	const modal = $(".modal");
	
                	 

    
	// ------------------주문 접수하기 모달---------------------
		
		
		// 주문 수락할 주문번호
        let acceptOrderNum = 0;
        
     	// 주문 접수 1번모달
		$(document).on("click", ".accept", function(){ 
			const acceptModal = $(".accept_modal");  
			
			acceptOrderNum = $(this).parent().siblings().find(".order_num").val();
			
			openModal(acceptModal,size);
                         
			let data = {
				orderNum : acceptOrderNum
			};
	                         
		$.ajax({
       		url : "/admin/orderDetail",
       	    type : "post",
       	    data : data,
       	    success : function(result){
       	    	
				const index = result["orderDetailMenu"].length;
				const optionIndex = result["orderDetailOption"].length;
				const userAddress1 = result["orderDetailMenu"][0]["userAddress2"];
				const userPhone = result["orderDetailMenu"][0]["userPhone"];
				let userAddress2 = result["orderDetailMenu"][0]["userAddress3"];
				let request = result["orderDetailMenu"][0]["request"];
				
				if(userAddress2 == null) {
					userAddress2 = "";
				}
				
				if(request == null) {
					request = "";
				}
				
				let menuHtml = "";
				
				for(var i =0;i<index;i++) {
    				const menuName =  result["orderDetailMenu"][i]["menuName"];
    				const menuPrice =  result["orderDetailMenu"][i]["menuPrice"];
    				const amount =  result["orderDetailMenu"][i]["amount"];
    				let option = ""; 
    					
    				
					for(var j=0;j<optionIndex;j++) {
						const optionCartNum = result["orderDetailOption"][j]["cartNum"]; 
						const menuCartNum = result["orderDetailMenu"][i]["cartNum"];
						const menuOption = result["orderDetailOption"][j]["menuOption"]; 
						const menuOptionPrice = result["orderDetailOption"][j]["menuOptionPrice"]; 
						
						if(optionCartNum == menuCartNum )	
							option += `<li id="option"><span>ㆍ` + menuOption + " " + menuOptionPrice.toLocaleString() +  `원</span></li>`
							
					}	
    				
					menuHtml +=  `<li><span>` + menuName + " " + menuPrice.toLocaleString() + `원</span><span> (` + amount + `개)</span><br></li>`
                           		 + option;
    			}  
				
				
				  let ht = `<div id="address">
								<span>` + userAddress1 + `</span><br>
								<span>` + userAddress2 + `</span>
			    			</div>
			        		<div>
			           			<span>연락처 ` + userPhone + `</span>
			        		</div>`;
	        		
 				 let ht2 = `<h2>요청사항</h2>
 				 			<div>` + request  + `</div>`;
 				 			
        		$(".accept_modal_box .info_area").html(ht);
 				$(".accept_modal_box .request").html(ht2);
 				$(".accept_modal_box ul").html(menuHtml);
    	
	        		} // success
	        	}); // ajax
 				        	
   
    	}); // accept
    	
    	// 주문 접수 1번모달 주문거부하기
    	$(".order_cancle button").click(function(){
    		const orderCancleModal = $(".order_cancle_modal");
    		
    		openModal(orderCancleModal, size);
    		
   		});

    	// 주문거부 사유 선택
    	$(".order_cancle_modal li").click(function(){
	   		let orderNum = acceptOrderNum; // 주문접수 클릭시 주문번호 가져오기
    		
	   		console.log(orderNum);
   			$(".order_cancle_modal li").removeAttr("id" , "select");
   			$(this).attr("id" , "select");
   			
			let cancleReason = $(".order_cancle_modal").find("#select").data().value;
   			
			
			// 거부하기 클릭
   			$(".order_cancle_btn").off().click(function(){
   				let data = {cancleReason : cancleReason, orderNum : orderNum};
   				
   				
   				orderList("/admin/orderCancle",data,0); 
   				
	   			
   			}) // order_cancle_btn
   			
   		})
    	
   	
   		
   		
    	
    	let time = 0; // 예상 배달시간
    	
    	// 주문 접수 2번모달
		$(".accept_next").click(function(){
	    	const timer = $(".delevery_timer");
			
			openModal(timer, size);
			
			time = $("#select").data()["value"];
		});
    	
    	// 접수 2번모달 시간선택
		$(".delevery_timer li").click(function(){
			$(this).siblings().removeAttr("id" ,"select");
               $(this).attr("id" ,"select");
               time = $(this).data()["value"];
       	});
		
    	// 접수 2번모달에서 주문 수락
    	 $("#accept").click(function(){
     		let data = {
				orderNum : acceptOrderNum,
				time : time,
				index : 0
 		    };
 	    	
     		orderList("/admin/accept",data,0); 
          });
				   
    	
       // 배달 완료하기      		
    	$(document).on("click", ".complete", function(){ 
    		
    		swal("배달 완료후 눌러주세요", {
				  buttons: ["취소", "배달 완료"],
				}).then((value) => {
					
					 if(value == true) {
						 let orderNum = $(this).parent().parent().find(".order_num").val();
				    		
				    		let data = {
									orderNum : orderNum
				        	};
				    		
				    		orderList("/admin/complete",data,index); 
					 } 
				});
			
    	}); // 완료 버튼 클릭
   	// ------------------주문 접수하기 모달---------------------
    	
				    	
   	
   	/* ---------------------- 왼쪽 탭 클릭 ----------------------- */
   	const contBox = $("#cont_box > ul > li ");
   	const store = $(".store");
   	const manage = $(".manage");
   	const aside = $("aside");
	const asideTab = $(".aside_tab > li ");
	let index =0; // aside tab 인덱스
        
    asideTab.click(function(){
		index = $(this).index();
		
		asideTab.removeClass("select2");
		
		$(this).addClass("select2");
		
		contBox.hide();
		contBox.eq(index).show();
		
		clearTimeout(list);
        
		orderList("/admin/orderList",{index : index },index);// 접수대기 주문 목록

       	if(index == 0 ) {
       		refreshOrderList(0);
       	} 
    });
     
       /* ---------------------- 왼쪽 탭 클릭 ----------------------- */
			            
       /* ---------------------- 상단 탭 클릭 ----------------------- */
          
		const tab = $("ul.box > li");  // 상단 탭 쿠키로 현재 탭 기억

          $.ajax({

			url:"/tabCookie",
			type:"post",
			success : function(result){
				console.log("저장쿠키 = " +  result);
				
				if(result == 1) {		// 매장 관리 탭
					store.show();
					manage.show();
					$("#cont_box").hide();
					clearInterval(list);
					
					console.log("매장관리 탭")
					
				} else if(result == 0){				// 주문 접수 탭 
					contBox.show();
					aside.show();
					
					refreshOrderList(0);
					
				}
				
				tab.eq(result).attr("id","select");
			}
		}) // ajax

        
         tab.click(function(){
        	 console.log("list = " + list);
        	 
             let tabNum = $(this).index();

             if(tabNum != 2 && tabNum != 3) { // tabNum 3 = 홈으로 버튼
            	 
	             tab.removeAttr("id" , "#select");
	             $(this).attr("id","select");
	
	             if(tabNum == 0 ) { //주문접수 눌렀을때
	                 $("aside").show();
	                 $("#cont_box").show();
	                 contBox.eq(index).show();
	                 manage.hide();
	                 if(index == 0 ) {
		                 refreshOrderList(0);
	                 }
	                 
	
	             } else if(tabNum == 1 ) { //매장관리 눌렀을때
	                $("#cont_box").hide();
	                manage.show();
	                contBox.hide();
	             
	                 $("aside").hide();
	                 
	                 clearTimeout(list);
	             }
	             
	             let data = {
	             		tabNum : tabNum
	             }
	         	$.ajax({
					url:"/saveTabCookie",
					type:"post",
					data : data,
					success : function(){
					}
				})
             }

         });
		
		$(".sales_check").click(function(){
			location.href = "/admin/sales"
		})
	/* ----------------------------- 상단 탭 클릭 ---------------------------- */
				    	
	
	/* -------------------------- 매장 등록 이미지 미리보기---------------------- */		
         
    	/*imgPreview(document.querySelector("#img"));*/
    	
	/* -------------------------- 매장 등록 이미지 미리보기---------------------- */ 
         
         
	/* -------------------------------- 함수 -------------------------------- */
           	
   	function orderList(url,data,index) {
   		const targetArr = [".order_list.first ul" , ".order_list.second ul" , ".order_list.third ul"];	 
        const btnNameArr = ["접수하기" , "완료" , ""];
        const btnClassArr = ["accept" , "complete" , "end" ];
        
        let target = targetArr[index];
        let btnName = btnNameArr[index];
        let btnClass = btnClassArr[index];
        
		$.ajax({
    		url : url,
    	    type : "post",
    	    data : data,
    	    success : function(result){
	 			
 			
				let ht = ""; // 메뉴리스트 html
				let next = "";  // 다음번 메뉴리스트의 주문번호 
				let total = 0;
	 			const length = result["orderList"].length;
				
 				for(var i =0;i<length;i++) {
 					
 					const date = new Date(result["orderList"][i]["orderDate"]);
 					const acceptTime = result["orderList"][i]['acceptTime'];
 					const year = moment(acceptTime).format("YYYY"); 
 					const month = moment(acceptTime).format("MM"); 
 					const day = moment(acceptTime).format("DD"); 
 					const hour = moment(acceptTime).format("HH"); 
 					const minute = moment(acceptTime).format("mm"); 
 					
					console.log("주문 접수 시간" + year + "년 "  + month + "월 " + day + "일 " + hour +  "시"  + minute + "분" );
    			
					const deleveryTime = result["orderList"][i]["deleveryTime"];
					const today = new Date();
					const setTime = new Date(year ,month -1, day, hour, minute);
					const time = deleveryTime - (( today.getTime() -setTime.getTime()) / 1000 / 60 ) ;
					
					if(index == 1 ){
					    if(time < 0) {
							btnName = "완료";
						} else {
							console.log(time);
							btnName = time.toFixed(0) + "분"; // 배달 예상시간 실시간 표시
						} 
					}
			
					total = result["orderList"][i]["menuPrice"];
					amount = result["orderList"][i]["amount"];
					
					let orderNum = result["orderList"][i]["orderNum"]; // 메뉴의 주문번호
					let cartNum = result["orderList"][i]["cartNum"]; // 메뉴의 카트번호
					let optionName = []; // 메뉴의 옵션 목록담을 배열
		
					let optionOrderNum = "";
					let optionCartNum = ""; // 같은 주문번호일때 메뉴 구분
					
			
					if(result["orderListOption"] != null) {
						
						for(var j = 0;j<result["orderListOption"].length;j++) {
							optionOrderNum = result["orderListOption"][j]["orderNum"]; // 옵션의 주문번호
							optionCartNum = result["orderListOption"][j]["cartNum"];	// 옵션의 카트번호
						
							if(orderNum == optionOrderNum ) { // 메뉴 주문번호 == 옵션 주문번호일때
								if(optionName[optionCartNum] == null) { 
									optionName[optionCartNum] = "";
								}
								// 옵션[카트번호]에 옵션들을 담는다
								optionName[optionCartNum] += result["orderListOption"][j]["menuOption"] + ",";
								
							}
						} 
					}
					
					
			
					  if(optionName != "") { 
							console.log("optionName = " + optionName);
							console.log("optionName = " + !optionName[0]);
						for(var j =0;j<optionName.length;j++) {
							
							if(!optionName[0]) continue;
							
							if(optionName[j].endsWith(",")) {  // 옵션이름이 , 로 끝나면
								optionName[j] = optionName[j].substring(0, optionName[j].length-1); 
							}
							
						}
					 }   
				/* 	console.log(typeof(optionName) );
					 if(optionName.endsWith(",")) {
						 console.log(3323323);
						 
					 } */
					  
					
					let menu = [];
					let menuCount = 0;
					
					for(var j =0; j<length;j++) {
						cartNum = result["orderList"][j]["cartNum"];
						
						if(orderNum == result["orderList"][j]["orderNum"]) {
							if(optionName[cartNum] != null && optionName[cartNum] != "" ) {
								menu.push(result["orderList"][j]["menuName"] + "[" + optionName[cartNum] + "]" );
							} else {
								menu.push(result["orderList"][j]["menuName"] );
							}
							menuCount ++;
						}
					}
			
			
					if(next != result["orderList"][i]["orderNum"] ) {
								
						ht += `<li class="order_box">
									<div class="time">
						    			<div>\${moment(date).format("MM월 DD일") }</div>
							    		<div>\${moment(date).format("hh:mm") }</div> 
						    		</div>
					   	
						    		<div class="info">
		                          		<div style="font-weight: bold;">
		                       			<span>
		                      				<span>[메뉴 \${menuCount }개] \${result["orderList"][i]["total"] }원</span> 
		                      				<span class="payMethod"> \${result["orderList"][i]['payMethod'] }</span>
		                    			</span>
		                       		</div>
					                        		
		                       		<div style="font-weight: bold;">\${menu }</div>
		                       		<div style="font-weight: bold;">\${result["orderList"][i]["userAddress2"] }</div>
		                       		
		                       		<div>\${result["orderList"][i]['storeName'] }</div>
		                          		<input type="hidden" value="\${result["orderList"][i]['orderNum'] }" class="order_num" name="orderNum" >
				                  	</div>
					                 			
					                <div class="button_box">
					                 	<input type="button" value="\${btnName }" class="\${btnClass } btn">
					                 </div>
								</li>`;
		 						
		 					next = result["orderList"][i]["orderNum"];
		 					
					}
						
 				} // for i
 	           $(target).html( ht );
    		} // success
    	}); // ajax
	}; // orderList

	
	
	let list = null; 
	// 접수대기 목록 새로고침	
	function refreshOrderList(index){
        setTimeout(function refresh(){
        	orderList("/admin/orderList",{index : index },0);// 접수대기 주문 목록
        	list = setTimeout(refresh , 5000);
        	console.log("list1 = " + list);
        },0);
	}
	
	
	
		    
		$(".closeB").click(function(){
			closeModal(size);
	    });
		    
		$(".modal_bg").click(function(){
	   		closeModal(size);	
	    });
		    
	    $(".closeA").click(function(){
			closeModal(size);
	    });
		    
		$("#accept").click(function(){
			closeModal(size);
		});
		
		$(".cancle").click(function(){
			closeModal(size);
		})
                    	      
 	     function closeModal(size){
			
			$(".modal_bg").hide();
 			modal.scrollTop(0);
 			$(".modal2").scrollTop(0);
 	        $(".modal_box").scrollTop(0);
 	        
 	        $(".accept_modal_box").scrollTop(0);  /////
 	        
 	        
 	        $("body").css("overflow" , "visible");
 	        
 	        if(size > 767) {
  	        	modal.css("transition" , "0s").css("top" , "100%");
 	        } else {
  	        	modal.css("transition" , "0.2s").css("top" , "100%");
 	        }
 	        

 	    };
                    	    

                    	
	}); // document.ready
		
