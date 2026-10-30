package com.naoesqueca.naoesqueca.api.controller;

import com.naoesqueca.naoesqueca.api.model.Prioridade;
import com.naoesqueca.naoesqueca.api.model.Status;
import com.naoesqueca.naoesqueca.api.service.LembreteService;
import com.naoesqueca.naoesqueca.dtos.LembreteRequestDTO;
import com.naoesqueca.naoesqueca.dtos.LembreteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lembretes")
public class LembreteController {

    private final LembreteService lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    @PostMapping
    public ResponseEntity<LembreteResponseDTO> cadastrar(@Valid @RequestBody LembreteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lembreteService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listar(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Prioridade prioridade) {
        return ResponseEntity.ok(lembreteService.listarComFiltros(status, prioridade));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<LembreteResponseDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(lembreteService.buscarPorPeriodo(inicio, fim));
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<LembreteResponseDTO>> listarAtrasados() {
        return ResponseEntity.ok(lembreteService.listarAtrasados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody LembreteRequestDTO dto) {
        return ResponseEntity.ok(lembreteService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<LembreteResponseDTO> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.concluir(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        lembreteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
