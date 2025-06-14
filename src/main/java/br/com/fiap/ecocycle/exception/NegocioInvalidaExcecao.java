package br.com.fiap.ecocycle.exception;

public class NegocioInvalidaExcecao extends RuntimeException {
    public NegocioInvalidaExcecao(String mensagem) {
        super(mensagem);
    }

    public NegocioInvalidaExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}