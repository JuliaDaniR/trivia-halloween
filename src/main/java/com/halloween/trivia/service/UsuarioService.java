package com.halloween.trivia.service;

import com.halloween.trivia.dto.DatosRegistroUsuario;
import com.halloween.trivia.entidades.Usuario;
import com.halloween.trivia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario registrarUsuario(DatosRegistroUsuario datosRegistroUsuario) {

        Usuario usuario = new Usuario(datosRegistroUsuario);
        return usuarioRepo.save(usuario);
    }

    public Usuario obtenerPorCorreoElectronico(String username) {
        return (Usuario) usuarioRepo.findByCorreoElectronico(username);
    }
}
