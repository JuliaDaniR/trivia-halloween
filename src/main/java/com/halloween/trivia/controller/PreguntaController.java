package com.halloween.trivia.controller;

import com.halloween.trivia.dto.*;
import com.halloween.trivia.entidades.Usuario;
import com.halloween.trivia.enumerador.Dificultad;
import com.halloween.trivia.enumerador.Tipo;
import com.halloween.trivia.service.PreguntaService;
import com.halloween.trivia.service.PuntajeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/preguntas")
@SecurityRequirement(name = "bearer-key")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;


    @Autowired
    private PuntajeService puntajeService;


    @PostMapping
    public ResponseEntity<DatosRespuestaPregunta> registrarPregunta(
            @RequestBody @Valid DatosRegistroPregunta datosRegistroPregunta,
            UriComponentsBuilder uriComponentsBuilder) {

        DatosRespuestaPregunta datosRespuestaPregunta = preguntaService.registrarPregunta(datosRegistroPregunta);

        URI url = uriComponentsBuilder.path("/preguntas/{id}").buildAndExpand(datosRespuestaPregunta.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPregunta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaPregunta>> listarPreguntas(@PageableDefault(size = 10) Pageable paginacion) {
        Page<DatosRespuestaPregunta> respuestaPreguntas = preguntaService.listarPreguntas(paginacion);
        return ResponseEntity.ok(respuestaPreguntas);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaPregunta> actualizarPregunta(@PathVariable Long id,
                                                                     @RequestBody @Valid DatosRegistroPregunta.DatosActualizarPregunta datosActualizarPregunta) {
        DatosRespuestaPregunta datosRespuestaPregunta = preguntaService.actualizarPregunta(id, datosActualizarPregunta);
        return ResponseEntity.ok(datosRespuestaPregunta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarPregunta(@PathVariable Long id) {
        boolean eliminado = preguntaService.eliminarPregunta(id);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La Pregunta con ID " + id + " no fue encontrado.");
        }
        return ResponseEntity.ok("La Pregunta con ID " + id + " fue eliminado correctamente.");
    }

    @GetMapping("/aleatoria")
    public ResponseEntity<DatosRespuestaPregunta> obtenerPreguntaAleatoria(
            @RequestParam Dificultad dificultad,
            @RequestParam Tipo tipo) {
        DatosRespuestaPregunta pregunta = preguntaService.buscarPreguntaAleatoria(dificultad, tipo);
        return ResponseEntity.ok(pregunta);
    }

    @GetMapping("/aleatoria/tipo")
    public ResponseEntity<DatosRespuestaPregunta> obtenerPreguntaAleatoriaPorTipo(
            @RequestParam Tipo tipo) {
        DatosRespuestaPregunta pregunta = preguntaService.buscarPreguntaAleatoriaPorTipo(tipo);
        return ResponseEntity.ok(pregunta);
    }

    @GetMapping("/aleatoria/dificultad")
    public ResponseEntity<DatosRespuestaPregunta> obtenerPreguntaAleatoriaPorDificultad(
            @RequestParam Dificultad dificultad) {
        DatosRespuestaPregunta pregunta = preguntaService.buscarPreguntaAleatoriaPorDificultad(dificultad);
        return ResponseEntity.ok(pregunta);
    }

    @PostMapping("/responder")
    public ResponseEntity<String> responderPregunta(@RequestBody VerificarRespuestaUsuario datosRespuestaUsuario,
                                                    @AuthenticationPrincipal Usuario usuario) {
        boolean esCorrecta = preguntaService.jugar(datosRespuestaUsuario.preguntaId(), datosRespuestaUsuario.respuestaId(), usuario);

        String mensaje = esCorrecta ? "Respuesta correcta, puntos añadidos." : "Respuesta incorrecta.";
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/puntajes")
    public ResponseEntity<List<PuntajeUsuarioDTO>> obtenerPuntajesTodosLosUsuario() {
        List<PuntajeUsuarioDTO> puntajes = puntajeService.obtenerPuntajesPorDificultad();
        return ResponseEntity.ok(puntajes);
    }

    @GetMapping("/puntajes/{id}")
    public ResponseEntity<List<PuntajeUsuarioDTO>> obtenerPuntajesPorUsuario(@PathVariable Long id) {
        List<PuntajeUsuarioDTO> puntajes = puntajeService.obtenerPuntajesPorIdUsuario(id);

        if (puntajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(puntajes);
    }
    @GetMapping("/listar/dificultad/{dificultad}")
    public ResponseEntity<Page<DatosRespuestaPregunta>> listarPreguntasPorDificultad(
            @PageableDefault(size = 10) Pageable paginacion,
            @PathVariable Dificultad dificultad) {
        Page<DatosRespuestaPregunta> respuestaPreguntas = preguntaService.listarPreguntasPorDificultad(dificultad, paginacion);
        return ResponseEntity.ok(respuestaPreguntas);
    }

    @GetMapping("/listar/tipo/{tipo}")
    public ResponseEntity<Page<DatosRespuestaPregunta>> listarPreguntasPorTipo(
            @PageableDefault(size = 10) Pageable paginacion,
            @PathVariable Tipo tipo) {
        Page<DatosRespuestaPregunta> respuestaPreguntas = preguntaService.listarPreguntasPorTipo(tipo, paginacion);
        return ResponseEntity.ok(respuestaPreguntas);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas(@AuthenticationPrincipal Usuario usuario) {
        EstadisticasDTO estadisticas = preguntaService.calcularEstadisticas(usuario.getId());
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/logros/{usuarioId}")
    public ResponseEntity<List<LogroDTO>> obtenerLogros(@PathVariable Long usuarioId) {
        List<LogroDTO> logros = preguntaService.obtenerLogrosPorUsuario(usuarioId);
        return ResponseEntity.ok(logros);
    }
}
