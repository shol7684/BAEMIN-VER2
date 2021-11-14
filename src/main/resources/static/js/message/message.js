$(document).ready(function(){
	
	var stompClient = null;
	
	$(function() {
		console.log("connect");
	
		var socket = new SockJS('/gs-guide-websocket');
	
		stompClient = Stomp.over(socket);
	
		stompClient.connect({}, function() {
	
			stompClient.subscribe('/topic/order-complete', function(message) {
				console.log("메세지 도착");
				console.log(message);
			});
		});
	})
	
	$("#send").click(function(){
		send();
	})
	
	
	
	function send() {
		console.log("sendName");
	
		stompClient.send("/message/order-complete-message", {}, $("#message").val());
	
		// JSON.stringify({'name': $("#name").val()})
	}
	
})






