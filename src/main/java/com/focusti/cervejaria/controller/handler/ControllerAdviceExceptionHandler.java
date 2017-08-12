package com.focusti.cervejaria.controller.handler;
	
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.focusti.cervejaria.service.exception.NomeEstiloJaCadastradoException;
	
/**
 * Responsável por observar se alguma
 * exceção foi lançada por algum controller
 * que não foi tratada e ele vai tratar.
 * 
 * @author root
 *
 */
@ControllerAdvice
public class ControllerAdviceExceptionHandler {
	
	@ExceptionHandler(NomeEstiloJaCadastradoException.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
}