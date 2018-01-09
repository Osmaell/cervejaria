package com.focusti.cervejaria.controller;
	
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.repository.Cervejas;
import com.focusti.cervejaria.security.UsuarioSistema;
import com.focusti.cervejaria.service.VendaService;
import com.focusti.cervejaria.session.TabelasItensSession;
	
@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	private static final String CADASTRO_VENDA_VIEW = "venda/CadastroVenda";
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@Autowired
	private VendaService vendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VENDA_VIEW);
		venda.setUuid(UUID.randomUUID().toString());
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar( Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		venda.setUsuario(usuarioSistema.getUsuario());
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		
		vendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping("/item")
	public @ResponseBody ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		
		return mvTabelaItensVenda(uuid);
	}
	
 	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade, String uuid) {
		
		this.tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		
		return mvTabelaItensVenda(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluir( @PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable("uuid") String uuid ) {
		
		this.tabelaItens.excluirItem(uuid, cerveja);
		
		return mvTabelaItensVenda(uuid);
	}
	
	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
	
}