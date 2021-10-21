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

                        <%@ include file="/WEB-INF/view/include/addressSearch.jsp" %>
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