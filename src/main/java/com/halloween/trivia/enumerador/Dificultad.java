package com.halloween.trivia.enumerador;

public enum Dificultad {

    FACIL("Fácil"),
    MEDIO("Medio"),
    DIFICIL("Difícil");

    private String nombre;

    Dificultad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
