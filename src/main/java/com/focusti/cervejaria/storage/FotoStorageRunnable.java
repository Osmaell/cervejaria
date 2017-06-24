package com.focusti.cervejaria.storage;
	
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.focusti.cervejaria.dto.FotoDTO;
	
public class FotoStorageRunnable implements Runnable {
	
	private MultipartFile[] files;
	private DeferredResult<FotoDTO> result;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result) {
		this.files = files;
		this.result = result;
	}
	
	@Override
	public void run() {
		
		String nome = files[0].getOriginalFilename();
		String contentType = files[0].getContentType();
		
		result.setResult(new FotoDTO(nome, contentType));
		
	}

}
