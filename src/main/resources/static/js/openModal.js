

    function openModal(modal,size) {
		
		if(size > 767) {
			modal.css("transition" , "0s").css("top" , "0%");
			console.log("pc");
		} else {
			modal.css("transition" , "0.2s").css("top" , "0%");
			console.log("mobile");
		}
		$("#modal_bg").show();
		$("body").css("overflow","hidden");
		$("body").css("overflow-y","hidden");
	}

