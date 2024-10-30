package com.halloween.trivia.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosRespuestaUsuario(

        @NotBlank
        String nombre,
        @NotBlank
        String email
) {
}