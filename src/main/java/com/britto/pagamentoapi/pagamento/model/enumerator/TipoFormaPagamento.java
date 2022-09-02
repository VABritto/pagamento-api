package com.britto.pagamentoapi.pagamento.model.enumerator;

public enum TipoFormaPagamento {

	AVISTA("AVISTA"), 
	PARCELADO_LOJA("PARCELADO LOJA"), 
	PARCELADO_EMISSOR("PARCELADO EMISSOR");
	
	private String value;
	
	TipoFormaPagamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

	public static TipoFormaPagamento getEnum(String value) {
        for(TipoFormaPagamento v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException("TipoFormaPagamento Inválido");
    }
	
	public static boolean isTipoFormaPagamentoValido(String value) {
		try {
			getEnum(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
