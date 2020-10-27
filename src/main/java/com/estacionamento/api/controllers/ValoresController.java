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

import com.estacionamento.api.dtos.ValoresDto;
import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.ValoresService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;

@RestController
@RequestMapping("/api/valores")
@CrossOrigin(origins = "*")
public class ValoresController {

	private static final Logger log = LoggerFactory.getLogger(ValoresController.class);

	@Autowired
	private ValoresService valoresService;

	/**
	 * Retorna os dados de todos os valores cadastrados
	 *
	 * @return Lista de valores cadastrados
	 */
	@GetMapping(value = "/todos")
	@PreAuthorize("hasAnyRole('FUNC')")
	public ResponseEntity<Response<List<ValoresDto>>> buscarTodosOsValores() {

		Response<List<ValoresDto>> response = new Response<List<ValoresDto>>();

		try {
			log.info("Controller: buscando todos os valores");

			Optional<List<Valores>> valores = valoresService.buscarTodosOsValores();

			response.setDados(ConversaoUtils.ConverterListaValores(valores.get()));

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
	 * Persiste um valor na base.
	 *
	 * @param Dados de entrada do valor
	 * @return Dados do valor persistido
	 */
	@PostMapping
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<ValoresDto>> salvar(@Valid @RequestBody ValoresDto valoresDto, BindingResult result) {
		Response<ValoresDto> response = new Response<ValoresDto>();

		try {
			log.info("Controller: salvando o valor: {}", valoresDto.toString());

			if(result.hasErrors()) {
				for(int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				log.info("Controller: os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			Valores valores = this.valoresService.salvar(ConversaoUtils.ConverterValoresDto(valoresDto));
			response.setDados(ConversaoUtils.ConverterValores(valores));
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
	 * Exclui um valor a partir do id informado no parâmtero
	 * 
	 * @param id do valor a ser excluído
	 * @return Sucesso/erro
	 */
	@DeleteMapping(value = "excluir/{id}")
	@PreAuthorize("hasAnyRole('ADM')")
	public ResponseEntity<Response<String>> excluirPorId(@PathVariable("id") int id){
		
		Response<String> response = new Response<String>();
		
		try {
			log.info("Controller: excluíndo valor de ID: {}", id);
			
			valoresService.excluirPorId(id);
			
			response.setDados("Valor de id: " + id + "excluído com sucesso");
			
			return ResponseEntity.ok(response);
		}catch (ConsistenciaException e) {

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
