package br.com.fiap.ecocycle.exception;

public class EntidadeNaoLocalizadaExcecao extends RuntimeException {
    public EntidadeNaoLocalizadaExcecao(String mensagem) {
        super(mensagem);
    }

    public EntidadeNaoLocalizadaExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}