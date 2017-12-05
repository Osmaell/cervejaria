Brewer.TabeaItens = (function(){
	
	function TabeaItens(autocomplete) {
		this.autocomplete = autocomplete;
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
		
		resposta.done(function(data){
			console.log('retorno', data);
		});
		
	}
	
	return TabeaItens;
}());

$(function(){
	
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabeaItens(autocomplete);
	tabelaItens.iniciar();
	
});