package br.com.fiap.ecocycle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostaErroDTO {
    private LocalDateTime momento;
    private Integer codigo;
    private String tipo;
    private String descricao;
    private String url;
    private List<ErroItem> itens;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErroItem {
        private String atributo;
        private String descricao;
    }
}