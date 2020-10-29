package com.estacionamento.api.controllers;


import java.util.Optional;

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

import com.estacionamento.api.dtos.VagaOcupadaDto;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.VagaOcupadaService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;

@RestController
@RequestMapping("api/vagaOcupada")
@CrossOrigin(origins = "*")
public class VagaOcupadaController {
	
	private static final Logger log = LoggerFactory.getLogger(VagaOcupadaController.class);
	
	@Autowired
	private VagaOcupadaService vagaOcupadaService;
	
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public ResponseEntity<Response<VagaOcupadaDto>> bucarPorId (@PathVariable("id") int id) {
		
		Response<VagaOcupadaDto> response = new Response<VagaOcupadaDto>();
		
		try {
			log.info("Controller: buscando usuario com id: {}", id);
			
			Optional<VagaOcupada> vagaOcupada = vagaOcupadaService.buscarPorId(id);
			response.setDados(ConversaoUtils.converterVagaOcupada(vagaOcupada.get()));
			
			return ResponseEntity.ok(response);
			
		} catch (ConsistenciaException e) {
			
			log.info("Controller: Inconsistência de dados: {} ", e.getMensagem());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.status(500).body(response);
		} catch (Exception e) {
			
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {]", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

}
