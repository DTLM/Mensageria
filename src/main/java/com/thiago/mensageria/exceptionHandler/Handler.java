package com.thiago.mensageria.exceptionHandler;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import jakarta.annotation.Resource;

@RestControllerAdvice
public class Handler extends ResponseEntityExceptionHandler{

	@Resource
	private MessageSource mensaSource;
	private HttpHeaders headers() {
		HttpHeaders head = new HttpHeaders();
		head.setContentType(MediaType.APPLICATION_JSON);
		return head;
	}
	
	private ResponseError response(String mensagem, HttpStatus status) {
		ResponseError error = new ResponseError();
		error.setError(mensagem);
		error.setStatus("error");
		error.setStatusCode(status.value());
		return error;
	}
}
