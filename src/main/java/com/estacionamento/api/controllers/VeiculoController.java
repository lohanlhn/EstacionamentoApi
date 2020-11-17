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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamento.api.dtos.VeiculoDto;
import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.VeiculoService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.sun.el.parser.ParseException;


@RestController
@RequestMapping("/api/veiculo")
@CrossOrigin(origins = "*")
public class VeiculoController {
	
	private static final Logger log = LoggerFactory.getLogger(VeiculoController.class);
	
	@Autowired
	private VeiculoService veiculoService;
	
	@GetMapping(value = "/cliente/{usuarioId}")
	public ResponseEntity<Response<List<VeiculoDto>>> buscarPorClienteid(@PathVariable("usuarioId") int usuarioId) {
		
		Response<List<VeiculoDto>> response = new Response<List<VeiculoDto>>();
		
		try {
			log.info("Controller: buscando Veiculos do cliente de ID: {}",usuarioId);
			
			Optional<List<Veiculo>> listaVeiculo = veiculoService.buscarPorClienteId(usuarioId);
			
			response.setDados(ConversaoUtils.converterListaVeiculo(listaVeiculo));
			
			return ResponseEntity.ok(response);
			
		} catch (ConsistenciaException e) {
			
			log.info("Controller: Incosistência de dados: {]", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
			
		} catch (Exception e) {
			
			log.info("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
			
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Response<VeiculoDto>> salvar(@Valid @RequestBody VeiculoDto veiculoDto, BindingResult result) throws ParseException {
		
		Response<VeiculoDto> response = new Response<VeiculoDto>();
		
		try {
			log.info("Controller: Salvando o Veiculo: {]", veiculoDto.toString());
			
			if (result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os Campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			Veiculo veiculo = this.veiculoService.salvar(ConversaoUtils.coverterVeiculoDto(veiculoDto));
			response.setDados(ConversaoUtils.conveterVeiculo(veiculo));
			
			return ResponseEntity.ok(response);
			
		}catch (ConsistenciaException e) {
			
			log.info("Controller: Inconsistência de dados: {]", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
			
		}catch (Exception e) {
			
			log.info("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@DeleteMapping(value = "excluirVeiculo/{id}")
   	@PreAuthorize("hasAnyRole('EXCLUSAO')")
   	public ResponseEntity<Response<String>> excluirPorId(@PathVariable("id") int id){
         	
         	Response<String> response = new Response<String>();
 
         	try {
 
                	log.info("Controller: excluíndo veiculo de ID: {}", id);
 
                	veiculoService.excluirPorId(id);
 
                	response.setDados("Veiculo de id: " + id + " excluído com sucesso");
 
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
