package com.focusti.cervejaria.mail;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.focusti.cervejaria.model.Venda;
	
@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void enviar(Venda venda) {
		
		SimpleMailMessage mensagem = new SimpleMailMessage();
		
		mensagem.setFrom("osmaelsousabraga@gmail.com");
		mensagem.setTo(venda.getCliente().getEmail());
		mensagem.setSubject("Venda efetuada");
		mensagem.setText("Obrigado, sua venda foi processada!");
		
		mailSender.send(mensagem);
		
	}
	
}