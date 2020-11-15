package com.estacionamento.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamento.api.dtos.UsuarioDto;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.UsuarioService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Retorna os dados de um usuario a partir do id informado
	 *
	 * @param Id do usuário
	 * @return Dados do usuário
	 */
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<UsuarioDto>> buscarPorId(@PathVariable("id") int id) {

		Response<UsuarioDto> response = new Response<UsuarioDto>();

		try {

			log.info("Controller: buscando usuario com id: {}", id);

			Optional<Usuario> usuario = usuarioService.buscarPorId(id);

			response.setDados(ConversaoUtils.ConverterUsuario(usuario.get()));

			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {

			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);

		}

	}
	
	/**
	 * Retorna os dados de todos os funcionarios cadastrados
	 * 
	 * @return Lista de vagas cadastradas
	 */
	@GetMapping(value = "/funcionario/todos")
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<List<UsuarioDto>>> buscarTodosOsFuncionarios() {

		Response<List<UsuarioDto>> response = new Response<List<UsuarioDto>>();

		try {
			log.info("Controller: buscando todas os funcionarios");

			Optional<List<Usuario>> funcionarios = usuarioService.buscarTodosOsFuncionarios();

			response.setDados(ConversaoUtils.ConverterListaUsuario(funcionarios.get()));

			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);

		}
	}

	/**
	 * Persiste um usuário cliente na base.
	 *
	 * @param Dados de entrada do usuário
	 * @return Dados do usuario persistido
	 */
	@PostMapping
	public ResponseEntity<Response<UsuarioDto>> salvarCliente(@Valid @RequestBody UsuarioDto usuarioDto,
			BindingResult result) {

		Response<UsuarioDto> response = new Response<UsuarioDto>();

		try {

			log.info("Controller: salvando o usuario: {}", usuarioDto.toString());

			// Verificando se todos os campos da DTO foram preenchidos
			if (result.hasErrors()) {

				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			// Converte o objeto usuarioDto para um objeto do tipo Usuario (entidade)
			Usuario usuario = ConversaoUtils.ConverterUsuarioDto(usuarioDto);

			// Salvando o usuário
			response.setDados(ConversaoUtils.ConverterUsuario(this.usuarioService.salvarCliente(usuario, usuarioDto.getTelefone(), usuarioDto.getCpf())));
			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {

			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);

		}

	}
		
	/**
	 * Persiste um usuário funcionario na base.
	 *
	 * @param Dados de entrada do usuário
	 * @return Dados do usuario persistido
	 */
	@PostMapping(value = "/funcionario")
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<UsuarioDto>> salvarFuncionario(@Valid @RequestBody UsuarioDto usuarioDto,
			BindingResult result) {

		Response<UsuarioDto> response = new Response<UsuarioDto>();

		try {

			log.info("Controller: salvando o usuario: {}", usuarioDto.toString());

			// Verificando se todos os campos da DTO foram preenchidos
			if (result.hasErrors()) {

				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			// Converte o objeto usuarioDto para um objeto do tipo Usuario (entidade)
			Usuario usuario = ConversaoUtils.ConverterUsuarioDto(usuarioDto);

			// Salvando o usuário
			response.setDados(ConversaoUtils.ConverterUsuario(this.usuarioService.salvarFuncionario(usuario)));
			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {

			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);

		}

	}

}
