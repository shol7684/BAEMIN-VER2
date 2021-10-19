<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/link.jsp" %>
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/home.css">

<%@ include file="./include/header.jsp" %>
    
    <!-- 콘텐츠 -->

    <div class="wrap">
        <main>
            <section class="address_search">

                <form action="/" method="post">
                    <div id="search_box">
                        <div>
                            <input type="hidden" id="sample2_postcode" placeholder="우편번호" name="address1" readonly>
                            <input type="text" onclick="sample2_execDaumPostcode()" value="${BMaddress2 }"
                                id="sample2_address" readonly placeholder="주소를 입력해 주세요 (두정동)" name="address2"><br>
                            <input type="hidden" id="sample2_detailAddress" placeholder="상세주소" name="address3" readonly>
                            <input type="hidden" id="sample2_extraAddress" placeholder="참고항목" name="address4" readonly>
                        </div>

                        <div class="search_btn">
                            <label for="search_btn">
                                <i class="fas fa-search"></i>
                            </label>

                            <input type="submit" name="search" value="" id="search_btn">

                        </div>

                        <!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
                        <div id="layer"
                            style="display:none;position:fixed;overflow:hidden;z-index:2;-webkit-overflow-scrolling:touch;">
                            <img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer"
                                style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1"
                                onclick="closeDaumPostcode()" alt="닫기 버튼">
                        </div>

                        <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
                        <script type="text/javascript">
                            $("#sample2_address").on("propertychange change keyup paste input", function () {

                                // 현재 변경된 데이터 셋팅
                                /* newValue = $(this).val(); */

                                // 현재 실시간 데이터 표츌
                                /* alert("텍스트 :: " + newValue); */
                            });
                        </script>

                        <script>
                            // 우편번호 찾기 화면을 넣을 element
                            var element_layer = document.getElementById('layer');

                            function closeDaumPostcode() {
                                // iframe을 넣은 element를 안보이게 한다.
                                element_layer.style.display = 'none';

                            }

                            function sample2_execDaumPostcode() {
                                new daum.Postcode({
                                    oncomplete: function (data) {
                                        // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                                        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                                        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                                        var addr = ''; // 주소 변수
                                        var extraAddr = ''; // 참고항목 변수

                                        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                                        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                                            addr = data.roadAddress;
                                        } else { // 사용자가 지번 주소를 선택했을 경우(J)
                                            addr = data.jibunAddress;
                                        }

                                        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                                        if (data.userSelectedType === 'R') {
                                            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                                            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                                            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                                extraAddr += data.bname;
                                            }
                                            // 건물명이 있고, 공동주택일 경우 추가한다.
                                            if (data.buildingName !== '' && data.apartment === 'Y') {
                                                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                                            }
                                            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                                            if (extraAddr !== '') {
                                                extraAddr = ' (' + extraAddr + ')';
                                            }
                                            // 조합된 참고항목을 해당 필드에 넣는다.
                                            document.getElementById("sample2_extraAddress").value = extraAddr;

                                        } else {
                                            document.getElementById("sample2_extraAddress").value = '';
                                        }

                                        // 우편번호와 주소 정보를 해당 필드에 넣는다.
                                        document.getElementById('sample2_postcode').value = data.zonecode;
                                        document.getElementById("sample2_address").value = addr;

                                        /* ---------------------------- form 자동 서브밋 추가 ---------------------------- */

                                        /*          setTimeout(function(){
                                                     
                                                     $("#search_btn").trigger("click");
                                                     
                                                  $(".search_btn").bind("click", function(){
                                                  });
                                                  
                                                 },100);  */

                                        $("#search_btn").click();



                                        /* ---------------------------- form 자동 서브밋 추가 ---------------------------- */


                                        // 커서를 상세주소 필드로 이동한다.
                                        document.getElementById("sample2_detailAddress").focus();

                                        // iframe을 넣은 element를 안보이게 한다.
                                        // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                                        element_layer.style.display = 'none';

                                    },
                                    width: '100%',
                                    height: '100%',
                                    maxSuggestItems: 5
                                }).embed(element_layer);

                                // iframe을 넣은 element를 보이게 한다.
                                element_layer.style.display = 'block';

                                // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
                                initLayerPosition();
                            }

                            // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
                            // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
                            // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
                            function initLayerPosition() {
                                var width = 300; //우편번호서비스가 들어갈 element의 width
                                var height = 400; //우편번호서비스가 들어갈 element의 height
                                var borderWidth = 5; //샘플에서 사용하는 border의 두께

                                // 위에서 선언한 값들을 실제 element에 넣는다.
                                element_layer.style.width = width + 'px';
                                element_layer.style.height = height + 'px';
                                element_layer.style.border = borderWidth + 'px solid';
                                // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계다.
                                element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width) / 2 - borderWidth) + 'px';
                                element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height) / 2 - borderWidth) + 'px';
                            }


                        </script>
                    </div>
                </form>
            </section>
            <section class="category_box">

                <div class="box">
                    <ul class="category">

                        <li>
                            <ul>
                                <li>
                                    <a href="/store/100/${BMaddress1 }">
                                        <span>
                                            <img src="/img/pizza2.png" alt="이미지">
                                        </span>
                                        <span>피자</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/103/${BMaddress1 }">
                                        <span>
                                            <img src="/img/bunsik1.png" alt="이미지">
                                        </span>
                                        <span>분식</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/104/${BMaddress1 }">
                                        <span>
                                            <img src="/img/dessert2.png" alt="이미지">
                                        </span>
                                        <span>카페/디저트</span>
                                    </a>
                                </li>


                                <li>
                                    <a href="/store/105/${BMaddress1 }">
                                        <span>
                                            <img src="/img/cutlet1.png" alt="이미지">
                                        </span>
                                        <span>돈까스/일식</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/101/${BMaddress1 }">
                                        <span>
                                            <img src="/img/chicken1.png" alt="이미지">
                                        </span>
                                        <span>치킨</span>
                                    </a>
                                </li>
                   
                                <li>
                                    <a href="/store/106/${BMaddress1 }">
                                        <span>
                                            <img src="/img/chinese1.png" alt="이미지">
                                        </span>
                                        <span>중국집</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/107/${BMaddress1 }">
                                        <span>
                                            <img src="/img/jockbal1.png" alt="이미지">
                                        </span>
                                        <span>족발/보쌈</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/108/${BMaddress1 }">
                                        <span>
                                            <img src="/img/jockbal2.png" alt="이미지">
                                        </span>
                                        <span>야식</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/109/${BMaddress1 }">
                                        <span>
                                            <img src="/img/jockbal3.png" alt="이미지">
                                        </span>
                                        <span>한식</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/102/${BMaddress1 }">
                                        <span>
                                            <img src="/img/hamburger4.png" alt="이미지">
                                        </span>
                                        <span>패스트푸드</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="/store/102/${BMaddress1 }">
                                        <span>
                                            <img src="/img/hamburger4.png" alt="이미지">
                                        </span>
                                        <span>패스트푸드</span>
                                    </a>
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
	<%@ include file="./include/nav.jsp" %>
    <!-- 하단 메뉴 -->

    <!-- 푸터 -->
    <%@ include file="./include/footer.jsp" %>
    <!-- 푸터 -->



</body>

</html>