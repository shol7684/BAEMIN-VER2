<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/home.css">

<%@ include file="/WEB-INF/view/include/header.jsp" %>
    
    <!-- 주문 완료시 알림창, 관리자 페이지로 주문번호 보내기 -->
    <script>
    	if("${orderMessage }" != "") {
	    	swal("${orderMessage }");
    	} 
    		
    	if("${orderNum}" != "") {
			const orderNum = "${orderNum}";
    		
	    	/* let ws = new WebSocket("ws://localhost:8080/chating"); */
			ws = new WebSocket("ws://211.247.99.55:8080/chating");
			
			ws.onopen = function(data){
				//소켓이 열리면 동작
				console.log("소켯 오픈");
				ws.send(orderNum);
			}
			ws.onclose = function(event) {
				console.log("클로즈");
			}
			
			ws.onerror = function(event) {
				console.log("에러");
			}
    	}
    </script>
    
    
    
 	<!-- 콘텐츠 -->
    <div class="wrap">
        <main>
            <section class="address_search">
                 <div id="search_box">
                     <div>
                         <input type="hidden" id="deleveryAddress1" placeholder="우편번호" value="${BMaddress1 }" name="address1" readonly>
                         <input type="text" onclick="sample2_execDaumPostcode()" value="${BMaddress2 }"
                             id="deleveryAddress2" readonly placeholder="주소를 입력해 주세요 (두정동)" name="address2"><br>
                         <input type="hidden" id="deleveryAddress3" placeholder="상세주소" name="address3" readonly>
                     </div>

                     <div class="search_btn">
                         <label for="search_btn">
                             <i class="fas fa-search"></i>
                         </label>

                         <input type="button" name="search" value="" id="search_btn">

                     </div>

                     <%@ include file="/WEB-INF/view/include/modifyAddress.jsp" %>
                 </div>
            </section>
            <section class="category_box">

                <div class="box">
                    <ul class="category">

                        <li>
                            <ul>
                                <li>
									<span>
                                           <img src="/img/pizza2.png" alt="이미지">
                                       </span>
                                       <span>피자</span>
                                </li>


								<li>
                                        <span>
                                            <img src="/img/chicken1.png" alt="이미지">
                                        </span>
                                        <span>치킨</span>
                                </li>
                                
                                <li>
                                        <span>
                                            <img src="/img/hamburger4.png" alt="이미지">
                                        </span>
                                        <span>패스트푸드</span>
                                </li>
                                
                                <li>
                                        <span>
                                            <img src="/img/bunsik1.png" alt="이미지">
                                        </span>
                                        <span>분식</span>
                                </li>

                                <li>
                                        <span>
                                            <img src="/img/dessert2.png" alt="이미지">
                                        </span>
                                        <span>카페/디저트</span>
                                </li>


                                <li>
                                        <span>
                                            <img src="/img/cutlet1.png" alt="이미지">
                                        </span>
                                        <span>돈까스/일식</span>
                                </li>

                                
                   
                                <li>
                                        <span>
                                            <img src="/img/chinese1.png" alt="이미지">
                                        </span>
                                        <span>중국집</span>
                                </li>

                                <li>
                                        <span>
                                            <img src="/img/jockbal1.png" alt="이미지">
                                        </span>
                                        <span>족발/보쌈</span>
                                </li>

                                <li>
                                        <span>
                                            <img src="/img/jockbal2.png" alt="이미지">
                                        </span>
                                        <span>야식</span>
                                </li>

                                <li>
                                        <span>
                                            <img src="/img/jockbal3.png" alt="이미지">
                                        </span>
                                        <span>한식</span>
                                </li>


                                <li>
                                        <span>
                                            <img src="/img/hamburger4.png" alt="이미지">
                                        </span>
                                        <span>패스트푸드</span>
                                </li>

                            </ul>
                        </li>
                    </ul>
                </div>

            </section>
        </main>
    </div>
    <!-- 콘텐츠 -->


    <!-- 하단 메뉴 -->
	<%@ include file="/WEB-INF/view/include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="/WEB-INF/view//include/footer.jsp" %>
    <!-- 푸터 -->

<script>
	let address1 = $("#deleveryAddress1").val();

	$(".category > li > ul li").click(function(){
		if(!address1) {
			swal("배달 받으실 주소를 입력해 주세요");
			return false;
		}
		
		const index = $(this).index();
		
		location.href = "/store/" + (100+index) + "/" +address1;
	})

</script>


</body>

</html>