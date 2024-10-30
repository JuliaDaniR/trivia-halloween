package com.halloween.trivia.dto;

import com.halloween.trivia.entidades.Respuesta;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DatosRegistroPregunta(

        @NotBlank String texto,
        @NotBlank Dificultad dificultad,
        @NotBlank Tipo tipo,
        @NotBlank @Valid List<DatosRespuesta> opciones,
        @NotBlank String breveHistoria
) {
    public static void validarOpcionesPorDificultad(Dificultad dificultad, List<DatosRespuesta> opciones) {
        int numeroRespuestasEsperadas;

        switch (dificultad) {
            case FACIL:
                numeroRespuestasEsperadas = 3;
                break;
            case MEDIO:
                numeroRespuestasEsperadas = 4;
                break;
            case DIFICIL:
                numeroRespuestasEsperadas = 5;
                break;
            default:
                throw new IllegalArgumentException("Dificultad no válida");
        }

        if (opciones.size() != numeroRespuestasEsperadas) {
            throw new IllegalArgumentException("El número de opciones de respuestas debe ser " + numeroRespuestasEsperadas + " para la dificultad " + dificultad);
        }
    }

    public record DatosRespuesta(
            @NotBlank String texto,
            boolean esCorrecta
    ) {}
    public static record DatosActualizarPregunta(
        @NotNull Long id,
        String texto,
        Dificultad dificultad,
        Tipo tipo,
        List<Respuesta> opciones,
        String breveHistoria
    ){
    }
}
