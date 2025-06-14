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
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ponto_coleta_id")
    private PontoColeta pontoColeta;

    private String tipoAlerta; // LIMITE_ATINGIDO, COLETA_PENDENTE, etc.
    private String mensagem;
    private boolean resolvido;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataResolucao;
}