package com.codeaddiction.jwt.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeaddiction.jwt.dto.UserRequest;
import com.codeaddiction.jwt.entity.User;
import com.codeaddiction.jwt.exception.GlobalServiceException;
import com.codeaddiction.jwt.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}



	@Override
	public String addNewUser(UserRequest userRequest) {
		Optional<User> findUserByEmail = userRepository.findUserByEmail(userRequest.getEmail());
		if(findUserByEmail.isPresent()) {
			throw new GlobalServiceException("User already registerd with the given email "+userRequest.getEmail());
		}
		
		User user= new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
		user.setRoles(userRequest.getRoles());
		userRepository.save(user);
		return "User Created Successfully..!!";
	}


}
