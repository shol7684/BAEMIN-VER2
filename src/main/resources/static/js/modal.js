
$(document).ready(function(){
                	 
	 var modal = $(".modal");
	 var modalBg = $("#modal_bg");
	 var closeA = $(".closeA");
	 var closeB = $(".closeB");
	 
	 var modal2 = $(".modal2"); // 추가
	 var cancle = $(".cancle");
     
     closeB.click(function(){

    	 closeModal();
    	 $("input[type='checkBox']").prop("checked", false);
     });
     
     modalBg.click(function(){
    	 closeModal();	
    	 $("input[type='checkBox']").prop("checked", false);
    	 
     });
     
    closeA.click(function(){
	
      	 closeModal();
      	 $("input[type='checkBox']").prop("checked", false);
     });
     
    $(".addCart").click(function(){
	   	closeModal();
  	}); // 장바구니에 담기 버튼 클릭
     
	$("#accept").click(function(){
		closeModal();
	});
	
	cancle.click(function(){
		closeModal();
		
	})
     
     function closeModal(){
		modal.scrollTop(0);
		modal2.scrollTop(0);
        modalBg.hide();
        /*modal.hide();*/
        modal.css("top" , "100%");
        /*$(".modal .btn_box").css("position" , "static");*/
        
        /* timer.hide(); */
        
        $(".modal_box").scrollTop(0);

        $("body").css("overflow" , "visible");
        $("#amount").val(1);
        optionPrice = 0;
        
        $(".plusOption").remove();
    };
 });