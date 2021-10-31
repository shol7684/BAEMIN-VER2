
$(document).ready(function() {

	const category = $(".cate").val();
	const address1 = $(".address1").val();

	$("li[data-category = '" + category + "'] > span").css("border-bottom", "3px solid #333333");
	$("li[data-category = '" + category + "'] > span").css("color", "#333333");

	
	let winHeight = 0;
	let docHeight = 0;
	let page = 2;
	let run = false;

	$(window).scroll(function(){
		winHeight = $(window).height();
		docHeigth = $(document).height();
		
		const top = $(window).scrollTop();
		
		if(docHeigth <= winHeight + top + 10 ) {
			if(run) {
				return;
			}
			console.log("페이지 추가");
			
			run = true;
			
			const data = {
				category : category,
				address1 : address1,
				page : page
			}
			
			$.ajax({
				url: "/storeNextPage",
				type: "GET",
				data : data,
				success: function(storeList){
					page++;
					
					let html = "";
					for(var i=0;i<storeList.length;i++) {
						
						let scoreHtml = "";
						for(var j=0;j<5;j++) {
							if(storeList[i].score >= j) {
								scoreHtml += `<i class="fas fa-star"></i> `;
							}
							if(storeList[i].score < j) {
								scoreHtml += `<i class="far fa-star"></i> `;
							}
						}
						
						html += `<li >
		                    <div>
		                        <a href="/store/detail/${storeList[i].id }">   
		                            <img src="${storeList[i].storeImg }" alt="이미지">
		                            <div class="inf">
		                              ${storeList[i].openingTime } ${storeList[i].closingTime } ${storeList[i].score }
		                                <h2>${storeList[i].storeName }</h2>
		                                <div>
		                                	<span>평점 ${storeList[i].score }</span>
		                                	<span class="score_box">
		                                	${scoreHtml}
					               			</span>
		                                </div>
	                                    <div><span>리뷰 ${storeList[i].reviewCount }</span><span>ㅣ</span><span>사장님 댓글 ${storeList[i].bossCommentCount }</span></div>
		                                <div><span>배달시간 ${storeList[i].deleveryTime }분</span><span>최소주문금액 ${storeList[i].minDelevery.toLocaleString() }원</span></div>
		                                <div>배달팁 ${storeList[i].deleveryTip.toLocaleString()}원</div>
		                                 
		                            </div>
		                        </a>
	                        </div>
	                    </li>`;
						
					}
					
					$(".store").append(html);
					
					if(storeList != "") {
						run = false;
					}
				}
			}) // ajax
		} // if
	}) // scroll
	
	
	
	
	
	function cs(value){
		console.log(value);
	}
	
	
	


	/* ------------------------------ 가게 정렬 ------------------------------ */
	$(".option li").click(function() {
		const index = $(this).index();
		const address = $(".address").val();


		let data = {
			index: index,
			address: address,
			category: category
		}


		$.ajax({
			url: "/sortStoreList",
			type: "post",
			data: data,
			success: function(result) {

				let ht = "";

				console.log("길이 = " + result.length);
				console.log(result);
				console.log(result[0]["storeNum"]);


				for (var i = 0; i < result.length; i++) {

					const storeNum = result[i]["storeNum"];
					const storeThumb = result[i]["storeThumb"];
					const storeName = result[i]["storeName"];
					const deleveryTime = result[i]["deleveryTime"];
					const minDelevery = result[i]["minDelevery"];
					const deleveryTip = result[i]["deleveryTip"];
					const score = result[i]["score"].toFixed(1);
					const reviewCount = result[i]["reviewCount"];
					const bossComment = result[i]["bossComment"];

					let scoreHt = "<i class='fas far fa-star'></i> ";

					for (var j = 0; j < 4; j++) {

						if (Math.round(score) - 1 > j) {
							scoreHt += "<i class='fas far fa-star'></i> ";
						} else {
							scoreHt += "<i class='far fa-star'></i> ";
						}

					}
					console.log(result);
					console.log(`score = ${score}`);

					ht += `<li>
		              <div>
		                  <a href="/store/detail/${storeNum}">           
		                      <img src="${storeThumb}" alt="이미지">
		                      <div class="inf">
		                          <h2>${storeName}</h2>
		                          <div>
		                          	<span>평점 ${score}</span>
		                          	<span class="score_box">
		                          		${scoreHt}
			               			</span>
		                          </div>
		                          
		                          <div><span>리뷰 ${reviewCount}</span><span>ㅣ</span><span>사장님 댓글 ${bossComment}</span></div>
		                          <div><span>배달시간 ${deleveryTime}분</span><span>최소주문금액 ${minDelevery.toLocaleString()}원</span></div>
		                          <div>배달팁 ${deleveryTip.toLocaleString()}원</div>
		                      </div>
		                  </a>
		              </div>
		          </li>`;

				}

				$(".box ul.store").html(ht);


			} // success
		}); // ajax
	}); // function
	/* ------------------------------ 가게 정렬 ------------------------------ */




});