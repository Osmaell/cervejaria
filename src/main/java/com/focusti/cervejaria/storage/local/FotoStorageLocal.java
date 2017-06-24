package com.focusti.cervejaria.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import com.focusti.cervejaria.config.FotoStorage;

public class FotoStorageLocal implements FotoStorage {
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}
	
	@Override
	public void salvarTemporariamente(MultipartFile... files) {
		System.out.println("Salvando a foto temporáriamente");
	}
	
	private void criarPastas() {
		
		try {
			
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
		
	}
	
}