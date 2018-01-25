package com.focusti.cervejaria.controller;
	
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.controller.page.PageWrapper;
import com.focusti.cervejaria.controller.validator.VendaValidator;
import com.focusti.cervejaria.mail.Mailer;
import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.model.constants.StatusVenda;
import com.focusti.cervejaria.repository.Cervejas;
import com.focusti.cervejaria.repository.Vendas;
import com.focusti.cervejaria.repository.filter.VendaFilter;
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
	private Vendas vendas;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VendaValidator vendaValidator;
	
	@Autowired
	private Mailer mailer;
	
	@InitBinder("venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VENDA_VIEW);
		
		if (StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));
		
		return mv;
	}
	
	@PostMapping(value = "/nova", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		vendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping(value = "/nova", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		vendaService.emitir(venda);
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso");
		
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping(value = "/nova", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		venda = vendaService.salvar(venda);
		mailer.enviar(venda);
		
		attributes.addFlashAttribute("mensagem", String.format("Venda n° %d salva com sucesso e e-mail enviado", venda.getCodigo()));
		
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter, @PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		Page<Venda> page = vendas.filtrar(vendaFilter, pageable);
		PageWrapper<Venda> pageWrapper = new PageWrapper<>(page, httpServletRequest);
		
		ModelAndView mv = new ModelAndView("venda/PesquisaVendas");
		
		mv.addObject("status", StatusVenda.values());
		mv.addObject("pagina", pageWrapper);
		
		return mv;
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
	
	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();
		
		vendaValidator.validate(venda, result);
	}
	
}