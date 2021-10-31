<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

<style>
	.room {
	width: 400px;
	height: 500px;
	background: black;
	}
	
	.room > div {
	color:  #fff;
	
	}
</style>

</head>
<body>


<div class="room">
	<div></div>

</div>


<input type="text" class="input">
<button type="button" class="btn">전송</button>


<script>
	console.log("location.host = " + location.host);
	
	var ws;
	
	
	connect();

	function connect(){
		
		/* ws = new WebSocket("ws://localhost:8080/chating"); */ 
		ws = new WebSocket("ws://211.247.99.55:8080/chating");
		/* ws = new WebSocket("ws://" + location.host + "/chating"); */
		
		ws.onopen = function(data){
			//소켓이 열리면 동작
			console.log("소켯 오픈");
		}
		
		ws.onmessage = function(data) {
			//메시지를 받으면 동작
			
			console.log("메시지 도착");
			console.log(data.data);
			
			const text = `<div>\${data.data}</div>`;
			$(".room").append(text);	
		}
		
		ws.onclose = function(event) {
			console.log("클로즈");
		}
		
		ws.onerror = function(event) {
			console.log("에러");
		}
	}
	
	$("button").click(function(){
		console.log(1);
		ws.send($(".input").val());
	})
	
	$(".input").keyup(function(e){
		if(e.keyCode == 13) {
			ws.send($(".input").val());
		}
		
		
	})

	
	</script>
	
</body>
</html>