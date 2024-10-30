package com.halloween.trivia.repository;

import com.halloween.trivia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public UserDetails findByCorreoElectronico(String username);
}
