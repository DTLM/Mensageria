package com.thiago.mensageria.exceptionHandler;

import java.time.LocalDate;

public class ResponseError {

	private LocalDate tempo = LocalDate.now();
	private String status = "error";
	private int statusCode;
	private String error;
	public LocalDate getTempo() {
		return tempo;
	}
	public void setTempo(LocalDate tempo) {
		this.tempo = tempo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
