package br.com.fiap.ecocycle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDTO {
    private Long id;
    private Long pontoColetaId;
    private String tipoAlerta;
    private String mensagem;
    private boolean resolvido;
    private LocalDateTime dataCriacao;
}