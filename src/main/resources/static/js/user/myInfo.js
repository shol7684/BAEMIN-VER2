
$(".pwd_modify").click(function() {
	const prePassword = $(".present_password").val();
	const newPassword = $(".new_password").val();
	
	const data = {
		value: newPassword,
		valueType: "password",
		password : prePassword
	};
	
	infoModify(data);
})



$(".nickname_modify").click(function() {
	const nickname = $(".nickname").val();
	const nicknameCheck = $(".nickname_check");

	const regNickname = /^[가-힣|a-z|A-Z|0-9|]+$/;

	if (nickname == null || nickname == "") {
		nicknameCheck.html("닉네임을 입력 해주세요");
		return false;
	}

	if (!regNickname.test(nickname)) {
		nicknameCheck.html("닉네임은 한글, 영어, 숫자만 4 ~10자리로 입력 가능합니다.");
		return false;
	}
	
	const data = {
		value: nickname,
		valueType: "nickname"
	};
	

	$.ajax({
		url: "/overlapCheck",
		type: "get",
		data: data,
		async: false,
		success: function(result) {

			if (result != 0) {
				nicknameCheck.html("이미 사용중인 닉네임입니다");
			} else {
				nicknameCheck.html("");

				swal(nickname + "으로 변경하시겠습니까?", {
					buttons: ["취소", "변경하기"],
				}).then((value) => {
					if (value == true) {

						infoModify(data);

					}
				});
			}
		}, error: function() {
			swal("실패");
		}// success
	}); // ajax
}); // nickname_modify


function infoModify(data) {

	$.ajax({
		url: "/user/infoModify",
		type: "PATCH",
		data: data,
		success: function(result) {

			swal(result);
		}, // success

		error: function() {
			swal("실패");
		}

	}); // ajax

} // nicknameModify

