Brewer.TabeaItens = (function(){
	
	function TabeaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
	}
	
	TabeaItens.prototype.iniciar = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item) {
		
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				'codigoCerveja': item.codigo
			}
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
		
	}
	
	function onItemAtualizadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeItemAlterado.bind(this));
		$('.js-tabela-item').on('dblclick', onDoubleClick);
	}
	
	function onQuantidadeItemAlterado(evento) {
		
		var input = $(evento.target);
		var quantidade = input.val();
		var codigoCerveja = input.data('codigo');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				'quantidade' : quantidade
			}
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
		
	}
	
	function onDoubleClick(evento) {
		
		// var item = $(evento.currentTarget);
		// é equivalente a $(this);
		
		$(this).toggleClass('solicitando-exclusao');
		
	}
	
	return TabeaItens;
}());

$(function(){
	
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabeaItens(autocomplete);
	tabelaItens.iniciar();
	
});