package com.codeaddiction.jwt.service;

import com.codeaddiction.jwt.dto.UserRequest;

public interface UserService {
	
	public String addNewUser(UserRequest userRequest);

}
