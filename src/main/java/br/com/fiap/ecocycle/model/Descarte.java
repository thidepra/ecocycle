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
@Table(name = "descarte")
public class Descarte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ponto_coleta_id")
    private PontoColeta pontoColeta;

    private String tipoResiduo;
    private Double quantidade;
    private String usuarioResponsavel;
    private LocalDateTime dataDescarte;
    private String observacoes;
}