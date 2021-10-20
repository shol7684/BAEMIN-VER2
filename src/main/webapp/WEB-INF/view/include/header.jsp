<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

</head>
<body  onselectstart='return false' >


    <header>
        <div id="header">
            <a href="/"><img src="/img/baemin.jpg" alt="이미지"> </a>

            <!-- 임시 -->
            <div class="admin" style="font-size: 13px;
                             position: absolute; right: 10px; top : 10px;
                             font-weight: bold ; ">
                <div
                    style="border: 1px solid #ddd; border-radius: 10px; padding: 5px; background: #fff; font-size: 13px;">
                    <a href="/admin/home">임시 사장님 페이지</a>
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