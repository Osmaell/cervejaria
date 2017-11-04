package com.focusti.cervejaria.service;

import com.focusti.cervejaria.repository.Usuarios;

public enum StatusUsuario {
	
	ATIVAR {
		
		@Override
		public void executar(Long[] codigos, Usuarios usuarios) {
			usuarios.findByCodigoIn(codigos).forEach(u -> {
				u.setAtivo(true);
				usuarios.save(u);
			});
		}
		
	},
	
	DESATIVAR {
		
		@Override
		public void executar(Long[] codigos, Usuarios usuarios) {
			usuarios.findByCodigoIn(codigos).forEach(u -> {
				u.setAtivo(false);
				usuarios.save(u);
			});
		}
		
	};
	
	public abstract void executar(Long[] codigos, Usuarios usuarios);
	
}