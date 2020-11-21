package com.estacionamento.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.estacionamento.api.dtos.ValoresDto;
import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.services.ValoresService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ValoresControllersTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private ValoresService valoresService;

	private Valores criarValoresTeste() {
		Valores valores = new Valores();

		valores.setId(1);
		valores.setMinutagem(15);
		valores.setValor(7.5);

		return valores;
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarSucesso() throws Exception {
		Valores valores = criarValoresTeste();
		ValoresDto objEntrada = ConversaoUtils.converterValores(valores);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(valoresService.salvar(Mockito.any(Valores.class))).willReturn(valores);

		mvc.perform(MockMvcRequestBuilders.post("/api/valores").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.minutagem").value(objEntrada.getMinutagem()))
				.andExpect(jsonPath("$.dados.valor").value(objEntrada.getValor()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarInconsistencia() throws Exception {
		Valores valores = criarValoresTeste();
		ValoresDto objEntrada = ConversaoUtils.converterValores(valores);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(valoresService.salvar(Mockito.any(Valores.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/valores").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testExcluirPorIdSucesso() throws Exception {

		mvc.perform(MockMvcRequestBuilders.delete("/api/valores/excluir/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.dados").value("Valor de id: 1 excluído com sucesso"))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

}
