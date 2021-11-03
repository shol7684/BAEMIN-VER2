/*

	function imgPreview(img) {
		var i = img;
		console.log("이미지 = " + i);
		img.onchange = function(e) {
	    	  var fileReader = new FileReader();
	    	  fileReader.readAsDataURL(e.target.files[0]);
	
	
	    	  fileReader.onload = function(e) { 
	    		$(".img_box").css("display" ,"block");
	    	    $(".preview").attr("src" , e.target.result);
	
	    	    fileReader.onload;
	    	  }
	    	}
	    	
	    	$(".img_close").click(function(){
	    		$(".preview").attr("src" , "");
	    		$(".img_box").css("display" ,"none");
	    		img.value = "";
	    	});
	    	
	}
	
	function aa() {
		alert(1);
	}
	
	

*/