package com.estacionamento.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.repositories.UsuarioRepository;
import com.estacionamento.api.utils.ConsistenciaException;
import java.util.Optional;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um usuario com o id: {}", id);

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", id);
		}

		return usuario;

	}

	public Usuario salvar(Usuario usuario) throws ConsistenciaException {
		log.info("Sevice: salvando o usuario: {}", usuario);

		if (usuario.getId() > 0)
			buscarPorId(usuario.getId());

		try {
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException e) {
			log.info("Service: O email: {} já está cadastrado para outro usuario", usuario.getEmail());
			throw new ConsistenciaException("O email: {} já está cadastrado para outro usuario", usuario.getEmail());
		}

	}
}
