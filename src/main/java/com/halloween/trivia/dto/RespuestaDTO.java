package com.halloween.trivia.dto;

import com.halloween.trivia.entidades.Respuesta;

public record RespuestaDTO(
        Long id,
        String texto,
        boolean esCorrecta
) {
    public RespuestaDTO(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getTexto(),
                respuesta.isEsCorrecta()
        );
    }
}
