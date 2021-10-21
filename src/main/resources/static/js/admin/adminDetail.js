

$(document).ready(function() {

	
	$(".review_btn").click(function() {
		cs($(this).siblings(".order_num").val());

		$(this).parent().parent().parent().siblings(".boss.input").stop().fadeToggle(0,function(){
		const top = $(this).offset().top;
			if($(this).css("display") == 'block') {
				$("html").animate({ scrollTop: top - 100 }, 200);
			}
		});
	})



	$(".boss_comment_btn").click(function(){
		const bossComment = $(this).parent().siblings().find(".comment_area").val();
		const orderNum = $(this).siblings(".order_num").val();
		const inputTarget = $(this).parent().parent().parent().siblings(".boss");
		
		cs("ordernum = " + orderNum);
		cs("bossComment = " + bossComment);
		
		const data = {
			bossComment : bossComment,
			orderNum : orderNum
		}
		
		$.ajax({
			url: "/admin/bossComment",
			type: "POST",
			data: data,
			success: function(result){
				
				let html = `<div class="boss_comment_box">
								<div class="nickname">사장님</div>
		                		<div class="boss_comment">${result }</div>
		                	</div>`;
				
				inputTarget.html(html);
			},
			fail: function(){
				alert("실패");
			}
			
			
			
		})
		
	})










	function cs(value) {
		console.log(value);
	}
})
