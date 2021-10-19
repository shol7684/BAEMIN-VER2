
$(document).ready(function() {


	const storeList = $(".store_list").val();

console.log(storeList);

	//가게 없으면 
	if (!storeList) {
		$(".box").css("background", "#F6F6F6").css("max-width", "100%")
	}


	const category = $(".cate").val();

	console.log(category);
	$("li[data-category = '" + category + "'] > span").css("border-bottom", "3px solid #333333");
	$("li[data-category = '" + category + "'] > span").css("color", "#333333");



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