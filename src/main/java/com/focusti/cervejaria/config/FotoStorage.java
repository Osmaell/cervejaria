package com.focusti.cervejaria.config;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public void salvarTemporariamente(MultipartFile ...files);
	
}