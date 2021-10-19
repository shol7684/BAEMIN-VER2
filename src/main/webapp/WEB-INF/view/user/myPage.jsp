<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/user/myPage.css">

<%@ include file="../include/header.jsp" %>

    <section class="title">
        <h1>my 배민</h1>
    </section>


    <!-- 콘텐츠 -->
    <main>
        <div class="container">
            <div class="content">
                <div class="content_box">
                    <c:if test="${empty SPRING_SECURITY_CONTEXT }">
                        <span class="user">
                            <a href="/login"><span>로그인을 해주세요</span></a>
                        </span>
                    </c:if>

                    <c:if test="${!empty SPRING_SECURITY_CONTEXT }">
                        <span class="user">
                            <a href="/myPage/info"><span>${username }</span></a>
                            <button type="button" class="logout">로그아웃</button>
                        </span>
                    </c:if>

                    <ul>
                        <li>
                        	<a href="/myPage/point">
	                        	<span>
	                        		<img src="/img/icon11.png" alt="포인트">
	                        	</span>
	                        	<span>포인트</span>
                        	</a>
                       	</li>
                        
                        <li>
                        	<a class="updating" href="/myPage/coupon" onclick="return false;">
                        		<span>
                        			<img src="/img/icon22.png" alt="쿠폰함">
                       			</span>
                       			<span>쿠폰함</span>
                      		</a>
                      	</li>
                      	
                        <li>
                        	<a class="updating" href="/myPage/gift" onclick="return false;">
                        		<span>
                        			<img src="/img/icon33.png" alt="선물함">
                        		</span>
                                <span>선물함</span>
							</a>
						</li>
                    </ul>

                    <ul>
						<li>
							<a href="/myPage/dibs">
								<span>
									<img src="/img/icon44.png" alt="찜한가게">
								</span>
								<span>찜한가게</span>
							</a>
						</li>
									
						<li>
							<a href="/orderList">
								<span>
									<img src="/img/icon55.png" alt="주문내역">
								</span>
								
								<span>주문내역</span>
							</a>
						</li>
						
						<li>
							<a href="/myPage/myReview">
							<span>
								<img src="/img/icon66.png" alt="리뷰관리">
							</span>
							<span>리뷰관리</span>
							</a>
						</li>
					</ul>
                </div>
            </div>
        </div>

    </main>
    <!-- 콘텐츠 -->


    <!-- 하단 메뉴 -->
	<%@ include file="../include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="../include/footer.jsp" %>
    <!-- 푸터 -->

    <script type="text/javascript">

        $(".updating").click(function () {
            swal("업데이트 중 입니다");
        })

        $(".logout").click(function () {
            location.href = "/logout";
        })
    </script>

</body>

</html>