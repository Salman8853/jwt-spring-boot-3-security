package com.codeaddiction.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private JwtAuthFilter authFilter;
	private MyUserDetailsService myUserDetailsService;
	private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;
	
	
	public SecurityConfig(JwtAuthFilter authFilter, MyUserDetailsService myUserDetailsService,
			UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint) {
		this.authFilter = authFilter;
		this.myUserDetailsService = myUserDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}


	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/product/**")
                .authenticated()
                .and()
                .exceptionHandling(a->a.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    
    // This Authorization mechanism used for Dao based authentication
    
    @Bean 
    AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
      provider.setPasswordEncoder(passwordEncoder());
      provider.setUserDetailsService(this.myUserDetailsService);
      return provider;
    }


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
   
}
