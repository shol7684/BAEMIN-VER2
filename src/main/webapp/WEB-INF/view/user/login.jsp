<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/link.jsp" %>

<link rel="stylesheet" href="/css/user/login.css">
</head>
<body>
    <main>
    
    <script type="text/javascript">
		const msg = "${msg}";
		
		if(msg != "") {
			swal(msg);
		}
    </script>
    
     
    
        <div class="login_box">
			<a href="/"><img src="/img/bamin2.png" alt="이미지"></a>    
            
            <form action="/login" method="post">

	            <div class="input_aera"><input type="text" name="username"  value="admin" required placeholder="이메일을 입력해 주세요" maxlength="30" ></div>
	            <div class="input_aera"><input type="password" name="password" value="1111" required placeholder="비밀번호를 입력해 주세요" maxlength="30"></div>

				<input type="submit" value="로그인" class="login_btn" >
            
				<div class="box">
					<div class="continue_login">
						<i class="fas fa-check-square" ></i>
						<input type="checkbox" id="continue_login" name="remember-me" > 
						<label for="continue_login">로그인 유지하기</label> 
					</div>
					
		            <div>
		            	<span class="id_search"><a href="/findId">아이디</a></span>
			            <span> ㅣ </span>
			            <span><a href="">비밀번호 찾기</a></span>
			            
		            </div>
	            </div>
            </form>
            
			<div id="oauth_login">
				<div>
					<a href="/oauth2/authorization/google">	
						<img src="/img/kakao_login_btn.png">
					</a>
				</div>

				<div>
					<a href="/kakkoLogin">
						<img src="/img/naver_login_btn2.png">
					</a>
				</div>
				
				<div>
					<a href="/oauth2/authorization/google">
						<img src="/img/google_login_btn.png">
					</a>
				</div>
			</div>
			
			<div class="join"><a href="/join" >회원 가입하러 가기</a></div>
        </div>
    </main>
    
    
    <script>
    	$(".continue_login i").click(function(){
    	
    		if($("input[type='checkbox']").is(":checked")) {
	    		$("input[type='checkbox']").prop("checked" , false);
	    		$(this).css("color" , "#999999");
	    		
    		} else {
	    		$("input[type='checkbox']").prop("checked" , true);
	    		$(this).css("color" , "#2AC1BC");
    		}
    		
    		
    	})
    	
    	$("input[type='checkbox']").change(function(){
    		
    		console.log($("input[type='checkbox']").is(":checked"));
    		
    		if($("input[type='checkbox']").is(":checked")) {
	    		$(".continue_login i").css("color" , "#2AC1BC");
    		} else {
	    		$(".continue_login i").css("color" , "#999999");
    		}
    	})
 
    
    
    </script>
    
    
</body>
</html>