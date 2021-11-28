
$(".pwd_modify").click(function() {
	const prevPassword = $(".present_password").val();
	const newPassword = $(".new_password").val();
	
	const data = {
		value: newPassword,
		valueType: "password",
		prevPassword : prevPassword
	};
	
	infoModify(data);
})



$(".nickname_modify").click(function() {
	const nickname = $(".nickname").val();
	const checkMsg = $(".nickname_check");

	if (nickname == null || nickname == "") {
		checkMsg.html("닉네임을 입력 해주세요");
		return;
	}

	if (!nicknameCheck(nickname)) {
		checkMsg.html("닉네임은 한글, 영어, 숫자만 4 ~10자리로 입력 가능합니다.");
		return;
	}
	
	const data = {
		value: nickname,
		valueType: "nickname"
	};
	

	$.ajax({
		url: "/overlapCheck",
		type: "get",
		data: data,
	})
	.then(function(result){
		if (result != 0) {
			checkMsg.html("이미 사용중인 닉네임입니다");
		} else {
			checkMsg.html("");

			swal(nickname + "으로 변경하시겠습니까?", {
				buttons: ["취소", "변경하기"],
			})
			.then(function(value){
				if (value == true) {
					infoModify(data);
				}
			})
		}
	})
	.fail(function(){
		alert("에러가 발생했습니다");
	})
}); // nickname_modify




function infoModify(data) {
	$.ajax({
		url: "/user/infoModify",
		type: "PATCH",
		data: data
	})
	.then(function(result){
		swal(result);
	})
	.fail(function(){
		alert("에러가 발생했습니다");
	})
} // nicknameModify

