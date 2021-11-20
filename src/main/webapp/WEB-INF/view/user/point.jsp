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
                <span id="my_point" data-point="${point }"><fm:formatNumber value="${point }" />원</span>
            </div>
            
            <div class="point_regi">
                <div>
                    <button>상품권 등록하기</button>
                </div>
				
                <div class="point_number_area">
					<div>
	                    <input type="text" class="point_number" maxlength="20" name="giftCardNum" placeholder="상품권 번호를 입력해주세요">
	                    <input type="button" value="등록">
	                </div>
                </div>
            </div>
            
		<div style="font-size: 15px; margin-top: 10px;">상품권번호 QKRTNALS</div>
		
		
        </div>
		
		<h2>포인트 이용 내역</h2><hr>
		<ul class="point_his">
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
$(document).ready(function(){
	 $(".point_regi button").click(function(){
         $(".point_number_area").fadeToggle(100);
     })
     
     $(".point_number_area input[type=button]").click(function(){
  	   
		   const giftCardNum = $(".point_number").val().replaceAll(" ", "");
		   
		   if(!giftCardNum) {
			   return;
		   }
		   
		$.ajax({
			url: "/admin/pointRegist",
  			data: {giftCardNum : giftCardNum},
			type: "POST"
		})
		.done(function(result){
			
			if(!result) {
				swal("잘못된 번호입니다");	
				return;
			}
			const point = result["point"]; 
			swal(point.toLocaleString() + "원이 적립되었습니다");
			$("#my_point").text((Number($("#my_point").data("point")) + point).toLocaleString() + "원");
			$(".point_number_area").fadeToggle(100);
	 		htmlWrite(result);  		   
  	   })
  	   .fail(function(){
  		   swal("에러");
  	   })
     })
     
     
	function htmlWrite(result){
		 const date = new Date();
		 const now =  date.getFullYear() + "." + (date.getMonth() + 1) + "." + date.getDate();
		 const html = 
			 `<li>
				<div>
           	        <div>\${result["info"] }</div>
                    <div>\${now }</div>
                </div>

                <div>
                	<div class="plus">+\${result["point"].toLocaleString() } 원 적립</div>
                </div>
			</li><hr>`;
	     $(".point_his").prepend(html);
	 }     
     
     
     
     
     
     
     
     
     
     
})
    
      
       
       
       
       
       

    </script>
</body>
</html>