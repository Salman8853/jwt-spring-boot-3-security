package com.codeaddiction.jwt.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codeaddiction.jwt.entity.User;
import com.codeaddiction.jwt.repository.UserRepository;



@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	

	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> findUserByEmail = userRepository.findUserByEmail(username);
		return findUserByEmail.map(MyUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found "+username));
	}

}
