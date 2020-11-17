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

import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.dtos.VagaOcupadaDto;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.response.Response;
import com.estacionamento.api.services.VagaOcupadaService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.sun.el.parser.ParseException;

@RestController
@RequestMapping("api/vagaOcupada")
@CrossOrigin(origins = "*")
public class VagaOcupadaController {
	
	private static final Logger log = LoggerFactory.getLogger(VagaOcupadaController.class);
	
	@Autowired
	private VagaOcupadaService vagaOcupadaService;
	
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public ResponseEntity<Response<VagaOcupadaDto>> buscarPorId (@PathVariable("id") int id) {
		
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
	
	@PostMapping
	public ResponseEntity<Response<VagaOcupadaDto>> salvarCliente(@Valid @RequestBody
			VagaOcupadaDto vagaOcupadaDto, BindingResult result) throws ParseException, java.text.ParseException {
		
		Response<VagaOcupadaDto> response = new Response<VagaOcupadaDto>();
		
		try {
			log.info("Controller: salvando VagaOcupada: {}", vagaOcupadaDto.toString());
			
			if(result.hasErrors()) {
				for (int i = 0; i< result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			VagaOcupada vagaOcupada = ConversaoUtils.converterVagaOcupadaDto(vagaOcupadaDto);
			
			response.setDados(ConversaoUtils.converterVagaOcupada(this.vagaOcupadaService.salvar(vagaOcupada)));
			
			return ResponseEntity.ok(response);
			
		} catch (ConsistenciaException e){
			
			log.info("Controller: Inconsistêcia de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
			
		} catch (Exception e) {
			
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
			
		}
		
		
	}
	
	@PostMapping(value = "/OcuparVaga")
	public  ResponseEntity<Response<VagaDto>> ocuparVaga(@Valid @RequestBody VagaDto vagaDto,
			BindingResult result) throws ParseException{
		
		Response<VagaDto> response = new Response<VagaDto>();
		
		try {
			log.info("Controller: Alterando disponibilidade da vaga: {}", vagaDto.getId());
			
			if(result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			Vaga vaga = ConversaoUtils.converterVagaDto(vagaDto);
			
			this.vagaOcupadaService.ocuparVaga(vaga);
			response.setDados(vagaDto);
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
	@PostMapping(value = "/DesocuparVaga")
	public  ResponseEntity<Response<VagaDto>> desocuparVaga(@Valid @RequestBody VagaDto vagaDto,
			BindingResult result) throws ParseException{
		
		Response<VagaDto> response = new Response<VagaDto>();
		
		try {
			log.info("Controller: Alterando disponibilidade da vaga: {}", vagaDto.getId());
			
			if(result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			Vaga vaga = ConversaoUtils.converterVagaDto(vagaDto);
			
			this.vagaOcupadaService.desocuparVaga(vaga);
			response.setDados(vagaDto);
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
	
	@GetMapping(value = "Valor/{idVagaOcupada}")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public ResponseEntity<Response<VagaOcupadaDto>> VerValor(@PathVariable("idVagaOcupada") int id) throws java.text.ParseException{
		
		Response<VagaOcupadaDto> response = new Response<VagaOcupadaDto>();
		
		try {
			log.info("Controller: Buscando Valor");
			
			Optional<VagaOcupada> vagaOcupada = Optional.empty();
			vagaOcupada.get().setValor(vagaOcupadaService.VerValor(id));
			
			response.setDados(ConversaoUtils.converterVagaOcupada(vagaOcupada.get()));
			
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
	
	@GetMapping(value = "/VagasOcupadasNaoPagas")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public ResponseEntity<Response<List<VagaOcupadaDto>>> buscarVagasNaoPagas () {
		
		Response<List<VagaOcupadaDto>> response = new Response<List<VagaOcupadaDto>>();
		
		try {
			log.info("Controller: buscando vagasOcupadas não pagas");
			
			Optional<List<VagaOcupada>> vagasOcupadas = vagaOcupadaService.buscarVagasNaoPagas();
			response.setDados(ConversaoUtils.converterListaVagaOcupada(vagasOcupadas));
			
			return ResponseEntity.ok(response);
			
		} catch (ConsistenciaException e) {
			
			log.info("Controller: Inconsistência de dados: {} ", e.getMensagem());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {]", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@GetMapping(value = "/BuscarTodas")
	@PreAuthorize("hasAnyRole('ADM_USUARIO')")
	public ResponseEntity<Response<List<VagaOcupadaDto>>> buscarTodasVagas () {
		
		Response<List<VagaOcupadaDto>> response = new Response<List<VagaOcupadaDto>>();
		
		try {
			log.info("Controller: buscando vagasOcupadas não pagas");
			
			Optional<List<VagaOcupada>> vagasOcupadas = vagaOcupadaService.buscarTodasVagaOcupadas();
			response.setDados(ConversaoUtils.converterListaVagaOcupada(vagasOcupadas));
			
			return ResponseEntity.ok(response);
			
		} catch (ConsistenciaException e) {
			
			log.info("Controller: Inconsistência de dados: {} ", e.getMensagem());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {]", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping(value = "/ConfirmarPagamento")
	public  ResponseEntity<Response<VagaOcupadaDto>> ConfirmarPagamento(@Valid @RequestBody VagaOcupadaDto vagaOcupadaDto,
			BindingResult result) throws ParseException{
		
		Response<VagaOcupadaDto> response = new Response<VagaOcupadaDto>();
		
		try {
			log.info("Controller: Confirmando Pagamento da vagaOcupada de id: {}", vagaOcupadaDto.getId());
			
			if(result.hasErrors()) {
				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}
				
				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);
			}
			
			VagaOcupada vagaOcupada = ConversaoUtils.converterVagaOcupadaDto(vagaOcupadaDto);
			
			this.vagaOcupadaService.confirmarPaga(vagaOcupada.getId());;
			response.setDados(vagaOcupadaDto);
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
