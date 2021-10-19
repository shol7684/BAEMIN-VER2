
$(document).ready(function() {

	console.log(window.location.pathname.indexOf("admin"));
	// 관리자페이지에서만 보이기
	if (window.location.pathname.indexOf("admin") == 1) {
		$("#admin_button_box").css("display", "block");
	}


	let cartStoreNum = null; // 카트에 담긴 메뉴의 가게번호, 서로 다른가게에서 담으면 안됨        

	let size = $(window).width();

	$(window).resize(function() {
		size = $(window).width();
		console.log(size);
	})

	// -------------------- 주문하기 버튼 클릭 -----------------------
	$(".order_btn").click(function() {
		location.href = "/order";
	});
	$(".cart_img_box").click(function() {
		location.href = "/order";
	});

	// -------------------- 주문하기 버튼 클릭 -----------------------


	// -------------------- 가게 별점 -------------------- 
	let score = Math.round($("#score").val());

	if (score <= 0) {
		score = 0;
	}

	$(".score_box i").eq(score).addClass("fas").prevAll().addClass("fas");

	// -------------------- 가게 별점 -------------------- 


	// -------------------- 리뷰탭 그래프 -------------------- 

	const reviewCount = $("#review_count").val();

	const fiveScore = $("#five_score").val() / reviewCount * 100 + "%";
	const fourScore = $("#four_score").val() / reviewCount * 100 + "%";
	const threeScore = $("#three_score").val() / reviewCount * 100 + "%";
	const twoScore = $("#two_score").val() / reviewCount * 100 + "%";
	const oneScore = $("#one_score").val() / reviewCount * 100 + "%";

	$(".graph.five").css("background", "gold").css("width", fiveScore);
	$(".graph.four").css("background", "gold").css("width", fourScore);
	$(".graph.three").css("background", "gold").css("width", threeScore);
	$(".graph.two").css("background", "gold").css("width", twoScore);
	$(".graph.one").css("background", "gold").css("width", oneScore);


	// -------------------- 리뷰탭 그래프 -------------------- 



	// -------------------- 찜 하기 -------------------- 
	const storeNum = $(".store_num").val();
	const userId = $(".user_id").val();

	let dibsCheck = $(".dibs_check").val(); // 찜 여부

	$(".fa-heart").click(function() {

		if (dibsCheck == 0) { // 찜 안되있을때
			$(this).removeClass("far");
			$(this).addClass("fas");

			dibsCheck = 1;

			if (userId != "guest") {
				const count = Number($(".count").html());
				$(".count").html(count + 1);
			}


		} else {	// 찜 되있을대

			$(this).removeClass("fas");
			$(this).addClass("far");

			dibsCheck = 0;

			if (userId != "guest") {
				const count = Number($(".count").html());
				$(".count").html(count - 1);
			}

		}

		dibs();


	})


	function dibs() {
		let data = {
			storeNum: storeNum
		};
		$.ajax({
			url: "/dibs",
			type: "post",
			async: false,
			data: data,
			success: function(result) {

			} // success
		}); // ajax
	};
	// -------------------- 찜 하기 --------------------




	// ------------------ 카트 리스트 ----------------------

	cartList("/cartList", "");

	//  ----------------- 카트 리스트 ----------------------




	// ------------------메뉴 리스트 클릭----------------------

	const deleveryTip = $(".delevery_tip").val();
	const storeName = $(".store_name").val();

	let menuNum;
	let menuName;
	let menuDec;
	let menuPrice = 0; // 1개 가격
	let sum = 0; // 메뉴 1개 총 가격
	// let total =0 ; // 장바구니 총 가격

	const addCartModal = $(".addCart_modal");
	const menuList = $(".menu > li .in"); //메뉴 리스트

	menuList.click(function() { // 메뉴 모달창 열기

		console.log("cartStoreNum =" + cartStoreNum);

		openModal(addCartModal, size);

		menuNum = $(this).find("#menu_num").val();
		menuName = $(this).find("#menu_name").val();
		menuPrice = Number($(this).find("#menu_price").val());
		sum = menuPrice;
		menuDec = $(this).find("#menu_dec").val();


		let data = {
			storeNum: storeNum,
			menuNum: menuNum
		};

		$.ajax({
			url: "/menuDetail",
			type: "get",
			async: false,
			data: data,
			success: function(result) {

				/*$(".menu_name").html(menuName);

				$(".menu_dec").html(menuDec);

				$(".menu_img").attr("src", result["menuDetail"]["menuImg"]);

				$(".menu_price").html(menuPrice.toLocaleString() + "원");

				$(".menu_sum").html(menuPrice.toLocaleString() + "원");

				let ht = "";

				$("#option ul").html(ht);

				if (result["menuOption"] != null && result["menuOption"] != "") {
					console.log("옵션");

					$("#option").css("display", "block");

					for (var i = 0; i < result["menuOption"].length; i++) {

						ht += `<li>           
			                 		<div class="option_box">
			                			<span>
		                	 			<input type="checkbox" class="menu_option" name="option" value="${result["menuOption"][i]["optionName"]}"> ${result["menuOption"][i]["optionName"]}
			                				<input type="hidden" class="option_price" value="${result["menuOption"][i]["optionPrice"]}">
			    	            				<input type="hidden" class="option_num" value="${result["menuOption"][i]["optionNum"]}">
			                 	 		</span>
			                			<span> ${result["menuOption"][i]["optionPrice"].toLocaleString()}원</span>
		                			</div>
		              			</li>`;
					}

					$("#option ul").html(ht);

				} else {
					$("#option").css("display", "none");
				}*/

			}
		}); // ajax
	}); // 메뉴 클릭 메서드



	/* ---------------------- 옵션 선택 --------------------- */
	let optionPrice = 0;

	$(document).on("change", "input[name='option']", function() {


		if ($(this).is(":checked")) {
			optionPrice += Number($(this).siblings(".option_price").val());

		} else {
			optionPrice -= Number($(this).siblings(".option_price").val());
		}

		sum = (menuPrice + optionPrice) * amount;

		$(".menu_sum").html(sum.toLocaleString() + "원");

	});

	/* ---------------------- 옵션 선택 --------------------- */



	/* ---------------------수량 증가 감소--------------------- */
	const amountBox = $("#amount");

	let amount = 1;

	$(".amount_box button").click(function() {

		if ($(this).hasClass("minus")) {

			amountBox.val() == 1 ? amountBox.val(amountBox.val()) : amountBox.val(Number(amountBox.val()) - 1);

		} else if ($(this).hasClass("plus")) {

			amountBox.val(Number(amountBox.val()) + 1);
		}

		amount = amountBox.val(); // 현재 출력중인 숫자

		sum = (menuPrice + optionPrice) * amount;

		$(".menu_sum").html(sum.toLocaleString() + "원");
	})




	/* ----------------------수량 증가 감소--------------------- */


	/* ---------------------- 장바구니에 담기 --------------------- */

	var cartCount = 0; // 모바일 카트

	$(".add_cart").click(function() {

		if ((cartStoreNum == null) || (storeNum == cartStoreNum)) {
			addCart();
			return;
		}

		if (cartStoreNum != null && storeNum != cartStoreNum) {

			swal({
				buttons: ["취소", "담기"],
				title: "장바구니에는 같은 가게의 메뉴만 담을 수 있습니다",
				text: "선택하신 메뉴를 장바구니에 담을 경우 이전에 담은 메뉴가 삭제됩니다"
			})
				.then((value) => {
					if (value == true) {

						deleteCartAll();
						addCart();
					}
				});

		}



	}); // 장바구니에 담기 버튼 클릭

	/* ---------------------- 장바구니에 담기 --------------------- */



	/* ---------------------- 장바구니 1개 삭제 --------------------- */

	$(document).on("click", ".cancle_btn", function() { // .html로 생성된 부분은 on 메서드를 사용해야된다
		const index = $(this).parent().index();

		console.log("index = " + index);

		let data = {
			index: index
		};

		cartList("/deleteCartOne", data);




	}); // 삭제 버튼 클릭

	/* ---------------------- 장바구니 1개 삭제 --------------------- */



	/* ---------------------- 장바구니 전체 삭제 --------------------- */

	$(".deleteAll").click(function() {

		deleteCartAll();

	}); // 전체 삭제
	/* ---------------------- 장바구니 전체 삭제 --------------------- */




	function cartEmpryCheck(total) {

		const minDelevery = Number($("#min_delevery").val());


		if ($(".cart ul ").html() != "") {


			if (total < minDelevery) {

				$(".order_btn").attr("disabled", true);
				$(".order_btn").css("background", "#ddd");
				$(".order_btn").html(`${minDelevery.toLocaleString()}원 이상 주문할 수 있습니다`);

			} else {

				$(".order_btn").attr("disabled", false); // 상품을 담으면 주문하기버튼 활성화
				$(".order_btn").css("background", "#30DAD9");

			}


		} else {

			$(".order_btn").attr("disabled", true);
			$(".order_btn").css("background", "#ddd");
			$(".order_btn").html("주문하기");

			cartStoreNum = null;
		}


	};



	let total = $(".total").data("total");

	function cartList(url, data) {
		
		$.ajax({
			url: url,
			type: "post",
			data: data,
			async: false,
			traditional: true,
			success: function(result) {


				let ht = "";
				let total = 0;

				if (result.length == 0) {
					$(".total").html("장바구니가 비었습니다.");
					$(".cart ul").html(ht); // 장바구니 목록 초기화
					cartEmpryCheck(total);
					return;
				}



				for (var i = 0; i < result.length; i++) {

					let optionHt = "";
					let optionTotal = 0;


					if (result[i]["menuOption"] != null && result[i]["menuOption"] != "") {

						for (var j = 0; j < result[i]["menuOption"].length; j++) {

							let menuOption = result[i]["menuOption"][j];
							let menuOptionPrice = Number(result[i]["menuOptionPrice"][j]);

							optionHt += "<div class='cart_menu_option'>" + menuOption + " " + menuOptionPrice.toLocaleString() + "원</div>";

							optionTotal += menuOptionPrice;

						}
					}



					ht += `<li> 
							<h3>${result[i]["menuName"]}</h3>
   							<div>${result[i]["menuPrice"].toLocaleString()}원</div>
   							<div>수량 : ${result[i]["amount"]}</div>
   							<div> ${optionHt} </div>
   							<div>합계 : ${((result[i]["menuPrice"] + optionTotal) * result[i]["amount"]).toLocaleString()}원</div>
   							<button class="cancle_btn"> ${"ｘ"} </button>
   							</li>`;  // 장바구니 추가하면 장바구니 리스트 변경

					total += (result[i]["menuPrice"] + optionTotal) * result[i]["amount"];
				}


				$(".cart ul").html(ht);

				if (!result) {
					$(".total").html("장바구니가 비었습니다.");
				} else {
					$(".total").html("총 합계 : " + total.toLocaleString() + "원");
				}


				cartEmpryCheck(total);

				/* 모바일 카트 */
				if (result.length > 0) {
					$(".cart_count").html(result.length);
					$(".cart_count").css("display", "block");

					cartCount = result.length;
				}
				/* 모바일 카트 */



				$(".total").data("total", total); // 전역변수에 세팅
				cartStoreNum = result[0]["storeNum"];





			} // success
		}); // ajax


	};


	function addCart() {
		const menuOption = new Array();
		const menuOptionPrice = new Array();

		$("input[type='checkBox']:checked").each(function() {
			menuOption.push($(this).val());
			menuOptionPrice.push($(this).siblings(".option_price").val());
		})
		console.log("StoreName  = " + storeName);

		console.log("menuOption= " + menuOption);
		console.log("menuOptionPrice= " + menuOptionPrice);

		let data = {
			menuName: menuName,
			storeNum: storeNum,
			storeName: storeName,
			menuNum: menuNum,
			menuPrice: menuPrice,
			amount: amount,
			deleveryTip: deleveryTip,
			menuOption: menuOption,
			menuOptionPrice: menuOptionPrice
		};

		cartList("/addCart", data);

		swal("장바구니에 추가되었습니다.", "", "success", {
			timer: 800,
			buttons: "확인"
		});

		console.log("카트 상품 수 = " + cartCount);
		$("#amount").val(1);

		cartCount += 1; // 모바일 카트
		amount = 1;
		optionPrice = 0;

		closeModal();
	}


	function deleteCartAll() {
		$.ajax({
			url: "/deleteCartAll",
			type: "post",
			async: false,
			success: function() {

				$(".cart ul  ").html("");

				cartEmpryCheck(total);

				$(".total").html("장바구니가 비었습니다.");



			}
		}); // ajax
	}






	const modal = $(".modal");
	const modalBg = $(".modal_bg");
	const closeA = $(".closeA");
	const closeB = $(".closeB");

	const modal2 = $(".modal2"); // 추가
	const cancle = $(".cancle");

	closeB.click(function() {
		closeModal();
	});

	modalBg.click(function() {
		closeModal();

	});

	closeA.click(function() {
		closeModal();
	});

	$(".addCart").click(function() {
		closeModal();
	}); // 장바구니에 담기 버튼 클릭

	$("#accept").click(function() {
		closeModal();
	});

	cancle.click(function() {
		closeModal();

	})

	function closeModal() {
		modal.scrollTop(0);
		modal2.scrollTop(0);
		modalBg.hide();
		modal.css("top", "100%");

		$(".modal_box").scrollTop(0);

		$("body").css("overflow", "visible");
		$("#amount").val(1);
		optionPrice = 0;

		/* $("input[type='checkBox']").prop("checked", false); */

		$(".plusOption").remove();
	};






	//탭 눌렀을때 색변경 콘텐츠 변경

	/*$("main  ul").eq(2).hide();
	$("main  ul").eq(3).hide();*/

	const tab = $("ul.tab > li");
	const menu = $(".menu > li");

	tab.click(function() {

		const index = $(this).index() + 1;

		tab.removeClass("select");
		$(this).addClass("select");

		$("main  ul").eq(1).hide();
		$("main  ul").eq(2).hide();
		$("main  ul").eq(3).hide();
		$("main  ul").eq(index).show();

		const offset = $(".offset").offset();
		const scrollPosition = $(document).scrollTop();

		console.log(offset);
		console.log(offset["top"]);

		if (offset["top"] < scrollPosition) {
			$("html").animate({ scrollTop: offset.top }, 100);
		}

	});

	//탭 눌렀을때 색변경 콘텐츠 변경
	/* ---------------------------- 이미지 확대 ---------------------------- */
	let zoom = false;
	$(".review_img").click(function() {

		if (zoom == false) {
			$(this).css("transition", "0.3s").css("width", "100%");
			zoom = true;
		} else {
			$(this).css("transition", "0.3s").css("width", "30%");
			zoom = false;
		}
	});
	/* ---------------------------- 이미지 확대 ---------------------------- */









});