package com.estacionamento.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Regra;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.repositories.RegraRepository;
import com.estacionamento.api.repositories.UsuarioRepository;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.SenhaUtils;

@Service
public class UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RegraRepository regraReprository;

		

	public Optional<Usuario> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando um usuário com o id: {}", id);

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", id);
		}

		return usuario;

	}
	
	public Optional<Usuario> buscarPorEmail(String email) throws ConsistenciaException {

		log.info("Service: buscando um usuário com o email: {}", email);

		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com email: {} foi encontrado", email);
			throw new ConsistenciaException("Nenhum usuário com email: {} foi encontrado", email);
		}

		return usuario;

	}

	public Optional<List<Usuario>> buscarTodosOsFuncionarios() throws ConsistenciaException {
		log.info("Service: buscando todas os funcionarios");

		Optional<List<Usuario>> funcionarios = usuarioRepository.findFuncionarios();

		if (!funcionarios.isPresent() || funcionarios.get().size() < 1) {
			log.info("Service: nenhum funcionario foi encontrado");
			throw new ConsistenciaException("Nenhum funcionario foi encontrado");
		}
		return funcionarios;
	}

	public Optional<Usuario> verificarCredenciais(String email) throws ConsistenciaException {

		log.info("Service: criando credenciais para o usuário de email: '{}'", email);

		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com email: {} foi encontrado", email);
			throw new ConsistenciaException("Nenhum usuário com email: {} foi encontrado", email);
		}

		usuario.get().setRegras(
				usuario.get().getRegras().stream().filter(r -> r.getAtivo() == true).collect(Collectors.toList()));

		return usuario;

	}
	
	public Usuario salvarCliente(Usuario usuario) throws ConsistenciaException {

		log.info("Service: salvando o usuario: {}", usuario);

		if (usuario.getId() > 0)
			buscarPorId(usuario.getId());

		usuario.setSenha(SenhaUtils.gerarHash(usuario.getSenha()));
		
		usuario.setTipo("C");
		
		try {
			usuarioRepository.save(usuario);

			return usuario;

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O email '{}' já está cadastrado para outro usuário", usuario.getEmail());
			throw new ConsistenciaException("O email '{}' já está cadastrado para outro usuário", usuario.getEmail());

		}

	}

	public Usuario salvarFuncionario(Usuario usuario) throws ConsistenciaException {

		log.info("Service: salvando o usuario: {}", usuario);

		if (usuario.getId() > 0)
			buscarPorId(usuario.getId());

		usuario.setSenha(SenhaUtils.gerarHash(usuario.getSenha()));

		usuario.setTipo("F");
		
		List<Regra> aux = new ArrayList<Regra>();		
		Regra regra = regraReprository.findByNome("ROLE_FUNC");
		aux.add(regra);
		usuario.setRegras(aux);
		

		try {

			usuarioRepository.save(usuario);

			return usuario;

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O email '{}' já está cadastrado para outro usuário", usuario.getEmail());
			throw new ConsistenciaException("O email '{}' já está cadastrado para outro usuário", usuario.getEmail());

		}

	}
	
	public void excluirPorId(int id) throws ConsistenciaException {
		 
     	log.info("Service: excluíndo o usuario de id: {}", id);

     	buscarPorId(id);
     	
     	usuarioRepository.deletarRegraDoUsuario(id);
     	usuarioRepository.deleteById(id);

	}
}
