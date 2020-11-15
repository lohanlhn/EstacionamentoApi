package com.estacionamento.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//import com.estacionamento.api.dtos.ClienteDto;
import com.estacionamento.api.entities.Cliente;
import com.estacionamento.api.services.ClienteService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
//import com.estacionamento.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteService clienteService;

	private Cliente CriarClienteTestes() {

		Cliente cliente = new Cliente();

		cliente.setId(1);
		cliente.setTelefone("12345678910");
		cliente.setCpf("05887098082");
		

		return cliente;

	}

	@Test
	@WithMockUser
	public void testBuscarPorIdSucesso() throws Exception {

		Cliente cliente = CriarClienteTestes();

		BDDMockito.given(clienteService.buscarPorId(Mockito.anyInt()))
			.willReturn(Optional.of(cliente));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
			.andExpect(jsonPath("$.dados.telefone").value(cliente.getTelefone()))
			.andExpect(jsonPath("$.dados.cpf").value(cliente.getCpf()))
			.andExpect(jsonPath("$.erros").isEmpty());

	}

	@Test
	@WithMockUser
	public void testBuscarPorIdInconsistencia() throws Exception {

		BDDMockito.given(clienteService.buscarPorId((Mockito.anyInt())))
			.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}
	
	@Test
	@WithMockUser
	public void testBuscarPorCpfSucesso() throws Exception {

		Cliente cliente = CriarClienteTestes();

		BDDMockito.given(clienteService.buscarPorCPF(Mockito.anyString()))
			.willReturn(Optional.of(cliente));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/cpf/05887098082")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
			.andExpect(jsonPath("$.dados.telefone").value(cliente.getTelefone()))
			.andExpect(jsonPath("$.dados.cpf").value(cliente.getCpf()))
			.andExpect(jsonPath("$.erros").isEmpty());

	}

	@Test
	@WithMockUser
	public void testBuscarPorCpfInconsistencia() throws Exception {

		BDDMockito.given(clienteService.buscarPorCPF(Mockito.anyString()))
			.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/cpf/05887098082")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}

	/*@Test
	@WithMockUser
	public void testSalvarSucesso() throws Exception {

		Cliente cliente = CriarClienteTestes();
		ClienteDto objEntrada = ConversaoUtils.Converter(cliente);

		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class)))
			.willReturn(cliente);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
			.andExpect(jsonPath("$.dados.nome").value(objEntrada.getNome()))
			.andExpect(jsonPath("$.dados.cpf").value(objEntrada.getCpf()))
			.andExpect(jsonPath("$.dados.uf").value(objEntrada.getUf()))
			.andExpect(jsonPath("$.erros").isEmpty());

	}
	
	@Test
	@WithMockUser
	public void testSalvarInconsistencia() throws Exception {

		Cliente cliente = CriarClienteTestes();
		ClienteDto objEntrada = ConversaoUtils.Converter(cliente);

		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class)))
			.willThrow(new ConsistenciaException("Teste inconsistência."));
		
		mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Teste inconsistência."));

	}
	
	
	@Test
	@WithMockUser
	public void testSalvarCpfInvalido() throws Exception {

		ClienteDto objEntrada = new ClienteDto();

		objEntrada.setTelefone("43999999999");
		objEntrada.setCpf("123456789011");
		
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("CPF inválido."));

	}*/
	
		
}