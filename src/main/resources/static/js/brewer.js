var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
		this.sku = $('.js-sku');
	}
	
	MaskMoney.prototype.enable = function () {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
		this.sku.mask('AA0000');
		$('[data-toggle="tooltip"]').tooltip();
	}
	
	return MaskMoney;
	
}());

$(function(){
	
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
});