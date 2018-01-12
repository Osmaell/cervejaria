package com.focusti.cervejaria.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.focusti.cervejaria.model.Venda;

@Component
public class VendaValidator implements Validator {
	
	/**
	 * Responsável por informar qual classe
	 * é para ser validada.
	 * 
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Responsável por implementar
	 * a validação.
	 * 
	 * @param target
	 * @param erros
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");
		
		Venda venda = (Venda) target;
		
		validarSeInformouApenasHorarioEntrega(errors, venda);
		validarSeInformouItens(errors, venda);
		validarValorTotalNegativo(errors, venda);
		
	}
	
	private void validarSeInformouApenasHorarioEntrega(Errors errors, Venda venda) {
		
		if (venda.getDataEntrega() == null && venda.getHorarioEntrega() != null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data da entrega para um horário");
		}
		
	}
	
	private void validarSeInformouItens(Errors errors, Venda venda) {
		
		if (venda.getItens().isEmpty()) {
			errors.reject("", "Adicione pelo menos uma cerveja na venda");
		}
		
	}
	
	private void validarValorTotalNegativo(Errors errors, Venda venda) {
		
		if (venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "Valor total não pode ser negativo");
		}
		
	}
	
}