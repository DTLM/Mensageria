package com.thiago.mensageria.exceptionHandler;

public class MensagemNotSendException extends RuntimeException{

	public MensagemNotSendException(String mensagem) {
		super(mensagem);
	}
	
	public MensagemNotSendException(String mensagem, Object...params) {
		super(String.format(mensagem, params));
	}
}
