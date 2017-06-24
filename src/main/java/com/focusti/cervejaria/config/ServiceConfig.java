package com.focusti.cervejaria.config;
	
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.focusti.cervejaria.service.CervejaService;
import com.focusti.cervejaria.storage.local.FotoStorageLocal;
	
@Configuration
@ComponentScan(basePackageClasses = CervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorageLocal() {
		return new FotoStorageLocal();
	}
	
}