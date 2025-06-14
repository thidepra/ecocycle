package br.com.fiap.ecocycle.service;

import br.com.fiap.ecocycle.dto.AlertaDTO;
import br.com.fiap.ecocycle.model.Alerta;
import br.com.fiap.ecocycle.model.PontoColeta;
import br.com.fiap.ecocycle.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;



    public List<AlertaDTO> listarAlertas() {
        return alertaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AlertaDTO> listarAlertasNaoResolvidos() {
        return alertaRepository.findByResolvido(false).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AlertaDTO> buscarPorId(Long id) {
        return alertaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void criarAlertaLimiteAtingido(PontoColeta pontoColeta) {
        Alerta alerta = new Alerta();
        alerta.setPontoColeta(pontoColeta);
        alerta.setTipoAlerta("LIMITE_ATINGIDO");
        alerta.setMensagem("O ponto de coleta " + pontoColeta.getNome() + " atingiu 90% da sua capacidade máxima.");
        alerta.setResolvido(false);
        alerta.setDataCriacao(LocalDateTime.now());

        alertaRepository.save(alerta);


    }

    public AlertaDTO resolverAlerta(Long id) {
        Optional<Alerta> alertaOpt = alertaRepository.findById(id);

        if (alertaOpt.isEmpty()) {
            throw new RuntimeException("Alerta não encontrado");
        }

        Alerta alerta = alertaOpt.get();
        alerta.setResolvido(true);
        alerta.setDataResolucao(LocalDateTime.now());

        return convertToDTO(alertaRepository.save(alerta));
    }

    private AlertaDTO convertToDTO(Alerta alerta) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(alerta.getId());
        dto.setPontoColetaId(alerta.getPontoColeta().getId());
        dto.setTipoAlerta(alerta.getTipoAlerta());
        dto.setMensagem(alerta.getMensagem());
        dto.setResolvido(alerta.isResolvido());
        dto.setDataCriacao(alerta.getDataCriacao());
        return dto;
    }
}