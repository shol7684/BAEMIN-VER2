<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

<style>
</style>


<button>버튼</button>


<script>
swal("2");
	$("button").click(function(){

		$.ajax({
			url: "/test22",
			type: "POST"
		})
		.done(function(result) {
			console.log("성공");
		})
		.fail(function(result) {
			console.log("실패");
		})
		
	})

</script>


</body>
</html>