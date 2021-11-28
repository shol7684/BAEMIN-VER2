package com.baemin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.util.FoodPriceCalc;
import com.baemin.vo.Cart;
import com.baemin.vo.CartList;

@Controller
public class CartController {

	private static final Logger LOGGER = LogManager.getLogger(CartController.class);

//	@Autowired
//	private StoreDetail storeDetail;

//	public static List<Cart> list = new ArrayList<>();
	
	@ResponseBody
	@PostMapping("/addCart")
	public CartList addCart(Cart cart, long storeId, String storeName, int deleveryTip, HttpSession session) {

		CartList cartList = (CartList) session.getAttribute("cartList");
		
		int totalPrice = FoodPriceCalc.foodPriceCalc(cart);
		
		System.out.println("카트 가격 계산 = " + totalPrice);
		
		if(cartList == null) {
			List<Cart> newCart = new ArrayList<>();
			cart.setTotalPrice(totalPrice);
			newCart.add(cart);
			cartList = new CartList(storeId, storeName, totalPrice, deleveryTip, newCart);
		} else {
			List<Cart> prevCart = cartList.getCart();
			int prevCartTotal = cartList.getCartTotal();
			cartList.setCartTotal(prevCartTotal + totalPrice);
		
			if(prevCart.contains(cart)) {
				int cartIndex = prevCart.indexOf(cart);
				int amount = cart.getAmount();
				
				Cart newCart = prevCart.get(cartIndex);
				int newAmount = newCart.getAmount() + amount;
				int newTotal = newCart.getTotalPrice() + totalPrice;
				
				newCart.setAmount(newAmount);
				newCart.setTotalPrice(newTotal);
				prevCart.set(cartIndex, newCart);
			} else {
				cart.setTotalPrice(totalPrice);
				prevCart.add(cart);
			}
		}
		
		session.setAttribute("cartList", cartList);
		
		
//		List<Integer> cartId = null;
//		List<Cart> cartList = null;
//		List<Integer> amountList = null;
//		List<Long> totalPriceList = null;
//		
//		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");
//		
//		// 메뉴가격 + 추가옵션 총합구하기
//		long totalPrice = FoodPriceCalc.foodPriceCalc(cart, amount);
//
//		// 장바구니에 처음 담을때
//		if (cartDetail == null) {
//
//			cartId = new ArrayList<>();
//			cartList = new ArrayList<>();
//			amountList = new ArrayList<>();
//			totalPriceList = new ArrayList<>();
//
//			cartId.add(1);
//			cartList.add(cart);
//			amountList.add(amount);
//			totalPriceList.add(totalPrice);
//			
//			// 장바구니가 비었을때 최초 한번만 입력
//			cartDetail = new CartDetail(cartId, cartList, amountList, totalPriceList);
//			cartDetail.setStoreId(storeId);
//			cartDetail.setMenuTotalPrice(totalPrice);
//			cartDetail.setStoreName(storeName);
//			cartDetail.setDeleveryTip(deleveryTip);
//			
//			session.setAttribute("cart", cartDetail);
//		
//			return cartDetail;
//			
//
//		} 
////		장바구니에 1개 이상 담겨있을때	
//		else {
//			cartId =  cartDetail.getCartId();
//			cartList = cartDetail.getCartList();
//			amountList = cartDetail.getAmountList();
//			totalPriceList = cartDetail.getTotalPriceList();
//			
//			cartDetail.setMenuTotalPrice(totalPrice + cartDetail.getMenuTotalPrice());
//			
//			if (cartList.contains(cart)) {
//
//				int index = cartList.indexOf(cart);
//				amountList.set(index, (amountList.get(index) + amount));
//				totalPriceList.set(index, (totalPriceList.get(index) + totalPrice));
//
//			} else {
//				cartId.add(cartId.size() + 1);
//				cartList.add(cart);
//				amountList.add(amount);
//				totalPriceList.add(totalPrice);
//			}
//		}
//		cartDetail.setCartId(cartId);
//		cartDetail.setCartList(cartList);
//		cartDetail.setAmountList(amountList);
//		cartDetail.setTotalPriceList(totalPriceList);
//		
//		session.setAttribute("cart", cartDetail);

		return cartList;

	}

