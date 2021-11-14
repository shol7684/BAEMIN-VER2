

function lenthCheck(e, length) {
	if(e.value.length >= length) {
		return false;
	}
	
	$(this).off().focusout(function(){
		if(e.value.length > length) {
			e.value = "";
		}
	})
	
	return true;
}



function imgPreview(e,target){
	const preview = target.siblings("div").find(".preview");
	const fileReader = new FileReader();

	fileReader.readAsDataURL(e.target.files[0]);

	fileReader.onload = function() {
		preview.attr("src", fileReader.result);
		$(".img_box div").css("display", "block");
	}
}
	
	
function imgClose() {
	$(".preview").attr("src", "");
	$("#img").val("");
	$(".img_box div").css("display", "none");
}







function openModal(modal, size) {

	if (size > 767) {
		modal.css("transition", "0s").css("top", "0%");
		console.log("pc");
	} else {
		modal.css("transition", "0.2s").css("top", "0%");
		console.log("mobile");
	}
	$("#modal_bg").show();
	$("body").css("overflow", "hidden");
	$("body").css("overflow-y", "hidden");
}



$(".closeA").click(function() {
	closeModal();
});


$("#modal_bg").click(function() {
	closeModal();

});

$(".closeB").click(function() {
	closeModal();
});


function closeModal() {
	$("#modal_bg").hide();
	$(".modal").css("top", "100%");
	$(".modal_box").scrollTop(0);
	$("body").css("overflow", "visible");
	$("input[type='checkBox']").prop("checked", false);
};




