package com.halloween.trivia.enumerador;

public enum Tipo {

    LEYENDA("Leyenda"),
    MITO("Mito"),
    HISTORIA("Historia");

    private String nombre;

    Tipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}