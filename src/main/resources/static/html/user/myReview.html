<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/reset.css" >
    
    
    <link rel="stylesheet" href="/css/footer.css" >
    <link rel="stylesheet" href="/css/nav.css" >
    <link rel="stylesheet" href="/css/header.css" >
    
    <link rel="stylesheet" href="/css/myReview.css" >

	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
 	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css" integrity="sha384-SZXxX4whJ79/gErwcOYf+zWLeJdY/qpuqC4cAa9rOGUstPomtqpuNWT9wdPEn2fk" crossorigin="anonymous">
    <script type="text/javascript" src="/js/modal.js" ></script>
 
</head>
<body>
	<header>
		<%@ include file="/WEB-INF/view//include/header2.jsp" %>
	</header>
	
	<main>
		<section id="main">
		
		<h1>리뷰 관리</h1>
		
		<h2>내가 쓴 총 리뷰</h2>
		
        <ul class="comment" >
        
        	<c:forEach items="${reviewList }" var="reviewList">
	            <li>
	            	<div class="myReview">
		            	<button class="delete_btn">삭제</button>
		            	<%-- <input type="hidden" value="${reviewList.reviewNum }" class="review_num"> --%>
		            	<div class="title">
		            		<h3>${reviewList.storeName }</h3>
		            		
	            			<c:set var="score"  value="${Math.round(reviewList.score)}"   />
		            		<c:forEach begin="0" end="4" var="i">
		            		
		            			<c:if test="${score < i  }">
		            				<i class="far fa-star"></i>
		            			</c:if>
		            			
		            			<c:if test="${score >= i  }">
		            				<i class="fas far fa-star"></i>
		            			</c:if>
		            			
							
							</c:forEach>
							
							<span><fm:formatDate value="${reviewList.regiDate }" pattern="yyyy-MM-dd" /> </span>
		            	</div>
		            	
		            	<c:if test="${!empty reviewList.reviewImg }">
			            	<div class="img_box">
			            		<img alt="이미지" src="${reviewList.reviewImg }">
			            	</div>
		            	</c:if>
		            	
		            	<div class="text">
		            		${reviewList.content }
		            	</div>
	            	</div>
	            	<c:if test="${!empty reviewList.bossComment }">
		         	    <div class="boss_comment">
		            		<h3>사장님</h3>
		            		<div>${reviewList.bossComment }</div>
		            	</div>
            		</c:if>
	            </li>
            
            </c:forEach>
           

        </ul>
<!-- ----------------------------------- 리뷰 탭 ------------------------------------- -->
	
	</section>
	</main>
	
	<nav>
		<%@ include file="/WEB-INF/view//include/nav.jsp" %>
	</nav>
	
	<footer>
		<%@ include file="/WEB-INF/view//include/footer.jsp" %>
	</footer>

	<script>
		let zoom = false;
		
		$(".img_box img").click(function(){
			if(zoom == false) {
				$(this).css("width" , "100%").css("transition" , "0.3s");
				zoom = true;
			} else {
				$(this).css("width" , "30%").css("transition" , "0.3s");
				zoom = false;
			}
		});
		
		$(".delete_btn").click(function(){
			var a = $(this).siblings(".review_num").val();
			console.log(a);
		})
		
		
		
		
		
	
	
	</script>




</body>
</html>