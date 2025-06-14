package br.com.fiap.ecocycle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescarteDTO {
    private Long id;
    private Long pontoColetaId;
    private String tipoResiduo;
    private Double quantidade;
    private String usuarioResponsavel;
    private String observacoes;
}