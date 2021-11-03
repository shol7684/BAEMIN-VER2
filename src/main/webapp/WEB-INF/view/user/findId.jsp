<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<%@ include file="/WEB-INF/view/include/header.jsp" %>

<style>
	body > div {
		width: 100%;
		text-align: center;
		min-height: calc(100vh - 341px);
	}
	
	button {
		border-radius: 5px;
		background: #30DAD9;
		color: #fff;
		padding: 8px 12px;
		border: none;
	}
	form {
		margin-top: 50px; 
	}
	
	.findId input {
		border-radius: 5px;
		border: 1px solid #666;
		width: 20%;
		min-width: 200px;
		padding: 7px 12px;
	}
	
	.send_email {
		margin-top: 50px;
		font-size: 18px;
	}
	.send_email h1 {
		display: inline; 
	}
	
	@media(max-width: 767px)  {
		body > div {
			min-height: calc(100vh - 276px);
			width: 90%;
			margin: 0 auto;
		}
	}
	
</style>
	
</head>
<body>
	<c:if test="${empty sendEmailMessage }">
	<div class="findId">
		<form action="/findId" method="post">
			<h1>가입하신 이메일을 입력해주세요</h1>
			<input type="text" name="email">
			<button>찾기</button>
		</form>
	</div>
	</c:if>
	
	<c:if test="${!empty sendEmailMessage }">
		
	<div class="send_email">
		<div>
			<h1>${sendEmailMessage }</h1>
			<span>으로 아이디를 전송했습니다</span><br>
			<div>가입한 적이 없는 이메일 주소나 올바르지 않은 이메일 주소를 입력하신 경우에는 메일을 받을 수 없습니다.</div>
			<button>돌아가기</button>
		</div>
	</div>
	
	<script>
		$("button").click(function(){
			location.href = "/login";
		})
	</script>
	</c:if>
	
	
<%@ include file="/WEB-INF/view/include/nav.jsp" %>	
<%@ include file="/WEB-INF/view/include/footer.jsp" %>	

</body>
</html>