package com.halloween.trivia.entidades;

import com.halloween.trivia.enumerador.Dificultad;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "Puntaje")
@Table(name = "puntajes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Puntaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    private Integer puntos;

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    @Temporal(TemporalType.DATE)
    private Date fecha;
}

