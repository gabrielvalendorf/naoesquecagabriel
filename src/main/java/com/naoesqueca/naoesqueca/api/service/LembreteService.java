package com.naoesqueca.naoesqueca.api.service;


import com.naoesqueca.naoesqueca.api.exeption.ResourceNotFoundException;
import com.naoesqueca.naoesqueca.api.model.Lembrete;
import com.naoesqueca.naoesqueca.api.model.Status;
import com.naoesqueca.naoesqueca.dtos.LembreteRequestDTO;
import com.naoesqueca.naoesqueca.dtos.LembreteResponseDTO;
import com.naoesqueca.naoesqueca.repository.LembreteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LembreteService {

    private final LembreteRepository lembreteRepository;

    public LembreteService(LembreteRepository lembreteRepository) {
        this.lembreteRepository = lembreteRepository;
    }

    @Transactional
    public LembreteResponseDTO cadastrar(LembreteRequestDTO dto) {
        Lembrete lembrete = new Lembrete();
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setStatus(Status.PENDENTE);
        lembrete.setCriadoEm(LocalDateTime.now());
        lembrete.setAtualizadoEm(LocalDateTime.now());

        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    public List<LembreteResponseDTO> listarTodos() {
        List<Lembrete> lembretes = lembreteRepository.findAll();
        List<LembreteResponseDTO> dtos = new ArrayList<>();

        for (Lembrete l : lembretes) {
            dtos.add(LembreteResponseDTO.fromEntity(l));
        }

        return dtos;
    }

    public LembreteResponseDTO buscarPorId(Long id) {
        return LembreteResponseDTO.fromEntity(obterEntidadePorId(id));
    }

    @Transactional
    public LembreteResponseDTO atualizar(Long id, LembreteRequestDTO dto) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setDescricao(dto.getDescricao());
        lembrete.setDataHora(dto.getDataHora());
        lembrete.setPrioridade(dto.getPrioridade());
        lembrete.setAtualizadoEm(LocalDateTime.now());

        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    @Transactional
    public LembreteResponseDTO concluir(Long id) {
        Lembrete lembrete = obterEntidadePorId(id);
        lembrete.setStatus(Status.CONCLUIDO);
        lembrete.setAtualizadoEm(LocalDateTime.now());
        return LembreteResponseDTO.fromEntity(lembreteRepository.save(lembrete));
    }

    private Lembrete obterEntidadePorId(Long id) {
        Optional<Lembrete> lembrete = lembreteRepository.findById(id);
        if (lembrete.isPresent()){
            return lembrete.get();
        }else{
            throw new ResourceNotFoundException("Lembrete não encontrado.");
        }
    }
}
