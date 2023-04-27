package com.codeaddiction.jwt.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private Integer id;
	private String name;
	private String password;
	private String email;
	private List<String> roles;
}
