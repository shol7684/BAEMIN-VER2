<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/link.jsp" %>

<link rel="stylesheet" href="/css/user/login.css">
</head>
<body>
    <!-- <c:if test="${!empty joinFail }">
    	<script>
    		swal('${joinFail }');
    	</script>
    </c:if> -->
	<main>
		<div class="login_box">
        	<a href="/"><img src="/img/bamin2.png" alt="이미지"></a>
				<form action="/join" method="post" onsubmit="return isSubmit.isSubmit();">
					<div class="input_aera">
		            	<input type="text" name="username" class="username" maxlength="20" required placeholder="아이디를 입력해 주세요" >
						<span class="msg_box"></span>
	            	</div>
		               
					<div class="input_aera">
						<input type="password" class="password1" name="password" maxlength="20" required placeholder="비밀번호를 입력해 주세요">
					</div>
	               
					<div class="input_aera">
						<input type="password" class="password2" maxlength="20" required placeholder="비밀번호를 한번더 입력해 주세요">
			            <span class="msg_box"></span>
	               	</div>
		               
	               	<div class="input_aera">
	               		<input type="email" name="email" class="email" required placeholder="이메일을 입력해 주세요" >
		                <span class="msg_box"></span>
               		</div>
		               
					<div class="input_aera">
						<input type="text" class="nickname" name="nickname" maxlength="20" required placeholder="사용하실 닉네임을 입력해 주세요">
	               		<span class="msg_box"></span>
					</div>
		               
					<div class="input_aera">
						<input type=number name="phone" value="" class="phone" onkeypress="return lenthCheck(this, 11);" required placeholder="'-' 없이 입력해 주세요" maxlength="11" >
	                    <span class="msg_box"></span>
	                </div>
		                
	               <input type="submit" value="회원가입" class="login_btn" >
			</form>
        </div>

    </main>
    <script src="/js/util/util.js"></script>
	<script type="text/javascript" src="/js/user/join.js"></script>
</body>
</html>