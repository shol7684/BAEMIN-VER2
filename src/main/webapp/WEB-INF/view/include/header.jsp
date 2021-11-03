<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
header .admin_page_btn {
	font-size: 13px;
	position: absolute;
	right: 10px;
	top: 10px;
	font-weight: bold;
}

header .admin_page_btn a {
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 5px;
	background: #fff;
	font-size: 13px;
	display: block;
}
</style>

</head>
<body onselectstart='return false'>
	<!-- <body> -->


	<header>
		<div id="header">
			<a href="/"><img src="/img/baemin.jpg" alt="이미지"> </a>

			<c:if test="${SPRING_SECURITY_CONTEXT != null }">
				로그인중			
			</c:if>

			<!-- 임시 -->
			<div class="admin_page_btn">
				<div>
					<a href="/admin/main">사장님 페이지</a>
				</div>
			</div>
			<!-- 임시 -->

		</div>
	</header>
	<!-- 헤더 -->


	<script>
		console.log(location.href);
		console.log(location.host);
		console.log(location.pathname); // baemin/home
		console.log(location.hostname);
	</script>