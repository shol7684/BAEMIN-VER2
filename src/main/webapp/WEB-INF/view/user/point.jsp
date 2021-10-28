<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view//include/link.jsp" %>
<link rel="stylesheet" href="/css/user/point.css" >
<%@ include file="/WEB-INF/view//include/header.jsp" %>
	
    <section class="title">
        <h1>포인트</h1>
    </section>

	
	<main>
		<div class="my_point">
            <div>
                <span>보유 포인트</span>
                <fm:formatNumber value="${point }" />원
            </div>
            
            <div class="point_regi">
                <div>
                    <button>상품권 등록하기</button>
                </div>

                <div class="point_number_area">
                	<form action="/admin/pointRegist" method="post">
	                    <input type="text" class="point_number" name="giftCardNum" placeholder="상품권 번호를 입력해주세요">
	                    <input type="submit" value="등록">
                    </form>
                </div>
            </div>
            
		<div style="font-size: 15px; margin-top: 10px;">상품권번호 QKRTNALS</div>
		
		
        </div>
		
		<h2>포인트 이용 내역</h2><hr>
		<ul>
			<c:forEach items="${myPoint }" var="myPoint">
				<li>
	                <div>
	                    <div>${myPoint.info }</div>
	                    <div><fm:formatDate value="${myPoint.usedDate }" pattern="yyyy.MM.dd" /> </div>
	                </div>
	
	                <div>
	                	<c:if test="${myPoint.point > 0 }">
	                		<div class="plus">+<fm:formatNumber value="${myPoint.point }" pattern="###,###"/> 원 적립</div>
	                	</c:if>
	                	
	                	<c:if test="${myPoint.point < 0 }">
	                		<div><fm:formatNumber value="${myPoint.point }" pattern="###,###"/> 원 사용</div>
	                	</c:if>
	                	
	                </div>
				</li><hr>
					
				
	
			</c:forEach>
			
		</ul>
	</main>
	
	
<%@ include file="/WEB-INF/view//include/nav.jsp" %>

<%@ include file="/WEB-INF/view//include/footer.jsp" %>
	
	
	
    <script>
       $(".point_regi button").click(function(){
           $(".point_number_area").fadeToggle(100);
       })

    </script>
</body>
</html>