package com.focusti.cervejaria.controller;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.focusti.cervejaria.config.FotoStorage;
import com.focusti.cervejaria.dto.FotoDTO;
import com.focusti.cervejaria.storage.FotoStorageRunnable;
	
@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> nova(@RequestParam("files[]") MultipartFile ... files) {
		DeferredResult<FotoDTO> result = new DeferredResult<>();
		
		Thread thread = new Thread(new FotoStorageRunnable(files, result, fotoStorage));
		thread.start();
		
		return result;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporariamente(@PathVariable String nome) {
		return fotoStorage.recuperarFotoTemporaria(nome);
	}
	
}