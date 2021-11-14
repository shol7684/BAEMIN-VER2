<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	
.page_box {
	display: flex;
	justify-content: center;
	margin-bottom: 20px;
}

.page_box li {
	border: 1px solid #999;
	border-right: none;
	width: 35px;
	height: 35px;
	text-align: center;
	line-height: 35px;
}

.page_box li:last-child {
	border-right: 1px solid #999;
}

.page_box li a {
	display: block;
	width: 100%;
	height: 100%;
}
.now_page {
	background: #30DAD9;
	color: #fff;
	cursor: default;
}
.now_page:hover {
	color: #fff;
}

@media(max-width :767px) {
	.page_box {
		margin-top: 20px;
	}
	.page_box li {
		width: 25px;
		height: 25px;
		line-height: 25px;
		font-size: 12px;
	}
}

	
	


	
</style>
    
 <ul class="page_box">
       	<c:if test="${page.nowPage > page.pageCount}">
       		<li><a href="${movePage }${page.prevPage }${query }">이전</a></li>
       	</c:if> 
       	
       	
       	<c:forEach begin="${page.pageNum }" end="${Math.max(0, page.pageNum + page.pageCount-1) }" var="i">
       	
       		<c:if test="${i <= lastPage}">
       			
       			<c:if test="${i != page.nowPage }">
        			<li><a href="${movePage }${i }${query } ">${i }</a></li>
       			</c:if>
       			<c:if test="${i == page.nowPage }">
       				<li><a class="now_page" onclick="return false;" href="${movePage }${i }${query }">${i }</a></li>
       			</c:if>
       			
       		</c:if>
       	</c:forEach>
       	
      		<c:if test="${page.pageNum + page.pageCount < lastPage }">
        	<li><a href="${movePage }${page.nextPage }${query }">다음</a></li>
      		</c:if>
      </ul>
      
      
      