package com.codeaddiction.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@GetMapping("/")
	public String helloUser() {
       return "Selected Product..!!";
	}
	
	
	@GetMapping("/all-product")
	public String helloadmin() {
		return "All products..!!";
	}

}
