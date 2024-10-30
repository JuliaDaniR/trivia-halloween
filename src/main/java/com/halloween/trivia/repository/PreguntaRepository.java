package com.halloween.trivia.repository;

import com.halloween.trivia.dto.DatosRespuestaPregunta;
import com.halloween.trivia.entidades.Pregunta;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    @Query("SELECT p FROM Pregunta p WHERE p.dificultad = :dificultad AND p.tipo = :tipo")
    List<Pregunta> findByDificultadAndTipo(@Param("dificultad") Dificultad dificultad, @Param("tipo") Tipo tipo);

    Page<Pregunta> findByDificultad(Dificultad dificultad, Pageable paginacion);

    Page<Pregunta> findByTipo(Tipo tipo, Pageable paginacion);

    @Query("SELECT p FROM Pregunta p WHERE p.dificultad = :dificultad")
    List<Pregunta> findByDificultad(@Param("dificultad") Dificultad dificultad);

    @Query("SELECT p FROM Pregunta p WHERE p.tipo = :tipo")
    List<Pregunta> findByTipo(@Param("tipo") Tipo tipo);
}
