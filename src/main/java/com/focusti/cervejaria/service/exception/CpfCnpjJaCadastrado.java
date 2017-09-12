package com.focusti.cervejaria.service.exception;

public class CpfCnpjJaCadastrado extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CpfCnpjJaCadastrado(String message) {
		super(message);
	}
	
}