<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/user/findInfo.css">
<%@ include file="/WEB-INF/view/include/header.jsp" %>

	
	<main class="find_id_page">
		<div class="find_info">
			<h3>가입하신 이메일을 입력해주세요</h3>
			<input type="email" name="email" class="email">
			<button class="find_btn">찾기</button>
			<div class="find_password"><a href="/find/password">비밀번호 찾기</a></div>
		</div>
	</main>	

	
	
	

	
<%@ include file="/WEB-INF/view/include/nav.jsp" %>	
<%@ include file="/WEB-INF/view/include/footer.jsp" %>	

	<script>
		$(".find_btn").click(function(){
			const email = $(".email").val();
			if(!emailCheck(email)) {
				swal("이메일을 정확히 입력해주세요");
				return;
			}
			
			
			$.ajax({
				url: "/find/id/sendEmail",
				type: "POST",
				data: {email : email}
			})
			.done(function(){
				const html = 
					`<div class="send_email">
						<div>
							<h3>\${email }</h3>
							<span>으로 아이디를 전송했습니다</span><br>
							<div>가입한 적이 없는 이메일 주소나 올바르지 않은 이메일 주소를 입력하신 경우에는 메일을 받을 수 없습니다.</div>
							<button class="back_btn">돌아가기</button>
						</div>
					</div>`;
					
				$("main").html(html);
					
			})
			.fail(function(){
				alert("에러가 발생했습니다");
			})
		})
	
	
		$(document).on("click", ".back_btn", function(){
			location.href = "/login";
		})
	</script>

</body>
</html>