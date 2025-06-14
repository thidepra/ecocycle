package br.com.fiap.ecocycle.exception;

public class AcaoProibidaExcecao extends RuntimeException {
    public AcaoProibidaExcecao(String mensagem) {
        super(mensagem);
    }

    public AcaoProibidaExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}