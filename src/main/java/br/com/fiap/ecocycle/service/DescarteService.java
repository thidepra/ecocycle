package br.com.fiap.ecocycle.service;

import br.com.fiap.ecocycle.dto.DescarteDTO;
import br.com.fiap.ecocycle.model.Descarte;
import br.com.fiap.ecocycle.model.PontoColeta;
import br.com.fiap.ecocycle.repository.DescarteRepository;
import br.com.fiap.ecocycle.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DescarteService {

    @Autowired
    private DescarteRepository descarteRepository;

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    @Autowired
    private PontoColetaService pontoColetaService;

    public List<DescarteDTO> listarTodos() {
        return descarteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DescarteDTO> buscarPorId(Long id) {
        return descarteRepository.findById(id)
                .map(this::convertToDTO);
    }

    public DescarteDTO registrarDescarte(DescarteDTO descarteDTO) {
        Optional<PontoColeta> pontoColetaOpt = pontoColetaRepository.findById(descarteDTO.getPontoColetaId());

        if (pontoColetaOpt.isEmpty()) {
            throw new RuntimeException("Ponto de coleta não encontrado");
        }

        PontoColeta pontoColeta = pontoColetaOpt.get();

        Descarte descarte = new Descarte();
        descarte.setPontoColeta(pontoColeta);
        descarte.setTipoResiduo(descarteDTO.getTipoResiduo());
        descarte.setQuantidade(descarteDTO.getQuantidade());
        descarte.setUsuarioResponsavel(descarteDTO.getUsuarioResponsavel());
        descarte.setObservacoes(descarteDTO.getObservacoes());
        descarte.setDataDescarte(LocalDateTime.now());

        Descarte salvo = descarteRepository.save(descarte);

        // Atualizar o nível do ponto de coleta
        Double novoNivel = pontoColeta.getNivelAtual() + descarteDTO.getQuantidade();
        pontoColetaService.atualizarNivel(pontoColeta.getId(), novoNivel);

        return convertToDTO(salvo);
    }

    public List<DescarteDTO> listarPorPontoColeta(Long pontoColetaId) {
        return descarteRepository.findByPontoColetaId(pontoColetaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DescarteDTO convertToDTO(Descarte descarte) {
        DescarteDTO dto = new DescarteDTO();
        dto.setId(descarte.getId());
        dto.setPontoColetaId(descarte.getPontoColeta().getId());
        dto.setTipoResiduo(descarte.getTipoResiduo());
        dto.setQuantidade(descarte.getQuantidade());
        dto.setUsuarioResponsavel(descarte.getUsuarioResponsavel());
        dto.setObservacoes(descarte.getObservacoes());
        return dto;
    }
}