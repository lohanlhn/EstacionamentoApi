package com.estacionamento.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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

import com.estacionamento.api.dtos.VeiculoDto;
import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.services.VeiculoService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class VeiculoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VeiculoService veiculoService;

	private Veiculo criarVeiculoTeste() {
		Veiculo veiculo = new Veiculo();
		
		veiculo.setId(1);
		veiculo.setMarca("Fiat");
		veiculo.setCor("Vermelho");
		veiculo.setPlaca("JYK2750");
		veiculo.setTipo("C");
		

		return veiculo;
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarSucesso() throws Exception {
		Veiculo veiculo = criarVeiculoTeste();
		VeiculoDto objEntrada = ConversaoUtils.conveterVeiculo(veiculo);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class))).willReturn(veiculo);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.marca").value(objEntrada.getMarca()))
				.andExpect(jsonPath("$.dados.cor").value(objEntrada.getCor()))
				.andExpect(jsonPath("$.dados.placa").value(objEntrada.getPlaca()))		
				.andExpect(jsonPath("$.dados.tipo").value(objEntrada.getTipo()))	
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarInconsistencia() throws Exception {
		Veiculo veiculo = criarVeiculoTeste();
		VeiculoDto objEntrada = ConversaoUtils.conveterVeiculo(veiculo);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}


	@Test
	@WithMockUser(roles = "ADM")
	public void testExcluirPorIdSucesso() throws Exception{				
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/veiculo/excluir/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados").value("Veiculo de id: 1 excluído com sucesso"))
		.andExpect(jsonPath("$.erros").isEmpty());
	}
	
}
