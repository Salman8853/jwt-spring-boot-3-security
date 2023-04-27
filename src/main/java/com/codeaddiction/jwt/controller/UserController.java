package com.codeaddiction.jwt.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeaddiction.jwt.config.JwtService;
import com.codeaddiction.jwt.dto.AuthUserRequest;
import com.codeaddiction.jwt.dto.UserRequest;
import com.codeaddiction.jwt.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    
   
	public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}


	@GetMapping("/welcome")
	public String helloCommon() {
		return "Hello welcome sir...!!";
	}
	
	
	@PostMapping("/add-user")
	public String addNewUser(@RequestBody UserRequest userRequest) {
		return userService.addNewUser(userRequest);
	}
	
	
	@PostMapping("/authenticate")
	public String authUser(@RequestBody AuthUserRequest authUserRequest) {
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUserRequest.getUsername(), authUserRequest.getPassword()));
	        if (authentication.isAuthenticated()) {
	            return jwtService.generateToken(authUserRequest.getUsername());
	        } else {
	            throw new UsernameNotFoundException("invalid user request !");
	        }
	}
	
}
