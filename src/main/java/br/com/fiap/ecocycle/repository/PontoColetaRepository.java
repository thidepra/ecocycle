package br.com.fiap.ecocycle.repository;

import br.com.fiap.ecocycle.model.PontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {
    List<PontoColeta> findByTipoResiduosContainingAndAtivo(String tipoResiduo, boolean ativo);
    List<PontoColeta> findByNivelAtualGreaterThanEqual(Double nivel);
}