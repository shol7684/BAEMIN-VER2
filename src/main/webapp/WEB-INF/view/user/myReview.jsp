<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view//include/link.jsp"%>
<link rel="stylesheet" href="/css/user/myReview.css">

<%@ include file="/WEB-INF/view//include/header.jsp"%>

<main>
	<section id="main">

		<h1>리뷰 관리</h1>

		<h2>내가 쓴 총 리뷰</h2>

		<ul class="comment">

			<c:forEach items="${myReviewList }" var="myReviewList" >
				<li>
					<div class="myReview">
						<button class="delete_btn" data-order-num=${myReviewList.orderNum } data-id=${myReviewList.userId } >삭제</button>
						<div class="title">
							<h3>${myReviewList.storeName }</h3>
 
 							<c:forEach begin="0" end="4" var="i">
 								<c:if test="${myReviewList.score >= i}">
 									<i class="fas fa-star"></i>
 								</c:if> 
 								<c:if test="${myReviewList.score < i}">
 									<i class="far fa-star"></i>
 								</c:if> 
 							
 							</c:forEach>
							<span><fm:formatDate value="${myReviewList.regiDate }" pattern="yyyy-MM-dd" /></span>
						</div>

						<c:if test="${!empty myReviewList.reviewImg }">
							<div class="img_box">
								<img alt="이미지" src="${myReviewList.reviewImg }">
							</div>
						</c:if>

						<div class="text">${myReviewList.reviewContent }</div>
					</div> 
					
					<c:if test="${!empty myReviewList.bossComment }">
						<div class="boss_comment">
							<h3>사장님</h3>
							<div>${myReviewList.bossComment }</div>
						</div>
					</c:if>
				</li>
			</c:forEach>


		</ul>
		<!-- ----------------------------------- 리뷰 탭 ------------------------------------- -->

	</section>
</main>


	<%@ include file="/WEB-INF/view//include/nav.jsp"%>

	<%@ include file="/WEB-INF/view//include/footer.jsp"%>

<script>
	let zoom = false;

	$(".img_box img").click(function() {
		if (zoom == false) {
			$(this).css("width", "100%").css("transition", "0.3s");
			zoom = true;
		} else {
			$(this).css("width", "30%").css("transition", "0.3s");
			zoom = false;
		}
	});

	$(".delete_btn").click(function() {
		const orderNum = $(this).data().orderNum;
		const userId = $(this).data().id;
		const index = $(this).parent().parent().index();
		
		swal({
			buttons: ["취소", "삭제"],
			title: "리뷰를 삭제합니다",
		})
		.then((value) => {
			if (value == true) {
				
				 $.ajax({
					url: "/user/review",
					type: "DELETE",
					data: {orderNum : orderNum , id : userId},
					success: function(){
						$(".comment > li").eq(index).remove();
					},
					fail: function(){
						alert("실패");
					}
				}) // ajax 
			}
		});	
		
		
	})
	
	
	
	
</script>




</body>
</html>