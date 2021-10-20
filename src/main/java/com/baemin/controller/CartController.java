package com.baemin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.util.FoodPriceCalc;
import com.baemin.vo.Cart;

@Controller
public class CartController {

	@ResponseBody
	@PostMapping("/addCart")
	public Map<String, Object> addCart(Cart cart, int storeId, int amount, int deleveryTip, String storeName, HttpSession session) {

		Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");

		List<Integer> cartId = null;
		List<Cart> cartList = null;
		List<Integer> amountList = null;
		List<Long> totalPriceList = null;
		
		// 메뉴가격 + 추가옵션 총합구하기
		long totalPrice = FoodPriceCalc.foodPriceCalc(cart, amount);

		if (cartMap == null) {

			cartMap = new HashMap<>();

			cartId = new ArrayList<>();
			cartList = new ArrayList<>();
			amountList = new ArrayList<>();
			totalPriceList = new ArrayList<>();

			cartId.add(1);
			cartList.add(cart);
			amountList.add(amount);
			totalPriceList.add(totalPrice);
			
			// 장바구니가 비었을때 최초 한번만 입력
			cartMap.put("storeId", storeId);  // 다른 가게에서 다시 장바구니에 담는경우를 구분하기 위해 추가
			cartMap.put("menuTotalPrice", totalPrice);
			cartMap.put("storeName", storeName);
			cartMap.put("deleveryTip", deleveryTip);

		} else {
			cartId =  (List<Integer>) cartMap.get("cartId");
			cartList = (List<Cart>) cartMap.get("cartList");
			amountList = (List<Integer>) cartMap.get("amountList");
			totalPriceList = (List<Long>) cartMap.get("totalPriceList");
			
			cartMap.put("menuTotalPrice", (long) cartMap.get("menuTotalPrice") + totalPrice);
			
			if (cartList.contains(cart)) {

				int index = cartList.indexOf(cart);
				amountList.set(index, (amountList.get(index) + amount));
				totalPriceList.set(index, (totalPriceList.get(index) + totalPrice));

			} else {
				cartId.add(cartId.size() + 1);
				cartList.add(cart);
				amountList.add(amount);
				totalPriceList.add(totalPrice);
			}
		}

		cartMap.put("cartId", cartId);
		cartMap.put("cartList", cartList);
		cartMap.put("amountList", amountList);
		cartMap.put("totalPriceList", totalPriceList);

		session.setAttribute("cartMap", cartMap);

		System.out.println(cartMap);

		return cartMap;

	}

	@ResponseBody
	@GetMapping("/cartList")
	public Map<String, Object> cartList(HttpSession session) {

		Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");
		System.out.println(cartMap == null);
		if (cartMap != null) {
			return cartMap;
		}

		return null;
	}
	
	@ResponseBody
	@DeleteMapping("/cartAll")
	public void deleteAllCart(HttpSession session) {
		session.removeAttribute("cartMap");
	}
	
	@ResponseBody
	@DeleteMapping("/cartOne")
	public Map<String, Object> deleteOneCart(HttpSession session, int index) {
		
		Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");
		
		if(cartMap == null) {
			return null;
		}
		
		List<Integer> cartId = (List<Integer>) cartMap.get("cartId");
		List<Cart> cartList =  (List<Cart>) cartMap.get("cartList");
		List<Integer> amountList = (List<Integer>) cartMap.get("amountList");
		List<Long> totalPriceList =(List<Long>) cartMap.get("totalPriceList");
		
		long menuTotalPrice = (long) cartMap.get("menuTotalPrice");
		
		cartId.remove(index);
		
		if(cartId.size() == 0 ) {
			session.removeAttribute("cartMap");
			return null;
		}
		menuTotalPrice -= totalPriceList.get(index);
		
		cartList.remove(index);
		amountList.remove(index);
		totalPriceList.remove(index);
		
		
		cartMap.put("cartId", cartId);
		cartMap.put("cartList", cartList);
		cartMap.put("amountList", amountList);
		cartMap.put("totalPriceList", totalPriceList);
		cartMap.put("menuTotalPrice", menuTotalPrice);  
		
		session.setAttribute("cartMap", cartMap);
		
		return cartMap;
	}
	
	
	@ResponseBody
	@PatchMapping("/cartAmount")
	public Map<String, Object> cartAmount(int cartNum, String clickBtn, HttpSession session) {
		
		System.out.println(cartNum);
		System.out.println(clickBtn);
		
		Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");
		
		List<Integer> cartId = (List<Integer>) cartMap.get("cartId");
		List<Cart> cartList =  (List<Cart>) cartMap.get("cartList");
		List<Integer> amountList = (List<Integer>) cartMap.get("amountList");
		List<Long> totalPriceList =(List<Long>) cartMap.get("totalPriceList");
		
		long menuTotalPrice =  (long) cartMap.get("menuTotalPrice");
		int amount = amountList.get(cartNum);
		long totalPrice = totalPriceList.get(cartNum);
		long foodPrice = totalPrice / amount;
		
		if(clickBtn.equals("plus")) {
			amountList.set(cartNum, amount + 1);
			totalPriceList.set(cartNum, foodPrice * (amount + 1));
			menuTotalPrice += foodPrice; 
			
		} else {
			
			if(amount <= 1) {
				return cartMap;
			}
			
			amountList.set(cartNum, amount - 1);
			totalPriceList.set(cartNum, foodPrice * (amount - 1));
			menuTotalPrice -= foodPrice; 
		}
		
			cartMap.put("amountList", amountList);
			cartMap.put("totalPriceList", totalPriceList);
			cartMap.put("menuTotalPrice", menuTotalPrice);
		
			session.setAttribute("cartMap", cartMap);
		
		
		return cartMap;
	}
	
	
	
	
	

}
