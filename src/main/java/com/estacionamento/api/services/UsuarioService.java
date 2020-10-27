package com.estacionamento.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Cliente;
import com.estacionamento.api.entities.Regra;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.repositories.ClienteRepository;
import com.estacionamento.api.repositories.RegraRepository;
import com.estacionamento.api.repositories.UsuarioRepository;
import com.estacionamento.api.security.utils.JwtTokenUtil;
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
	private ClienteRepository clienteReprository;
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public Optional<Usuario> buscarPorId(int id) throws ConsistenciaException {

		log.info("Service: buscando um usuário com o id: {}", id);

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum usuário com id: {} foi encontrado", id);
		}

		return usuario;

	}

	public Optional<Usuario> verificarCredenciais(String email) throws ConsistenciaException {

		log.info("Service: criando credenciais para o usuário de email: '{}'", email);

		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByEmail(email));

		if (!usuario.isPresent()) {
			log.info("Service: Nenhum usuário ativo com email: {} foi encontrado", email);
			throw new ConsistenciaException("Nenhum usuário ativo com email: {} foi encontrado", email);
		}

		usuario.get().setRegras(
				usuario.get().getRegras().stream().filter(r -> r.getAtivo() == true).collect(Collectors.toList()));

		return usuario;

	}

	public Usuario salvarCliente(Usuario usuario) throws ConsistenciaException {

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
		if (usuario.getRegras() != null) {

			List<Regra> aux = new ArrayList<Regra>(usuario.getRegras().size());

			for (Regra regra : usuario.getRegras()) {

				Optional<Regra> rg = Optional.ofNullable(regraReprository.findByNome(regra.getNome()));

				if (rg.isPresent()) {
					aux.add(rg.get());
				} else {

					log.info("A regra '{}' não existe", regra.getNome());
					throw new ConsistenciaException("A regra '{}' não existe", regra.getNome());

				}

			}

			usuario.setRegras(aux);

		}

		try {
			
			usuarioRepository.save(usuario);
		
			Cliente cliente = new Cliente();
			
			cliente.setUsuario(usuarioRepository.findByEmail(usuario.getEmail()));
			
			clienteReprository.save(cliente);
			
			return usuario;

		} catch (DataIntegrityViolationException e) {

			log.info("Service: O email '{}' já está cadastrado para outro usuário", usuario.getEmail());
			throw new ConsistenciaException("O email '{}' já está cadastrado para outro usuário", usuario.getEmail());

		}

	}

	public void alterarSenhaUsuario(String senhaAtual, String novaSenha, int id) throws ConsistenciaException {

		log.info("Service: alterando a senha do usuário: {}", id);

		// Verificar se existe um usuário com o ID informado
		Optional<Usuario> usr = buscarPorId(id);

		// String token = request.getHeader("Authorization");
		String token = httpServletRequest.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		String username = jwtTokenUtil.getUsernameFromToken(token);

		// Verificar se o usuário do token é diferente do usuário que está sendo
		// alterado
		if (!usr.get().getEmail().equals(username)) {

			// Caso essa condição for satisfeita, o usuário do token está tentando
			// alterar a senha de outro usuário. Não podemos deixar isso acontecer.

			log.info("Service: Cpf do token diferente do email do usuário a ser alterado");
			throw new ConsistenciaException("Você não tem permissão para alterar a senha de outro usuário.");

		}

		// Verificar se a senha atual do usuário diferente da informada na entrada
		if (!SenhaUtils.compararHash(senhaAtual, usr.get().getSenha())) {

			log.info("Service: A senha atual informada não é válida");
			throw new ConsistenciaException("A senha atual informada não é válida.");

		}

		usuarioRepository.alterarSenhaUsuario(SenhaUtils.gerarHash(novaSenha), id);

	}

}
