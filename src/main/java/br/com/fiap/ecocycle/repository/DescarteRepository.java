package br.com.fiap.ecocycle.repository;

import br.com.fiap.ecocycle.model.Descarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DescarteRepository extends JpaRepository<Descarte, Long> {
    List<Descarte> findByPontoColetaId(Long pontoColetaId);
    List<Descarte> findByDataDescarteBetween(LocalDateTime inicio, LocalDateTime fim);
}