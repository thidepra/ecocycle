package br.com.fiap.ecocycle.controller;

import br.com.fiap.ecocycle.dto.PontoColetaDTO;
import br.com.fiap.ecocycle.service.PontoColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos-coleta")
public class PontoColetaController {

    @Autowired
    private PontoColetaService pontoColetaService;

    @GetMapping
    public ResponseEntity<List<PontoColetaDTO>> listarTodos() {
        return ResponseEntity.ok(pontoColetaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoColetaDTO> buscarPorId(@PathVariable Long id) {
        return pontoColetaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PontoColetaDTO> criar(@RequestBody PontoColetaDTO pontoColetaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pontoColetaService.salvar(pontoColetaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoColetaDTO> atualizar(@PathVariable Long id, @RequestBody PontoColetaDTO pontoColetaDTO) {
        if (!pontoColetaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        pontoColetaDTO.setId(id);
        return ResponseEntity.ok(pontoColetaService.salvar(pontoColetaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!pontoColetaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        pontoColetaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}