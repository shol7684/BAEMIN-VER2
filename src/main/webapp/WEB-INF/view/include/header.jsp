<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

</head>
<body>


    <header>
        <div id="header">
            <a href="/"><img src="/img/baemin.jpg" alt="이미지"> </a>
            <button class="clear"
                style="position: absolute; top:10px; background: #fff; border: 1px solid #ddd; padding: 5px; border-radius: 10px;">
                장바구니 세션 초기화</button>

            <!-- 임시 -->
            <div class="admin" style="font-size: 13px;
                             position: absolute; right: 10px; top : 10px;
                             font-weight: bold ; ">
                <div
                    style="border: 1px solid #ddd; border-radius: 10px; padding: 5px; background: #fff; font-size: 13px;">
                    <a href="/admin/home">임시 사장님 페이지</a>
                </div>
                <c:if test="${!empty SPRING_SECURITY_CONTEXT }">
                    <span>
                    </span>
                </c:if>
                
                
            </div>
            <!-- 임시 -->

        </div>
        <script type="text/javascript">
            $(".clear").click(function () {
                $.ajax({
                    url: "/clear",
                    type: "post",
                    async: false,
                    success: function (result) {

                        alert("장바구니 세션 초기화 성공");

                    } // success
                }); // ajax
            })

        </script>
    </header>
    <!-- 헤더 -->


<script>
    console.log(location.href);
    console.log(location.host);
    console.log(location.pathname); // baemin/home
    console.log(location.hostname);
</script>