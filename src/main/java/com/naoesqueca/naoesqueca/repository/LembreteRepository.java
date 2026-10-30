package com.naoesqueca.naoesqueca.repository;


import com.naoesqueca.naoesqueca.api.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
}

