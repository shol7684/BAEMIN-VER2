

$(document).ready(function(){
	



$(".aside_tab li").click(function(){
	$(".aside_tab li").removeClass("active");
	$(this).addClass("active");
})




orderList();

function orderList(){
	
	
	$.ajax({
		url: "/admin/orderList",
		type: "get",
		success: function(result) {
		cs(result.length);
			let html = "";
		
			for(var i=0;i<result.length;i++) {
				
				/*<div>${moment(date).format("MM월 DD일") }</div>
	    		<div>${moment(date).format("hh:mm") }</div> */
				
				
				html += `<li class="order_box">
						<div class="time">
			    			<div>${1 }</div>
				    		<div>${1 }</div> 
			    		</div>
		   	
			    		<div class="info">
                      		<div style="font-weight: bold;">
                   			<span>
                  				<span>[메뉴 ${1 }개] ${123 }원</span> 
                  				<span class="payMethod"> ${'카드' }</span>
                			</span>
                   		</div>
		                        		
                   		<div style="font-weight: bold;">${'menu' }</div>
                   		<div style="font-weight: bold;">\${result["orderList"][i]["userAddress2"] }</div>
                   		
                   		<div>\${result["orderList"][i]['storeName'] }</div>
                      		<input type="hidden" value="\${result["orderList"][i]['orderNum'] }" class="order_num" name="orderNum" >
	                  	</div>
		                 			
		                <div class="button_box">
		                 	<input type="button" value="\${btnName }" class="\${btnClass } btn">
		                 </div>
					</li>`;
			}
			
			
			$(".order_list ul").html(html);
			
			
			
		},
		fail: function() {
			alert("에러가 발생했습니다");
		}
		
		
	})
	
	
	
	
	
	
	
}

function cs(value){
	console.log(value);
}
	


})