package com.halloween.trivia.repository;

import com.halloween.trivia.dto.PuntajeUsuarioDTO;
import com.halloween.trivia.entidades.Puntaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntajeRepository extends JpaRepository<Puntaje, Long> {

        @Query("SELECT new com.halloween.trivia.dto.PuntajeUsuarioDTO(" +
                "p.usuario.nombre, " +
                "CAST(SUM(CASE WHEN p.dificultad = 'FACIL' THEN p.puntos ELSE 0 END) AS int), " +
                "CAST(SUM(CASE WHEN p.dificultad = 'MEDIO' THEN p.puntos ELSE 0 END) AS int), " +
                "CAST(SUM(CASE WHEN p.dificultad = 'DIFICIL' THEN p.puntos ELSE 0 END) AS int)) " +
                "FROM Puntaje p " +
                "GROUP BY p.usuario.nombre")
        List<PuntajeUsuarioDTO> obtenerPuntajePorDificultad();

        @Query("SELECT new com.halloween.trivia.dto.PuntajeUsuarioDTO(" +
                "p.usuario.nombre, " +
                "CAST(SUM(CASE WHEN p.dificultad = 'FACIL' THEN p.puntos ELSE 0 END) AS int), " +
                "CAST(SUM(CASE WHEN p.dificultad = 'MEDIO' THEN p.puntos ELSE 0 END) AS int), " +
                "CAST(SUM(CASE WHEN p.dificultad = 'DIFICIL' THEN p.puntos ELSE 0 END) AS int)) " +
                "FROM Puntaje p WHERE p.usuario.id = :usuarioId " +
                "GROUP BY p.usuario.nombre")
        List<PuntajeUsuarioDTO> obtenerPuntajesPorIdUsuario(@Param("usuarioId") Long usuarioId);

        @Query("SELECT COUNT(p) FROM Puntaje p WHERE p.usuario.id = :usuarioId")
        int countByUsuarioId(@Param("usuarioId") Long usuarioId);

        @Query("SELECT COUNT(r) FROM Respuesta r JOIN Puntaje p ON r.pregunta.id = p.pregunta.id " +
                "WHERE p.usuario.id = :usuarioId AND r.esCorrecta = true")
        int countCorrectasByUsuarioId(@Param("usuarioId") Long usuarioId);

        @Query("SELECT COUNT(DISTINCT DATE(p.fecha)) FROM Puntaje p WHERE p.usuario.id = :usuarioId")
        int countDiasJugados(@Param("usuarioId") Long usuarioId);

        @Query("SELECT COUNT(DISTINCT p.tipo) FROM Puntaje pun JOIN pun.pregunta p WHERE pun.usuario.id = :usuarioId")
        int countTiposRespondidos(@Param("usuarioId") Long usuarioId);

        @Query("SELECT COUNT(r) FROM Respuesta r JOIN Puntaje p ON r.pregunta.id = p.pregunta.id " +
                "WHERE p.usuario.id = :usuarioId AND r.esCorrecta = true " +
                "ORDER BY p.fecha DESC")
        int countConsecutivasCorrectas(@Param("usuarioId") Long usuarioId);
    }

