package com.halloween.trivia.dto;

import com.halloween.trivia.entidades.Pregunta;
import com.halloween.trivia.entidades.Respuesta;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;

import java.util.List;
import java.util.stream.Collectors;

public record DatosRespuestaPregunta(
        Long id,
        String texto,
        Dificultad dificultad,
        Tipo tipo,
        List<RespuestaDTO> opciones,
        String breveHistoria
) {
    public DatosRespuestaPregunta(Pregunta pregunta) {
        this(
                pregunta.getId(),
                pregunta.getTexto(),
                pregunta.getDificultad(),
                pregunta.getTipo(),
                pregunta.getOpciones().stream()
                        .map(RespuestaDTO::new)
                        .collect(Collectors.toList()),
                pregunta.getBreveHistoria()
        );
    }
}
