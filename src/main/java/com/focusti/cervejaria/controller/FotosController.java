package com.focusti.cervejaria.controller;
	
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.focusti.cervejaria.dto.FotoDTO;
import com.focusti.cervejaria.storage.FotoStorageRunnable;
	
@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@PostMapping
	public DeferredResult<FotoDTO> nova(@RequestParam("files[]") MultipartFile ... files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(files, result));
		thread.start();
		
		return result;
	}
	
}