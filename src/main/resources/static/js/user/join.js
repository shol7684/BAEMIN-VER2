$(".login_btn").css("background", "#ddd");
$(".login_btn").attr('disabled', true);

let usernameCheck = 0;
let passwordCheck = 0;
let emailCheck = 0;
let nicknameCheck = 0;
let phoneCheck = 0;


$(".username").focusout(function(){
	const usernamecheckMsg = $(".username_check_msg");
	const data = {value : $(".username").val(), valueType : "username" };
	const impossibilityMsg = "이미 사용중인 아이디입니다"
	const possibilityMsg = "사용 가능합니다"
	
	overlapCheck(data, usernamecheckMsg, impossibilityMsg, possibilityMsg);	
	
});


$(".password1").focusout(function() {
	pwdCheck();
	inputCheck();
});

$(".password2").focusout(function() {
	pwdCheck();
	inputCheck();
});

$(".email").focusout(function() {

	const email = $(".email").val();
	const emailCheckMsg = $(".email_check_msg");
	const regEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

	if (email == null || email == "") {
		emailCheckMsg.html("이메일을 입력해 주세요");
		emailCount = 0;
		return;
	}

	if (!regEmail.test(email)) {
		emailCheckMsg.html("사용 불가능합니다");
		emailCount = 0;
		return;
	}
	emailCheckMsg.html("");
	emailCheck = 1;
	inputCheck();

}); // function


$(".nickname").focusout(function() {

	const nickname = $(".nickname").val();
	const nicknameCheckMsg = $(".nickname_check_msg");
	const regNickname = /^[가-힣|a-z|A-Z|0-9|]+$/;

	if (nickname == null || nickname == "") {
		nicknameCheckMsg.html("닉네임을 입력 해주세요");
		inputCheck();

		return false;
	}

	if (!regNickname.test(nickname)) {
		nicknameCheckMsg.html("닉네임은 한글, 영어, 숫자만 4 ~10자리로 입력 가능합니다.");
		inputCheck();

		return false;
	}

	console.log("test=" + regNickname.test(nickname));

	let data = {
		value: nickname,
		valueType : "nickname" 
	};

	const impossibilityMsg = "이미 사용중이거나 탈퇴한 닉네임입니다";
	const possibilityMsg = "사용 가능합니다";

	overlapCheck(data, nicknameCheckMsg, impossibilityMsg, possibilityMsg);


}); // nickname check 



$(".phone").focusout(function() {

	const regPhone = /[0-9]{10,12}$/;
	const phoneCheckMsg = $(".phone_check_msg");

	!regPhone.test($(".phone").val()) ? phoneCheckMsg.html("휴대폰번호를 확인해 주세요") : phoneCheckMsg.html("");

	if(phoneCheckMsg != "") {
		
		if(!regPhone.test($(".phone").val())) {
			phoneCheckMsg.html("휴대폰번호를 확인해 주세요");
			phoneCheck = 0;
		} else {
			 phoneCheckMsg.html("");
			 phoneCheck = 1;
		}
	}

	inputCheck();
});


/*폰번호 길이제한 11자*/
function lenthCheck(e) {
	
	if(e.value.length > 10) {
		return false;
	}
	
	return true;
}

function overlapCheck(data, target, impossibilityMsg, possibilityMsg) {
	$.ajax({
		url: "/overlapCheck",
		type: "get",
		data: data,
		success: function(result) {

			let valueType = data["valueType"];
			
			if (result == 0 ) {
				
				target.html(possibilityMsg);
				
				if(valueType == "username") {
					usernameCheck = 1;
				} else if (valueType == "email") {
					emailCheck = 1;
				} else if (valueType == "nickname") {
					nicknameCheck = 1;	
				}
			} else {
				target.html(impossibilityMsg);
				
				if(valueType == "username") {
					usernameCheck = 0;
				} else if (valueType == "email") {
					emailCheck = 0;
				} else if (valueType == "nickname") {
					nicknameCheck = 0;	
				}
			}
			
			inputCheck();
		} // success
	}); // ajax
}


function pwdCheck() {
	const password1 = $(".password1").val();
	const password2 = $(".password2").val();

	if (password1 != "" && password2 != "") {

		if(password1 != password2) {
			$(".password_check_msg").html("비밀번호를 확인해 주세요");
			passwordCheck = 0;
		} else {
			$(".password_check_msg").html("");
			passwordCheck = 1;
		}
	}
}

function inputCheck() {

	let count = usernameCheck + passwordCheck + emailCheck + nicknameCheck + phoneCheck ;
/*	
	console.log(`usernameCheck = ${usernameCheck}
	passwordCheck = ${passwordCheck}
	emailCheck = ${emailCheck}
	nicknameCheck = ${nicknameCheck}
	phoneCheck = ${phoneCheck}
	`);
*/
	console.log(`count = ${count}`);

	if(count == 5) {
		$(".login_btn").css("background", "#2AC1BC");
		$(".login_btn").attr('disabled', false);
	} else {
		$(".login_btn").css("background", "#ddd");
		$(".login_btn").attr('disabled', true);
	}
}
