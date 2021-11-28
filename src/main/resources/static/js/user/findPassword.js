	
// 비밀번호 변경시 비밀번호입력 상태 확인
const isSubmit = (function() {
	let submit = false;
	
	const getSubmit =function() {
		return submit;
	}
	
	const setSubmit = function(set){
		submit = set;
		return submit;
	}
	return {getSubmit : getSubmit, setSubmit : setSubmit};
})();	
	
	
		
// 체크박스 찾을 방법 선택		
const findMethod = (function(){
	let method = "email";
	
	const getMethod = function(){
		return method;
	}
	const setMethod = function(set){
		method = set;
	}
	return {getMethod : getMethod, setMethod : setMethod }
})();		
		
	
		
// 인증번호 발송했는지 여부
const authStatus = (function(){
	let auth = false;
	const getAuth = function(){
		return auth;
	}	
	const setAuth = function(set){
		auth = set;
		return auth;
	}
	return {getAuth : getAuth , setAuth : setAuth }
})();

		
	
		
function usernameCheck() {
	let submit = false;
	const username = $("input[name=username]").val().replaceAll(" ", "");
	if(!username) {
		return false;
	}
	
	$.ajax({
		url: "/overlapCheck",
		type: "GET",
		async: false,
		data: {
			value : username,
			valueType : "username"
		}
	})
	.done(function(result){
		if(result == 1) {
			submit = true;
		} else {
			swal("아이디를 정확히 입력해주세요");
		} 
	})
	.fail(function(){
		alert("에러");
	})
	return submit;
}
	

	

function validityTimer(time){
	const target = findMethod.getMethod() == "email" ? $("#find_by_email").find(".validity_time") : $("#find_by_phone").find(".validity_time");
	
	let minute = Math.floor(time / 60);
	let second = time % 60;
	let timer = setInterval(function(){
		
		const text = minute + ":" + second.toString().padStart(2, '0');
		target.text(text);
		second--;
		time--;
		
		if(second < 0 && 0 < minute) {
			second = 59;
			minute--;
		}
		
		if(time < 0) {
			clearInterval(timer);
		}
	},1000)
}	





function pwdCheck() {
	const password1 = $(".password1").val();
	const password2 = $(".password2").val();

	if (password1 != "" && password2 != "") {

		if(password1 != password2) {
			$(".password_check_msg").html("비밀번호를 확인해 주세요");
			isSubmit.setSubmit(false);
		} else {
			$(".password_check_msg").html("");
			isSubmit.setSubmit(true);
		}
	}
}




function authNumSend(data, url){
	$.ajax({
		url: url,
		type: "GET",
		data: data
	})
	.done(function(result){
		if(!result) {
			swal("가입하신 정보를 확인해주세요");
			return;
		}
		swal("인증번호를 발송했습니다");
		$(".auth_num").prop("readonly", false);
		$("input[name=userId]").val(result["userId"]);
		validityTimer(result["validityTime"]);
		authStatus.setAuth(true);
		
	})
	.fail(function(){
		alert("에러");
	})
}





function moveModify(authNum){
	$.ajax({
		url: "/authNumCheck",
		type: "GET",
		data: {authNum : authNum}
	})
	.done(function(result){
		if(result["status"] == "success") {
			const userId = $(".user_id").val();
			location.href = "/find/password/auth/modify?userId=" + userId + "&uuid=" + result["uuid"] + "&authNum=" + authNum;
		} else {
			swal(result["msg"]);
		}
	})
	.fail(function(){
		alert("에러");
	})
}




$("input[type=radio]").change(function(){
	const method =  $(this).attr("id");
	findMethod.setMethod(method);
})




// 다음 버튼
$(".move_modify").click(function(){
	if(!authStatus.getAuth()) {
		swal("인증번호를 발송해주세요");
		return;
	}
	
	let authNum = "";
	
	$("input[type=radio]:checked").each(function(){
		authNum = $(this).siblings(".auth").find(".auth_num").val(); 
	})
	
	if(!authNum) {
		return;
	}
	
	moveModify(authNum);
})




// 이메일로 인증번호 보내기
$(".auth_num_send_eemail").click(function(){
	const data = {
		email : $(".email").val(),
		username : $("input[name=username]").val(),
	}
	const url = "/authEmail"; 
	
	if(!emailCheck(data.email)) {
		swal("이메일을 정확히 입력해주세요");
		return;
	}
	authNumSend(data, url);
})	


// 전화번호로 인증번호 보내기
$(".auth_num_send_phone").click(function(){
	const data = {
		phone : $(".phone").val(),
		username : $("input[name=username]").val(),
	}
	const url = "/authPhone";
	if(!phoneCheck(data.phone)) {
		swal("전화번호를 정확히 입력해주세요");
		return;
	}
	authNumSend(data, url);
})


	
	
$(".password1").focusout(function() {
	pwdCheck();
});

$(".password2").focusout(function() {
	pwdCheck();
});
	

// 패스워드 변경
$(".modify_btn").click(function(){
	if(!isSubmit.getSubmit()) {
		return;
	}
	
	const data = {
		password : $(".password1").val(),
		uuid : $("input[name=uuid]").val(),
		userId : $("input[name=userId]").val()
	}
	
	$.ajax({
		url: "/modifyPassword",
		type: "PATCH",
		data: data
	})
	.done(function(result){
		swal({
			text : result,
			closeOnClickOutside : false
		})
		.then(function(){
			location.href = "/login";
		})
	})
	.fail(function(){
		alert("에러");
	})
})	


	