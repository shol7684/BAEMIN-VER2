<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<div id="modal_bg"></div>

<div class="store_reg_modal modal" >
	<div id="modal_header">
	 	<button class="closeA"><i class="fas fa-times"></i></button>
		<h1>매장 등록</h1>
	 </div>

    <form action="/admin/storeRegist" method="post" enctype="multipart/form-data"> 

    
		<div class="modal_box">
	         <ul>
	            <li>
	                <label for="category">
	                    <h2>카테고리</h2>
	                </label>	
	
	                <div>
	                    <select id="category" name="category">
	                        <option value="100">피자</option>
	                        <option value="101">치킨</option>
	                        <option value="102">패스트푸드</option>
	                        <option value="103">분식</option>
	                        <option value="104">카페/디저트</option>
	                        <option value="105">돈까스/일식</option>
	                        <option value="106">중국집</option>
	                        <option value="107">족발/보쌈</option>
	                        <option value="108">야식</option>
	                        <option value="109">한식</option>
	                    </select>
	                </div>
	            </li>
	
	            <li>
	                <label for="storeName" >
	                    <h2>매장 이름</h2>
	                </label>
	                <div><input type="text" id="storeName" name="storeName" value="" required spellcheck="false" ></div>
	
	            <li class="location">
	                <label for="location">
	                    <h2>매장 위치</h2>
	                </label>
					
	                    <div class="sample">
	                        <input type="button" onclick="sample2_execDaumPostcode()" value="우편번호 찾기" id="sample2_btn" ><br>
	                        <input type="text" onclick="sample2_execDaumPostcode()" id="sample2_postcode" placeholder="우편번호" readonly name="storeAddress1" required>
	
	                        <input type="text" onclick="sample2_execDaumPostcode()" id="sample2_address" placeholder="주소" readonly name="storeAddress2" required><br>
	                        <input type="text" id="sample2_detailAddress" placeholder="상세주소" name="storeAddress3">
	                        <input type="hidden" id="sample2_extraAddress" placeholder="참고항목" readonly>
	
	                       <%@ include file="/WEB-INF/view/include/addressSearch.jsp" %>
	                    </div>
	
	            </li>
	
	            <li>
	                <label for="storePhoneNumber">
	                    <h2>매장 전화번호</h2>
	                </label>
	                <div><input type="number" onkeypress="return lenthCheck(this, 11);" id="storePhoneNumber" name="storePhone" required></div>
	            </li>
	
	            <li class="business_hour">
                    <h2>영업시간</h2>
	                <div>
	                     <span>
	                        <select name="openingTime" id="opening_time" required>
	                            <c:forEach begin="0" end="24" var="i">
	                            	<option value="${i }">${i }</option>
	                            </c:forEach>
	                        </select>
	                    </span>
	                    
	                    <span> ~ </span>
	                    
	                    <span>
	                        <select name="closingTime" id="closing_time" required>
                                <c:forEach begin="0" end="24" var="i">
	                            	<option value="${i }">${i }</option>
	                            </c:forEach>
	                        </select>
	                    </span>
	                </div>
	            </li>
	
	            <li>
	                <label for="minDeleveryPrice">
	                    <h2>최소 배달금액</h2>
	                </label>
	                <div><input type="number" onkeypress="return lenthCheck(this, 8);" id="minDeleveryPrice" name="minDelevery" required>원</div>
	            </li>
	            
	            <li>
	                <label for="minDeleveryPrice">
	                    <h2>배달팁</h2>
	                </label>
	                <div><input type="number" onkeypress="return lenthCheck(this, 8);" id="deleveryTip" name="deleveryTip" required >원</div>
	            </li>
	            
	            <li>
	                <label for="deleveryTime">
	                    <h2>예상 배달시간</h2>
	                </label>
	                <div><input type="number"  onkeypress="return lenthCheck(this, 3);"  pattern="/d*" value="" id="deleveryTime" name="deleveryTime" required>분</div>
	            </li>
	            
	             <li>
	                <label for="store_des">
	                    <h2>가게 정보</h2>
	                </label>
	                <div class="store_des"><textarea id="store_des" name="storeDes" maxlength="500" ></textarea></div>
	                
	            </li> 
	
	            <li>
                    <h2>매장 이미지 첨부</h2>
                    
                    <div id="img_area">
                   	 	<label for="img" /></label>
			    		<input type="file" id="img" name="file" >
			    		
		                <div class="img_box">
			    			<img class="preview">
			    			<button type="button" class="img_close"> x</button>
				    	</div>
                    </div>
	                
	            </li>
	        </ul>
		</div>
		
		<div id="btn_box">
       		<button class=closeB type="button">취소</button>
       		<button class="regist_btn" type="submit">등록</button>
        </div>
        
		
	</form>
</div>
