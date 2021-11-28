<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>

<style>
	body > div {
		margin: 50px;
	}
	
	button {
	}
</style>


<div>
	<ul>
		<li>
			<input type="checkbox" value="btn1">
			<span>1번</span>
		</li>
		<li>
			<input type="checkbox" value="btn2">
			<span>2번</span>
		</li>
		<li>
			<input type="checkbox" value="btn3">
			<span>3번</span>
		</li>
	</ul>
</div>

<button>버튼</button>

<script>
	
	$("button").click(function(){
		
		$("input[type=checkbox]:checked").each(function(){
			const value = $(this).val();
			console.log(value);
		})
		
	})
</script>


</body>
</html>