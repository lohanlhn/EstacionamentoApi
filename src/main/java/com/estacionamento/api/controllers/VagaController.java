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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.VagaService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;

@RestController
@RequestMapping("/api/vaga")
@CrossOrigin(origins = "*")
public class VagaController {

	private static final Logger log = LoggerFactory.getLogger(ValoresController.class);

	@Autowired
	private VagaService vagaService;

	/**
	 * Retorna os dados de todas as vagas cadastradas
	 * 
	 * @return Lista de vagas cadastradas
	 */
	@GetMapping(value = "/todas")
	@PreAuthorize("hasAnyRole('FUNC')")
	public ResponseEntity<Response<List<VagaDto>>> buscarTodosAsVagas() {

		Response<List<VagaDto>> response = new Response<List<VagaDto>>();

		try {
			log.info("Controller: buscando todas as vagas");

			Optional<List<Vaga>> vagas = vagaService.buscarTodasAsVagas();

			response.setDados(ConversaoUtils.ConverterListaVaga(vagas.get()));

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
	 * Persiste uma vaga na base.
	 *
	 * @param Dados de entrada da vaga
	 * @return Dados da vaga persistida
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<VagaDto>> salvar(@Valid @RequestBody VagaDto vagaDto, BindingResult result) {
		Response<VagaDto> response = new Response<VagaDto>();

		try {
			log.info("Controller: salvando a vaga: {}", vagaDto.toString());

			if (result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				log.info("Controller: os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}

			Vaga vaga = this.vagaService.salvar(ConversaoUtils.ConverterVagaDto(vagaDto));
			response.setDados(ConversaoUtils.ConverterVaga(vaga));
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
	 * Exclui uma vaga a partir do id informado no parâmtero
	 * 
	 * @param id da vaga a ser excluída
	 * @return Sucesso/erro
	 */
	@DeleteMapping(value = "excluir/{id}")
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<String>> excluirPorId(@PathVariable("id") int id) {

		Response<String> response = new Response<String>();

		try {
			log.info("Controller: excluíndo valor de ID: {}", id);

			vagaService.excluirPorId(id);

			response.setDados("Valor de id: " + id + "excluído com sucesso");

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
