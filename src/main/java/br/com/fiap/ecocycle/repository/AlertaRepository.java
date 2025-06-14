package br.com.fiap.ecocycle.repository;

import br.com.fiap.ecocycle.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByPontoColetaIdAndResolvido(Long pontoColetaId, boolean resolvido);
    List<Alerta> findByResolvido(boolean resolvido);
}