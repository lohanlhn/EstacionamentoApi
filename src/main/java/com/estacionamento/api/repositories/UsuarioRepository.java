package com.estacionamento.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	@Transactional(readOnly = true)
   	Optional<Usuario> findByEmail(String email);
   	
   	@Transactional
   	@Modifying(clearAutomatically = true)
   	@Query("UPDATE Usuario SET senha = :novasenha WHERE email = :emailUsuario")
   	void alterarSenhaUsuario(@Param("novasenha") String novasenha, @Param("emailUsuario") String emailUsuario);
   	

}
