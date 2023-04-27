package com.codeaddiction.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.ServletException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(GlobalServiceException.class)
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public ResponseEntity<String> getGlobalException(GlobalServiceException e,WebRequest webRequest){ 
		 return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	 }
	 
	 @ExceptionHandler(ServletException.class)
	 @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	 public ResponseEntity<String> getGlobalException2(ServletException e,WebRequest webRequest){ 
		 return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	 }
	 
}
