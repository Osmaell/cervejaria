package com.focusti.cervejaria.storage;
	
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.focusti.cervejaria.config.FotoStorage;
import com.focusti.cervejaria.dto.FotoDTO;
	
public class FotoStorageRunnable implements Runnable {
	
	private MultipartFile[] files;
	private DeferredResult<FotoDTO> result;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> result, FotoStorage fotoStorage) {
		this.files = files;
		this.result = result;
		this.fotoStorage = fotoStorage;
	}
	
	@Override
	public void run() {
		
		this.fotoStorage.salvarTemporariamente(files);
		
		String nome = files[0].getOriginalFilename();
		String contentType = files[0].getContentType();
		
		result.setResult(new FotoDTO(nome, contentType));
		
	}
	
}