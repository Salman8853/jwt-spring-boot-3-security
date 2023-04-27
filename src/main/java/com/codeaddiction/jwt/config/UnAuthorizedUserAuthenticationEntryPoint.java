package com.codeaddiction.jwt.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UnAuthorizedUserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		   //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UnAuthorized User");
		
		    response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
		
	}


}
