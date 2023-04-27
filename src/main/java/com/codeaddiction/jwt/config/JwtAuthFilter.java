package com.codeaddiction.jwt.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter  {
	
	
	private MyUserDetailsService myUserDetailsService;
	private JwtService jwtService;
	
	public JwtAuthFilter(MyUserDetailsService myUserDetailsService, JwtService jwtService) {
		super();
		this.myUserDetailsService = myUserDetailsService;
		this.jwtService = jwtService;
	}



	@Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        String authHeader = request.getHeader("Authorization");
	        String token = null;
	        String username = null;
	      try {
	    	   if (authHeader != null && authHeader.startsWith("Bearer ")) {
		            token = authHeader.substring(7);
		            username = jwtService.extractUsername(token);
		        }
		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		            if (jwtService.validateToken(token, userDetails)) {
		                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(authToken);
		            }
		              
		        }
		      filterChain.doFilter(request, response);
		        
	      }catch (SignatureException ex){
	            response.setContentType("application/json");
		        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        response.getOutputStream().println("{ \"error\": \"Invalid JWT signature \" }");
	            
        }catch (MalformedJwtException e) {
			//logger.error("Invalid JWT token: {}", e.getMessage())
			System.out.println("Invalid JWT token: {}");
			
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getOutputStream().println("{ \"error\": \"Invalid JWT token \" }");
			
		} catch (ExpiredJwtException e) {
			//logger.error("JWT token is expired: {}", e.getMessage());
			System.out.println("JWT token is expired:");
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getOutputStream().println("{ \"error\": \"JWT token is expired \" }");
			
		} catch (UnsupportedJwtException e) {
			//logger.error("JWT token is unsupported: {}", e.getMessage());
			System.out.println("JWT token is unsupported: {}");
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getOutputStream().println("{ \"error\": \"JWT token is unsupported \" }");
		} catch (IllegalArgumentException e) {
			//logger.error("JWT claims string is empty: {}", e.getMessage());
			System.out.println("JWT claims string is empty: {}");
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getOutputStream().println("{ \"error\": \"JWT claims string is empty \" }");
		}catch(Exception e) {
			System.out.println("JWT token is Exception: {}");
			response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getOutputStream().println("{ \"error\": \"Exception occure in JWT token \" }");
		}  
		          
	    }

}
