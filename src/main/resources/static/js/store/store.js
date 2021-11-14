
$(document).ready(function() {
	const category = $(".category").data("category");
	const address1 = $(".address1").val();
	let sort = "기본순";
	$(".option li[data-sort='기본순']").addClass("active");

	$("li[data-category = '" + category + "'] > span").css("border-bottom", "3px solid #333333");
	$("li[data-category = '" + category + "'] > span").css("color", "#333333");

	
	let winHeight = 0;
	let docHeight = 0;
	let page = 2;
	let run = false;

	$(window).scroll(function(){
		winHeight = $(window).height();
		docHeight = $(document).height();
		
		const top = $(window).scrollTop();
		
		if(docHeight <= winHeight + top + 10 ) {
			if(run) {
				return;
			}
			console.log("페이지 추가");
			console.log("sort= " + sort);
			
			run = true;
			
			const data = {
				page : page,
				category : category,
				address1 : address1
			}
			
			$.ajax({
				url: "/storeNextPage",
				type: "GET",
				data : data
			})
			.done(function(result){
				const storeHtml = storeList(result);
				page++;
				
				$(".store").append(storeHtml);
				
				if(storeHtml != "") {
					run = false;
				}
			})
			.fail(function(data, textStatus, errorThrown){
				swal("다시 시도해주세요");
			})	
			
		} // if
	}) // scroll
	
	

// 가게 정렬 
$(".option li").click(function() {
	
	sort = $(this).data("sort");
	/*nowOption();*/
	$(".option li").removeClass("active");
	$(this).addClass("active");
	
	const data = {
		sort : sort,
		category : category,
		address1 : address1
	}
	$.ajax({
		url: "/store/sortStore",
		type: "get",
		data: data
	})
	.done(function(result, textStatus, xhr){
		// 페이지 초기화
		run = false;
		page = 2;
		
		const storeHtml = storeList(result);
		$(".box ul.store").html(storeHtml);
		
	})
	.fail(function(data, textStatus, errorThrown){
		swal("다시 시도해주세요");
	})
}); // function



function nowOption(){
	$(".option li").removeClass("active");
	$(".option li[data-sort='" + sort +"']").addClass("active");
}



function storeList(result){
	let html = "";
		for(var i=0;i<result.length;i++) {
			const id = result[i]["id"];
			
			const storeImg = result[i]["storeImg"];
			const storeThumb = result[i]["storeThumb"];
			const storeName = result[i]["storeName"];
			const deleveryTime = result[i]["deleveryTime"];
			const minDelevery = result[i]["minDelevery"].toLocaleString();
			const deleveryTip = result[i]["deleveryTip"].toLocaleString();
			const score = result[i]["score"].toFixed(1);
			const reviewCount = result[i]["reviewCount"];
			const bossCommentCount = result[i]["bossCommentCount"];
			const openingTime = result[i]["openingTime"];
			const closingTime = result[i]["closingTime"];
			
			let scoreHtml = "";
			for(var j=0;j<5;j++) {
				if(Math.round(score)  > j) {
					scoreHtml += "<i class='fas fa-star'></i> ";
				} else {
					scoreHtml += "<i class='far fa-star'></i> ";
				}
			}
			let isOpenHtml = "";
			if(result[i]["isOpen"] == "false") {
				isOpenHtml = `<div class="is_open">
								<a href="/store/detail/${storeList.id }">지금은 준비중입니다</a>
							</div>`;
			}
			
			
			html += `<li >
						<div class="img_box">
							<a href=/store/detail/${id }"><img src="${storeImg }" alt="이미지"></a>
						</div>
				
						<div class="info_box">
						
							<h2><a href="/store/detail/${id }">${storeName }</a></h2>
							
							<a>
							<span>
								<span>평점 ${score }</span>
								
								<span class="score_box">
									${scoreHtml}
								</span>
							</span>
								
							<span>
								<span>리뷰 ${reviewCount }</span>
								<span>사장님 댓글 ${bossCommentCount }</span>
							</span>
							
							<span>
								<span>최소주문금액 ${minDelevery }원</span>
								<span>배달팁 ${deleveryTip }원</span>
							</span>
							<span>배달시간 ${deleveryTime }분</span>
							</a>
						</div>
                	${isOpenHtml}
                </li>`;
		}
	return html;	
}



});