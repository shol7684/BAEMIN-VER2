<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>

	<span>에이작스 테스트</span>
	
	<input type="text" class="name1">
	<button>전송</button>
	
	<script>
	
	$("button").click(function(){
		const data = {
			name : $(".name1").val()
		};
		
		$.ajax({
			url: "/send",
			type: "GET",
			data : data,
			traditional : true
		})
		.done(function(result){
			console.log(result);
			console.log(JSON.parse(result));
			
			
		})
		.fail(function(){
			console.log("에러발생");
		})
	})
		
	</script>
	
</body>
</html>