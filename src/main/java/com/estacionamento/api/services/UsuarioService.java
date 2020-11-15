package com.estacionamento.api.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamento.api.entities.Cliente;
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

	@Autowired
	private ClienteService clienteService;

	

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

		log.info("Service: buscando um usuário com o id: {}", email);

		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", email);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", email);
		}

		return usuario;

	}

	public Optional<Usuario> verificarCredenciais(String email) throws ConsistenciaException {

		log.info("Service: criando credenciais para o usuário de email: '{}'", email);

		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário ativo com email: {} foi encontrado", email);
			throw new ConsistenciaException("Nenhum usuário ativo com email: {} foi encontrado", email);
		}

		usuario.get().setRegras(
				usuario.get().getRegras().stream().filter(r -> r.getAtivo() == true).collect(Collectors.toList()));

		return usuario;

	}

	@Transactional
	public Usuario salvarCliente(Usuario usuario, String telefone, String cpf) throws ConsistenciaException {

		log.info("Service: salvando o usuario: {}", usuario);

		if (usuario.getId() > 0)
			buscarPorId(usuario.getId());

		usuario.setSenha(SenhaUtils.gerarHash(usuario.getSenha()));

		try {
			usuarioRepository.save(usuario);

			Cliente cliente = new Cliente();

			cliente.setTelefone(telefone);
			cliente.setCpf(cpf);
			cliente.setUsuario(usuarioRepository.findByEmail(usuario.getEmail()).get());

			clienteService.salvar(cliente);

			return usuario;

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O email '{}' já está cadastrado para outro usuário", usuario.getEmail());
			throw new ConsistenciaException("O email '{}' já está cadastrado para outro usuário", usuario.getEmail());

		}

	}

	public Usuario salvarFuncionario(Usuario usuario) throws ConsistenciaException {

		log.info("Service: salvando o usuario: {}", usuario);

		// Se foi informando ID na DTO, é porque trata-se de uma ALTERAÇÃO
		if (usuario.getId() > 0) {

			// Verificar se o ID existe na base
			Optional<Usuario> usr = buscarPorId(usuario.getId());

			// Setando a senha do objeto usuário com a mesma senha encontarda na base.
			// Se não fizermos isso, a senha fica em branco.
			usuario.setSenha(usr.get().getSenha());

		}

		// Carregando as regras definidas para o usuário, caso existam
//		if (usuario.getRegras() != null) {
//
//			List<Regra> aux = new ArrayList<Regra>(usuario.getRegras().size());
//			
//			
//
//			for (Regra regra : usuario.getRegras()) {
//
//				Optional<Regra> rg = Optional.ofNullable(regraReprository.findByNome(regra.getNome()));
//
//				if (rg.isPresent()) {
//					aux.add(rg.get());
//				} else {
//
//					log.info("A regra '{}' não existe", regra.getNome());
//					throw new ConsistenciaException("A regra '{}' não existe", regra.getNome());
//
//				}
//
//			}
//
//			usuario.setRegras(aux);
//
//		}
		// Verifica se o tipo de usuario está correto
		if (!usuario.getTipo().equals("F")) {
			log.info("O tipo deve ser F");
			throw new ConsistenciaException("O tipo deve ser F");
		}
		
		Regra regra = regraReprository.findByNome("ROLE_FUNC");
		usuario.getRegras().add(regra);
		

		try {

			usuarioRepository.save(usuario);

			return usuario;

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O email '{}' já está cadastrado para outro usuário", usuario.getEmail());
			throw new ConsistenciaException("O email '{}' já está cadastrado para outro usuário", usuario.getEmail());

		}

	}
}
