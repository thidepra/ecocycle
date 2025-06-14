package br.com.fiap.ecocycle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoColetaDTO {
    private Long id;
    private String nome;
    private String endereco;
    private String tipoResiduos;
    private Double capacidadeMaxima;
    private Double nivelAtual;
    private boolean ativo;
}