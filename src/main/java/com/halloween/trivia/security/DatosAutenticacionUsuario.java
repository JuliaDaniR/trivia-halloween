package com.halloween.trivia.security;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutenticacionUsuario(String email ,
                                        @JsonAlias({"password","contraseña"})String clave) {
}
