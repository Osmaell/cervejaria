package com.focusti.cervejaria.mail;
	
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.focusti.cervejaria.model.Venda;
	
@Component
public class Mailer {
	
	private static Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Async
	public void enviar(Venda venda) {
		
		Context context = new Context();
		context.setVariable("venda", venda);
		context.setVariable("logo", "logo");
		context.setVariable("mockCerveja", "mockCerveja");
		
		try {
			
			String email = thymeleaf.process("mail/ResumoVenda", context);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setFrom("osmaeltestefrom@gmail.com");
			helper.setTo("osmaeltesteto@gmail.com");
			helper.setSubject("Brewer - Venda realizada");
			helper.setText(email, true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));
			
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		
	}
	
}