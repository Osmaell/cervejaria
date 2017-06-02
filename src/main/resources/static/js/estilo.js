var Estilo = Estilo || {};
	
Estilo.CadastroEstiloRapido = (function(){
	
	function CadastroEstiloRapido() {
		this.modal = $('#modalCadastroRapidoEstilo');
		this.btnSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeEstilo = $('#nomeEstilo');
		this.containerError = $('.js-mensagem-cadastro-rapido-estilo');
	}
	
	CadastroEstiloRapido.prototype.iniciar = function() {
		
		this.form.on('submit', function(event) {event.preventDefault()} );
		
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hidden.bs.modal', onModalClose.bind(this));
		this.btnSalvar.on('click', onSalvarEstilo.bind(this));
		
	}
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	

	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.containerError.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onSalvarEstilo() {
		
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify( {nome: nomeEstilo} ),
			success: onSalvarEstiloSucesso.bind(this),
			error: onSalvarEstiloErro.bind(this)
		});
		
	}
	
	function onSalvarEstiloSucesso(estilo) {
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
	}
	
	function onSalvarEstiloErro(obj) {
		
		this.containerError.removeClass('hidden');
		this.containerError.html('\
				<i class="fa fa-exclamation-circle" ></i>\
				<span>' + obj.responseText +'</span>');
		
		this.form.find('.form-group').addClass('has-error');
	}
	
	return CadastroEstiloRapido;
}());

$(function() {
	
	var cadastroEstiloRapido = new Estilo.CadastroEstiloRapido();
	cadastroEstiloRapido.iniciar();
	
});