<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/user/findInfo.css">
<%@ include file="/WEB-INF/view/include/header.jsp" %>
	
	<main class="find_password_page">
		<div class="find_info">
			<form action="/find/password/auth" method="get" class="find_form" onsubmit="return usernameCheck();">
				<h3>가입하신 아이디를 입력해주세요</h3>
				<input type="text" name="username" class="username">
				<button>다음</button>
			</form>
		</div>
	</main>
	
	
	
<%@ include file="/WEB-INF/view/include/nav.jsp" %>	
<%@ include file="/WEB-INF/view/include/footer.jsp" %>	

	<script src="/js/user/findPassword.js"></script>


<script>

</script>
</body>
</html>