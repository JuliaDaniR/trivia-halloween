package com.halloween.trivia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(
    @NotBlank String nombre,
    @NotBlank String email,
    @NotBlank String password
) {

        public static record DatosActualizarUsuario(
                @NotNull Long id,
                String nombre,
                String email
        ){
        }
    }
