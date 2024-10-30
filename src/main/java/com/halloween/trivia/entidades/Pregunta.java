package com.halloween.trivia.entidades;

import com.halloween.trivia.dto.DatosRegistroPregunta;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Pregunta")
@Table(name = "preguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String texto;

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> opciones;

    @Lob
    private String breveHistoria;

    public Pregunta(DatosRegistroPregunta datosRegistroPregunta) {
        this.texto = datosRegistroPregunta.texto();
        this.dificultad = datosRegistroPregunta.dificultad();
        this.tipo = datosRegistroPregunta.tipo();
        this.breveHistoria = datosRegistroPregunta.breveHistoria();
    }

    public void actualizarDatos(DatosRegistroPregunta.DatosActualizarPregunta datosActualizarPregunta){
        if(datosActualizarPregunta.texto() != null){
            this.texto = datosActualizarPregunta.texto();
        }
        if(datosActualizarPregunta.dificultad() != null){
            this.dificultad = datosActualizarPregunta.dificultad();
        }
        if(datosActualizarPregunta.tipo() != null){
            this.tipo = datosActualizarPregunta.tipo();
        }
        if(datosActualizarPregunta.breveHistoria() != null){
            this.breveHistoria = datosActualizarPregunta.breveHistoria();
        }
        if (datosActualizarPregunta.opciones() != null) {
            this.opciones.clear();
            this.opciones.addAll(
                    datosActualizarPregunta.opciones().stream()
                            .map(datosRespuesta -> new Respuesta(null, datosRespuesta.getTexto(), datosRespuesta.isEsCorrecta(), this))
                            .collect(Collectors.toList())
            );
        }
    }
}


