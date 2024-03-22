package com.thiago.mensageria.exceptionHandler;

import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import jakarta.annotation.Resource;

@RestControllerAdvice
public class Handler extends ResponseEntityExceptionHandler{

	@Resource
	private MessageSource messageSource;
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
	
	@ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (e.getClass().isAssignableFrom(UndeclaredThrowableException.class)) {
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
            return handleBusinessException((MensagemNotSendException) exception.getUndeclaredThrowable(), request);
        } else {
            String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
            ResponseError error = response(message,HttpStatus.INTERNAL_SERVER_ERROR);
            return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }
    @ExceptionHandler({MensagemNotSendException.class})
    private ResponseEntity<Object> handleBusinessException(MensagemNotSendException e, WebRequest request) {
        ResponseError error = response(e.getMessage(),HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(e, error, headers(), HttpStatus.BAD_REQUEST, request);
    }
}
