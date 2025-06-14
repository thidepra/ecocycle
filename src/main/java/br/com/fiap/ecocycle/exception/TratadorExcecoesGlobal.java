package br.com.fiap.ecocycle.exception;

import br.com.fiap.ecocycle.dto.RespostaErroDTO;
import br.com.fiap.ecocycle.exception.EntidadeNaoLocalizadaExcecao;
import br.com.fiap.ecocycle.exception.NegocioInvalidaExcecao;
import br.com.fiap.ecocycle.exception.AcaoProibidaExcecao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class TratadorExcecoesGlobal {

    @ExceptionHandler(EntidadeNaoLocalizadaExcecao.class)
    public ResponseEntity<RespostaErroDTO> gerenciarEntidadeNaoLocalizada(
            EntidadeNaoLocalizadaExcecao ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.NOT_FOUND.value());
        erro.setTipo("Entidade não localizada");
        erro.setDescricao(ex.getMessage());
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AcaoProibidaExcecao.class)
    public ResponseEntity<RespostaErroDTO> gerenciarAcaoProibida(
            AcaoProibidaExcecao ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.FORBIDDEN.value());
        erro.setTipo("Ação proibida");
        erro.setDescricao(ex.getMessage());
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NegocioInvalidaExcecao.class)
    public ResponseEntity<RespostaErroDTO> gerenciarNegocioInvalida(
            NegocioInvalidaExcecao ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.BAD_REQUEST.value());
        erro.setTipo("Regra de negócio violada");
        erro.setDescricao(ex.getMessage());
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErroDTO> gerenciarValidacaoArgumento(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<RespostaErroDTO.ErroItem> detalhes = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(erro -> new RespostaErroDTO.ErroItem(erro.getField(), erro.getDefaultMessage()))
                .collect(Collectors.toList());

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.BAD_REQUEST.value());
        erro.setTipo("Validação falhou");
        erro.setDescricao("Dados fornecidos são inválidos");
        erro.setUrl(request.getRequestURI());
        erro.setItens(detalhes);

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespostaErroDTO> gerenciarViolacaoRestricao(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<RespostaErroDTO.ErroItem> detalhes = ex.getConstraintViolations()
                .stream()
                .map(erro -> new RespostaErroDTO.ErroItem(
                        erro.getPropertyPath().toString(),
                        erro.getMessage()))
                .collect(Collectors.toList());

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.BAD_REQUEST.value());
        erro.setTipo("Validação falhou");
        erro.setDescricao("Restrições foram violadas");
        erro.setUrl(request.getRequestURI());
        erro.setItens(detalhes);

        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespostaErroDTO> gerenciarAcessoNegado(
            AccessDeniedException ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.FORBIDDEN.value());
        erro.setTipo("Acesso negado");
        erro.setDescricao("Sem permissão para este recurso");
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespostaErroDTO> gerenciarCredenciaisInvalidas(
            BadCredentialsException ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.UNAUTHORIZED.value());
        erro.setTipo("Autenticação falhou");
        erro.setDescricao("Credenciais inválidas fornecidas");
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErroDTO> gerenciarExcecaoGenerica(
            Exception ex,
            HttpServletRequest request) {

        RespostaErroDTO erro = new RespostaErroDTO();
        erro.setMomento(LocalDateTime.now());
        erro.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        erro.setTipo("Erro interno");
        erro.setDescricao("Um erro inesperado ocorreu. Contate o suporte técnico.");
        erro.setUrl(request.getRequestURI());

        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}