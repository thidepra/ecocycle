package br.com.fiap.ecocycle.controller;

import br.com.fiap.ecocycle.dto.AlertaDTO;
import br.com.fiap.ecocycle.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> listarAlertas() {
        return ResponseEntity.ok(alertaService.listarAlertas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<AlertaDTO>> listarAlertasPendentes() {
        return ResponseEntity.ok(alertaService.listarAlertasNaoResolvidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> buscarPorId(@PathVariable Long id) {
        return alertaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<AlertaDTO> resolverAlerta(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(alertaService.resolverAlerta(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}