	@ResponseBody
	@GetMapping("/cartList")
	public CartList cartList(HttpSession session) {
		CartList cartList = (CartList) session.getAttribute("cartList");
		if (cartList != null) {
			return cartList;
		}
		return null;
	}

	@ResponseBody
	@DeleteMapping("/cartAll")
	public void deleteAllCart(HttpSession session) {
		session.removeAttribute("cartList");
	}

	@ResponseBody
	@DeleteMapping("/cartOne")
	public CartList deleteOneCart(HttpSession session, int index) {

		CartList cartList = (CartList) session.getAttribute("cartList");

		if (cartList == null) {
			return null;
		}
		int cartTotal = cartList.getCartTotal();
		List<Cart> cart = cartList.getCart();
		int removeCartPrice = cart.get(index).getTotalPrice();
		
		cart.remove(index);
		
		if(cart.size() == 0) {
			session.removeAttribute("cartList");
			return null;
		}
		
		cartTotal -=  removeCartPrice;
		cartList.setCartTotal(cartTotal);
		
		session.setAttribute("cartList", cartList);

//		List<Integer> cartId = cartDetail.getCartId();
//		List<Cart> cartList = cartDetail.getCartList();
//		List<Integer> amountList = cartDetail.getAmountList();
//		List<Long> totalPriceList = cartDetail.getTotalPriceList();
//		long menuTotalPrice = cartDetail.getMenuTotalPrice();
//
//		cartId.remove(index);
//
//		if (cartId.size() == 0) {
//			session.removeAttribute("cart");
//			return null;
//		}
//		menuTotalPrice -= totalPriceList.get(index);
//
//		cartList.remove(index);
//		amountList.remove(index);
//		totalPriceList.remove(index);
//
//		cartDetail.setCartId(cartId);
//		cartDetail.setCartList(cartList);
//		cartDetail.setAmountList(amountList);
//		cartDetail.setTotalPriceList(totalPriceList);
//		cartDetail.setMenuTotalPrice(menuTotalPrice);
//
//		session.setAttribute("cart", cartDetail);

		return cartList;
	}

	@ResponseBody
	@PatchMapping("/cartAmount")
	public CartList cartAmount(int cartNum, String clickBtn, HttpSession session) {

		System.out.println(cartNum);
		System.out.println(clickBtn);

		CartList cartList = (CartList) session.getAttribute("cartList");
//
//		List<Integer> cartId = cartDetail.getCartId();
//		List<Cart> cartList = cartDetail.getCartList();
//		List<Integer> amountList = cartDetail.getAmountList();
//		List<Long> totalPriceList = cartDetail.getTotalPriceList();
//		long menuTotalPrice = cartDetail.getMenuTotalPrice();
//
//		int amount = amountList.get(cartNum);
//		long totalPrice = totalPriceList.get(cartNum);
//		long foodPrice = totalPrice / amount;
//
//		if (clickBtn.equals("plus")) {
//			amountList.set(cartNum, amount + 1);
//			totalPriceList.set(cartNum, foodPrice * (amount + 1));
//			menuTotalPrice += foodPrice;
//
//		} else {
//
//			if (amount <= 1) {
//				return cartDetail;
//			}
//
//			amountList.set(cartNum, amount - 1);
//			totalPriceList.set(cartNum, foodPrice * (amount - 1));
//			menuTotalPrice -= foodPrice;
//		}
//
//		cartDetail.setAmountList(amountList);
//		cartDetail.setTotalPriceList(totalPriceList);
//		cartDetail.setMenuTotalPrice(menuTotalPrice);
//
//		session.setAttribute("cart", cartDetail);

		return cartList;
	}

}
