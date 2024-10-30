package com.halloween.trivia.controller;

import com.halloween.trivia.entidades.Usuario;
import com.halloween.trivia.security.DatosAutenticacionUsuario;
import com.halloween.trivia.security.DatosJWTtoken;
import com.halloween.trivia.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTtoken> autenticarUsuario(@RequestBody DatosAutenticacionUsuario datosAutenticacionUsuario) {
        System.out.println("Intentando autenticar usuario: " + datosAutenticacionUsuario.email());
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.clave());
        Authentication usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
        String tokenJWT = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        DatosJWTtoken response = new DatosJWTtoken(tokenJWT, ((Usuario) usuarioAutenticado.getPrincipal()).getNombre());
        return ResponseEntity.ok(response);
    }

}

