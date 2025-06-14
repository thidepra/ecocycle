package br.com.fiap.ecocycle.controller;

import br.com.fiap.ecocycle.dto.DescarteDTO;
import br.com.fiap.ecocycle.service.DescarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descartes")
public class DescarteController {

    @Autowired
    private DescarteService descarteService;

    @GetMapping
    public ResponseEntity<List<DescarteDTO>> listarTodos() {
        return ResponseEntity.ok(descarteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescarteDTO> buscarPorId(@PathVariable Long id) {
        return descarteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DescarteDTO> registrar(@RequestBody DescarteDTO descarteDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(descarteService.registrarDescarte(descarteDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ponto-coleta/{pontoColetaId}")
    public ResponseEntity<List<DescarteDTO>> listarPorPontoColeta(@PathVariable Long pontoColetaId) {
        return ResponseEntity.ok(descarteService.listarPorPontoColeta(pontoColetaId));
    }
}