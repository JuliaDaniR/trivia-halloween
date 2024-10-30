package com.halloween.trivia.service;

import com.halloween.trivia.dto.PuntajeUsuarioDTO;
import com.halloween.trivia.repository.PuntajeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PuntajeService {

    @Autowired
    private PuntajeRepository puntajeRepository;

    public List<PuntajeUsuarioDTO> obtenerPuntajesPorDificultad() {
        return puntajeRepository.obtenerPuntajePorDificultad();
    }
    @Transactional(readOnly = true)
    public List<PuntajeUsuarioDTO> obtenerPuntajesPorIdUsuario(Long id) {
        return puntajeRepository.obtenerPuntajesPorIdUsuario(id);
    }
}
