package br.com.fiap.ecocycle.service;

import br.com.fiap.ecocycle.dto.PontoColetaDTO;
import br.com.fiap.ecocycle.model.PontoColeta;
import br.com.fiap.ecocycle.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PontoColetaService {

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    @Autowired
    private AlertaService alertaService;

    public List<PontoColetaDTO> listarTodos() {
        return pontoColetaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PontoColetaDTO> buscarPorId(Long id) {
        return pontoColetaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PontoColetaDTO salvar(PontoColetaDTO pontoColetaDTO) {
        PontoColeta pontoColeta = converterParaEntidade(pontoColetaDTO);

        if (pontoColeta.getId() == null) {
            pontoColeta.setDataCadastro(LocalDateTime.now());
        }

        pontoColeta.setUltimaAtualizacao(LocalDateTime.now());

        PontoColeta salvo = pontoColetaRepository.save(pontoColeta);
        return convertToDTO(salvo);
    }

    public void atualizarNivel(Long id, Double novoNivel) {
        pontoColetaRepository.findById(id).ifPresent(pontoColeta -> {
            pontoColeta.setNivelAtual(novoNivel);
            pontoColeta.setUltimaAtualizacao(LocalDateTime.now());
            pontoColetaRepository.save(pontoColeta);

            // Verificar se o nível atingiu o limite
            if (novoNivel >= pontoColeta.getCapacidadeMaxima() * 0.9) {
                alertaService.criarAlertaLimiteAtingido(pontoColeta);
            }
        });
    }

    public void excluir(Long id) {
        pontoColetaRepository.deleteById(id);
    }

    private PontoColetaDTO convertToDTO(PontoColeta pontoColeta) {
        PontoColetaDTO dto = new PontoColetaDTO();
        dto.setId(pontoColeta.getId());
        dto.setNome(pontoColeta.getNome());
        dto.setEndereco(pontoColeta.getEndereco());
        dto.setTipoResiduos(pontoColeta.getTipoResiduos());
        dto.setCapacidadeMaxima(pontoColeta.getCapacidadeMaxima());
        dto.setNivelAtual(pontoColeta.getNivelAtual());
        dto.setAtivo(pontoColeta.isAtivo());
        return dto;
    }

    private PontoColeta converterParaEntidade(PontoColetaDTO dto) {
        PontoColeta pontoColeta = new PontoColeta();
        pontoColeta.setId(dto.getId());
        pontoColeta.setNome(dto.getNome());
        pontoColeta.setEndereco(dto.getEndereco());
        pontoColeta.setTipoResiduos(dto.getTipoResiduos());
        pontoColeta.setCapacidadeMaxima(dto.getCapacidadeMaxima());
        pontoColeta.setNivelAtual(dto.getNivelAtual());
        pontoColeta.setAtivo(dto.isAtivo());
        return pontoColeta;
    }
}