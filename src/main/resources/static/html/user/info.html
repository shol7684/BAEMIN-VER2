<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
   	<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/reset.css" >
    <link rel="stylesheet" href="/css/myPageInfo.css" >
    
    <link rel="stylesheet" href="/css/nav.css" >
    <link rel="stylesheet" href="/css/header.css" >
    <link rel="stylesheet" href="/css/footer.css" >
    
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
</head>
<body>

   	<header>
	    <%@ include file="/WEB-INF/view/include/header2.jsp" %>
	</header>
	
	<div class="wrap">
	
		<section class="title">
			<h1>정보 수정</h1>
		</section>
		
	    <main>
		    <div class="info_box">
		    	
		    	<div>
					<h2>이메일</h2>
					<div class="email">${user.userId }</div>		    	
		    	
		    	</div>
		    	
		    	<div>
		    		<h2>비밀번호</h2>
		    		<span class="input_area"><input class="pwd" type="password"></span>
		    		<span class="submit_area"><input type="button" class="pwd_modify" value="변경"></span>
		    	</div>
		    	
		    
	            <div>
	            	<h2>닉네임 변경</h2>
	            	<span class="input_area"><input type="text" class="nickname" name="nickname"  value=""  required placeholder="변경하실 닉네임을 입력해 주세요"></span>
					<span class="submit_area"><input type="button" value="수정" class="nickname_modify"  ></span>
	            	<div class="nickname_check"></div>
					
				</div>
				
				<div>
					<h2>휴대폰 번호 변경</h2>
	            	<span class="input_area"><input type="text" class="phone" name="nickname"  value=""  required placeholder=""></span>
					<span class="submit_area"><input type="button" value="재인증" class=""  ></span>
	           	</div>	
		
			</div>
	
	    </main>
	    
	    
	    
	    <script>
	    
	    $(".pwd_modify").click(function(){
	    	const pwd = $(".pwd").val();
    	
    		infoModify(pwd,1);
	    })
	    		
	    
	    
	    $(".nickname_modify").click(function(){
	    	const nickname = $(".nickname").val();
	    	const nicknameCheck = $(".nickname_check");
	    	
	    	let data = {
	    			functionNumber :0,
					nickname:nickname
		       };
	    	
	    	const regNickname =  /^[가-힣|a-z|A-Z|0-9|]+$/;

    		if(nickname == null || nickname == ""){
    			nicknameCheck.html("닉네임을 입력 해주세요");
    			return false;
    		}
    		
    		if(!regNickname.test(nickname)){
    			nicknameCheck.html("닉네임은 한글, 영어, 숫자만 4 ~10자리로 입력 가능합니다.");
    			return false;
    		}

	       	$.ajax({
	       		url : "/overlapCheck",
	       	    type : "post",
	       	    data : data,
	       	    async : false,
	       	    success : function(result){
	       	    	
	       	    	 
	       	    	if(result == 1) {
	       	    		nicknameCheck.html("이미 사용중이거나 탈퇴한 닉네임입니다");
	      			
	       	    	} else {
						nicknameCheck.html("");
	       	    		
						swal(nickname + "으로 변경하시겠습니까?" , {
	      				  buttons: ["취소", "변경하기"],
	      				}).then((value) => {
							if(value == true) {
								
								infoModify(nickname,0);
								
	      					 }  
	      				});
	       	    	} 
	       		} ,error : function() {
	       			swal("실패" );
	       		}// success
	       	}); // ajax
	    }); // nickname_modify
	    
	    
	    function infoModify(info,functionNum ){
	    
		    $.ajax({
	       		url : "/infoModify",
	       	    type : "post",
	       	    data : { info : info , functionNum : functionNum},
	       	    success : function(result){
	       	    	
	       	    	swal("변경되었습니다" );
	       		}, // success
	       		
	       		error : function() {
	       			swal("실패" );
	       		}
	       		
	       	}); // ajax
	    
	    } // nicknameModify
	    	
	    </script>
    
    </div>
    
    
        <!-- 하단 메뉴 -->
    <nav>
    	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    </nav>
    <!-- 하단 메뉴 -->

	<!-- 푸터 -->
    <footer>
		<%@ include file="/WEB-INF/view/include/footer.jsp" %>
    </footer>
	<!-- 푸터 -->

    
</body>
</html>