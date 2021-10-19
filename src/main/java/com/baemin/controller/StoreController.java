package com.baemin.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.service.StoreService;
import com.baemin.vo.Store;

@Controller
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	private static final Logger LOGGER = LogManager.getLogger(StoreController.class);
	
	@GetMapping("store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {
		
		System.out.println(category);
		System.out.println(address1);

		List<Store> storeList =  storeService.storeList(category, address1 / 100);
		
		model.addAttribute("storeList" , storeList);
		
		return "store/store";
	}
	
	
	@GetMapping("/store/detail/{id}")
	public String storeDetail(@PathVariable int id, Model model) {
		
		System.out.println(id);
		
		Map<String, Object> store = storeService.storeDetail(id);
		
		System.out.println(store);
		
		model.addAttribute("store" , store);
		
		return "store/detail";
	}
	
	
	@ResponseBody
	@GetMapping("/foodOption")
	public List menuDetail(int foodId) {
		List<String> foodOption = storeService.foodOption(foodId);
		
		return foodOption;
	}
	
	
	
	
}
