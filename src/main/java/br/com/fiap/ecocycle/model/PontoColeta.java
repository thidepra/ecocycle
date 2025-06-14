package br.com.fiap.ecocycle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ponto_coleta")
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String tipoResiduos;
    private Double capacidadeMaxima;
    private Double nivelAtual;
    private boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimaAtualizacao;
}