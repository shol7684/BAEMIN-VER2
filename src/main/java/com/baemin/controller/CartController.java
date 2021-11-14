package com.baemin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.util.FoodPriceCalc;
import com.baemin.vo.Cart;
import com.baemin.vo.CartDetail;
import com.baemin.vo.StoreDetail;

@Controller
public class CartController {

	private static final Logger LOGGER = LogManager.getLogger(CartController.class);

//	@Autowired
//	private StoreDetail storeDetail;

	@ResponseBody
	@PostMapping("/addCart")
	public CartDetail addCart(Cart cart, int amount, int deleveryTip, long storeId, String storeName, HttpSession session) {

		List<Integer> cartId = null;
		List<Cart> cartList = null;
		List<Integer> amountList = null;
		List<Long> totalPriceList = null;
		
		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");
		System.out.println("null ?????");
		System.out.println(cartDetail == null);
		
		// 메뉴가격 + 추가옵션 총합구하기
		long totalPrice = FoodPriceCalc.foodPriceCalc(cart, amount);

		// 장바구니에 처음 담을때
		if (cartDetail == null) {

			cartId = new ArrayList<>();
			cartList = new ArrayList<>();
			amountList = new ArrayList<>();
			totalPriceList = new ArrayList<>();

			cartId.add(1);
			cartList.add(cart);
			amountList.add(amount);
			totalPriceList.add(totalPrice);
			
			// 장바구니가 비었을때 최초 한번만 입력
			cartDetail = new CartDetail(cartId, cartList, amountList, totalPriceList);
			cartDetail.setStoreId(storeId);
			cartDetail.setMenuTotalPrice(totalPrice);
			cartDetail.setStoreName(storeName);
			cartDetail.setDeleveryTip(deleveryTip);
			
			session.setAttribute("cart", cartDetail);
		
			return cartDetail;
			

		} 
//		장바구니에 1개 이상 담겨있을때	
		else {
			cartId =  cartDetail.getCartId();
			cartList = cartDetail.getCartList();
			amountList = cartDetail.getAmountList();
			totalPriceList = cartDetail.getTotalPriceList();
			
			cartDetail.setMenuTotalPrice(totalPrice + cartDetail.getMenuTotalPrice());
			
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
		cartDetail.setCartId(cartId);
		cartDetail.setCartList(cartList);
		cartDetail.setAmountList(amountList);
		cartDetail.setTotalPriceList(totalPriceList);
		
		session.setAttribute("cart", cartDetail);

		return cartDetail;

	}

	@ResponseBody
	@GetMapping("/cartList")
	public CartDetail cartList(HttpSession session) {

		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");
		if (cartDetail != null) {
			return cartDetail;
		}

		return null;
	}

	@ResponseBody
	@DeleteMapping("/cartAll")
	public void deleteAllCart(HttpSession session) {
		session.removeAttribute("cart");
	}

	@ResponseBody
	@DeleteMapping("/cartOne")
	public CartDetail deleteOneCart(HttpSession session, int index) {

		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");

		if (cartDetail == null) {
			return null;
		}

		List<Integer> cartId = cartDetail.getCartId();
		List<Cart> cartList = cartDetail.getCartList();
		List<Integer> amountList = cartDetail.getAmountList();
		List<Long> totalPriceList = cartDetail.getTotalPriceList();
		long menuTotalPrice = cartDetail.getMenuTotalPrice();

		cartId.remove(index);

		if (cartId.size() == 0) {
			session.removeAttribute("cart");
			return null;
		}
		menuTotalPrice -= totalPriceList.get(index);

		cartList.remove(index);
		amountList.remove(index);
		totalPriceList.remove(index);

		cartDetail.setCartId(cartId);
		cartDetail.setCartList(cartList);
		cartDetail.setAmountList(amountList);
		cartDetail.setTotalPriceList(totalPriceList);
		cartDetail.setMenuTotalPrice(menuTotalPrice);

		session.setAttribute("cart", cartDetail);

		return cartDetail;
	}

	@ResponseBody
	@PatchMapping("/cartAmount")
	public CartDetail cartAmount(int cartNum, String clickBtn, HttpSession session) {

		System.out.println(cartNum);
		System.out.println(clickBtn);

		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");

		List<Integer> cartId = cartDetail.getCartId();
		List<Cart> cartList = cartDetail.getCartList();
		List<Integer> amountList = cartDetail.getAmountList();
		List<Long> totalPriceList = cartDetail.getTotalPriceList();
		long menuTotalPrice = cartDetail.getMenuTotalPrice();

		int amount = amountList.get(cartNum);
		long totalPrice = totalPriceList.get(cartNum);
		long foodPrice = totalPrice / amount;

		if (clickBtn.equals("plus")) {
			amountList.set(cartNum, amount + 1);
			totalPriceList.set(cartNum, foodPrice * (amount + 1));
			menuTotalPrice += foodPrice;

		} else {

			if (amount <= 1) {
				return cartDetail;
			}

			amountList.set(cartNum, amount - 1);
			totalPriceList.set(cartNum, foodPrice * (amount - 1));
			menuTotalPrice -= foodPrice;
		}

		cartDetail.setAmountList(amountList);
		cartDetail.setTotalPriceList(totalPriceList);
		cartDetail.setMenuTotalPrice(menuTotalPrice);

		session.setAttribute("cart", cartDetail);

		return cartDetail;
	}

}
