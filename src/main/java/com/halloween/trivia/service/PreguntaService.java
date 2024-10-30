package com.halloween.trivia.service;

import com.halloween.trivia.dto.DatosRegistroPregunta;
import com.halloween.trivia.dto.DatosRespuestaPregunta;
import com.halloween.trivia.dto.EstadisticasDTO;
import com.halloween.trivia.dto.LogroDTO;
import com.halloween.trivia.entidades.Pregunta;
import com.halloween.trivia.entidades.Puntaje;
import com.halloween.trivia.entidades.Respuesta;
import com.halloween.trivia.entidades.Usuario;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;
import com.halloween.trivia.repository.PreguntaRepository;
import com.halloween.trivia.repository.PuntajeRepository;
import com.halloween.trivia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PuntajeRepository puntajeRepo;

    @Transactional
    public DatosRespuestaPregunta registrarPregunta(DatosRegistroPregunta datosRegistroPregunta) {

        DatosRegistroPregunta.validarOpcionesPorDificultad(datosRegistroPregunta.dificultad(), datosRegistroPregunta.opciones());

        Pregunta pregunta = new Pregunta(datosRegistroPregunta);
        List<Respuesta> respuestas = datosRegistroPregunta.opciones().stream()
                .map(datosRespuesta -> new Respuesta(null, datosRespuesta.texto(), datosRespuesta.esCorrecta(), pregunta))
                .collect(Collectors.toList());

        pregunta.setOpciones(respuestas);

        preguntaRepo.save(pregunta);
        return new DatosRespuestaPregunta(pregunta);
    }

    public Page<DatosRespuestaPregunta> listarPreguntas(Pageable paginacion) {
        Page<Pregunta> preguntas = preguntaRepo.findAll(paginacion);
        return preguntas.map(DatosRespuestaPregunta::new);
    }

    @Transactional
    public DatosRespuestaPregunta actualizarPregunta(Long id, DatosRegistroPregunta.DatosActualizarPregunta datosActualizarPregunta) {
        Pregunta pregunta = preguntaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada con id " + id));

        pregunta.actualizarDatos(datosActualizarPregunta);
        preguntaRepo.save(pregunta);
        return new DatosRespuestaPregunta(pregunta);
    }

    @Transactional
    public boolean eliminarPregunta(Long id) {
        if (!preguntaRepo.existsById(id)) {
            return false;
        }
        preguntaRepo.deleteById(id);
        return true;
    }

    public DatosRespuestaPregunta buscarPreguntaAleatoria(Dificultad dificultad, Tipo tipo) {
        List<Pregunta> preguntas = preguntaRepo.findByDificultadAndTipo(dificultad, tipo);

        if (preguntas.isEmpty()) {
            throw new NoSuchElementException("No se encontró una pregunta para la dificultad y tipo dados");
        }
        Pregunta preguntaAleatoria = preguntas.get(new Random().nextInt(preguntas.size()));

        return new DatosRespuestaPregunta(preguntaAleatoria);
    }
    @Transactional
    public boolean jugar(Long preguntaId, Long respuestaId, Usuario usuario) {
        Pregunta pregunta = preguntaRepo.findById(preguntaId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        Respuesta respuestaCorrecta = pregunta.getOpciones().stream()
                .filter(Respuesta::isEsCorrecta)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No se encontró una respuesta correcta"));

        boolean esCorrecta = respuestaCorrecta.getId().equals(respuestaId);

        if (esCorrecta) {
            sumarPuntos(usuario, pregunta.getDificultad(), pregunta);
        }

        return esCorrecta;
    }

    private void sumarPuntos(Usuario usuario, Dificultad dificultad, Pregunta pregunta) {
        Puntaje puntaje = new Puntaje();
        puntaje.setUsuario(usuario);
        puntaje.setPregunta(pregunta);
        puntaje.setDificultad(dificultad);
        puntaje.setFecha(new Date());

        int puntos = switch (dificultad) {
            case FACIL -> 5;
            case MEDIO -> 10;
            case DIFICIL -> 20;
        };
        puntaje.setPuntos(puntos);

        puntajeRepo.save(puntaje);
    }

    public Page<DatosRespuestaPregunta> listarPreguntasPorDificultad(Dificultad dificultad, Pageable paginacion) {
        Page<Pregunta> preguntas = preguntaRepo.findByDificultad(dificultad, paginacion);
        return preguntas.map(DatosRespuestaPregunta::new);
    }

    public Page<DatosRespuestaPregunta> listarPreguntasPorTipo(Tipo tipo, Pageable paginacion) {
        Page<Pregunta> preguntas = preguntaRepo.findByTipo(tipo, paginacion);
        return preguntas.map(DatosRespuestaPregunta::new);
    }

    public DatosRespuestaPregunta buscarPreguntaAleatoriaPorTipo(Tipo tipo) {
        List<Pregunta> preguntas = preguntaRepo.findByTipo(tipo);

        if (preguntas.isEmpty()) {
            throw new NoSuchElementException("No se encontró una pregunta para el tipo dado");
        }
        Pregunta preguntaAleatoria = preguntas.get(new Random().nextInt(preguntas.size()));

        return new DatosRespuestaPregunta(preguntaAleatoria);
    }

    public DatosRespuestaPregunta buscarPreguntaAleatoriaPorDificultad(Dificultad dificultad) {
        List<Pregunta> preguntas = preguntaRepo.findByDificultad(dificultad);

        if (preguntas.isEmpty()) {
            throw new NoSuchElementException("No se encontró una pregunta para la dificultad dada");
        }
        Pregunta preguntaAleatoria = preguntas.get(new Random().nextInt(preguntas.size()));

        return new DatosRespuestaPregunta(preguntaAleatoria);
    }

    public EstadisticasDTO calcularEstadisticas(Long usuarioId) {
            int totalPreguntas = puntajeRepo.countByUsuarioId(usuarioId);
            int respuestasCorrectas = puntajeRepo.countCorrectasByUsuarioId(usuarioId);

            double porcentajeCorrectas = totalPreguntas > 0 ? (double) respuestasCorrectas / totalPreguntas * 100 : 0;

            return new EstadisticasDTO(totalPreguntas, respuestasCorrectas, porcentajeCorrectas);
        }

    public List<LogroDTO> obtenerLogrosPorUsuario(Long usuarioId) {
        List<LogroDTO> logros = new ArrayList<>();

        int totalRespuestasCorrectas = puntajeRepo.countCorrectasByUsuarioId(usuarioId);
        int totalRespuestas = puntajeRepo.countByUsuarioId(usuarioId);

        // Logro por respuestas correctas
        if (totalRespuestasCorrectas >= 10) {
            logros.add(new LogroDTO("¡Experto!", "Has respondido correctamente 10 preguntas."));
        }

        // Logro por porcentaje de respuestas correctas
        if (totalRespuestas > 0) {
            double porcentajeCorrectas = (double) totalRespuestasCorrectas / totalRespuestas * 100;
            if (porcentajeCorrectas >= 80) {
                logros.add(new LogroDTO("¡Maestro!", "Tienes un porcentaje de respuestas correctas del 80% o más."));
            }
        }

        // Logro por total de respuestas respondidas
        if (totalRespuestas >= 50) {
            logros.add(new LogroDTO("¡Participante Activo!", "Has respondido 50 preguntas."));
        }

        // Logro por respuestas consecutivas correctas
        int respuestasConsecutivas = puntajeRepo.countConsecutivasCorrectas(usuarioId);
        if (respuestasConsecutivas >= 5) {
            logros.add(new LogroDTO("¡Racha Ganadora!", "Has respondido 5 preguntas correctas consecutivas."));
        }

        // Logro por uso frecuente
        int diasJugados = puntajeRepo.countDiasJugados(usuarioId);
        if (diasJugados >= 7) {
            logros.add(new LogroDTO("¡Jugador Frecuente!", "Has jugado al menos 7 días en total."));
        }

        // Logro por responder preguntas de todos los tipos
        int tiposRespondidos = puntajeRepo.countTiposRespondidos(usuarioId);
        int totalTipos = Tipo.values().length; // Asumiendo que tienes un enum Tipo
        if (tiposRespondidos >= totalTipos) {
            logros.add(new LogroDTO("¡Explorador!", "Has respondido preguntas de todos los tipos disponibles."));
        }

        return logros;
    }
}

