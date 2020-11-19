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

import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.services.VagaService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VagaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VagaService vagaService;

	private Vaga criarVagaTeste() {
		Vaga vaga = new Vaga();

		vaga.setId(1);
		vaga.setDisponivel(true);
		vaga.setCodVaga("A01");

		return vaga;
	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(roles = "FUNC") public void testBuscarTodasAsVagas() throws
	 * Exception{ Vaga vaga = criarVagaTeste();
	 * 
	 * List<Vaga> lstVagas = new ArrayList<>(); lstVagas.add(vaga);
	 * 
	 * BDDMockito.given(vagaService.buscarTodasAsVagas())
	 * .willReturn(Optional.of(lstVagas));
	 * 
	 * mvc.perform(MockMvcRequestBuilders.get("/api/vaga/todas")
	 * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk())
	 * .andExpect(jsonPath("$.dados[0].id").value(vaga.getId()))
	 * .andExpect(jsonPath("$.dados[0].codVaga").value(vaga.getCodVaga()))
	 * .andExpect(jsonPath("$.dados[0].disponivel").value(vaga.getDisponivel()))
	 * .andExpect(jsonPath("$.erros").isEmpty()); }
	 */

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarSucesso() throws Exception {
		Vaga vaga = criarVagaTeste();
		VagaDto objEntrada = ConversaoUtils.converterVaga(vaga);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(vagaService.salvar(Mockito.any(Vaga.class))).willReturn(vaga);

		mvc.perform(MockMvcRequestBuilders.post("/api/vaga").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.codVaga").value(objEntrada.getCodVaga()))
				.andExpect(jsonPath("$.dados.disponivel").value(objEntrada.getDisponivel()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarInconsistencia() throws Exception {
		Vaga vaga = criarVagaTeste();
		VagaDto objEntrada = ConversaoUtils.converterVaga(vaga);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(vagaService.salvar(Mockito.any(Vaga.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/vaga").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarCodVagaInsuficiente() throws Exception {
		Vaga vaga = criarVagaTeste();
		vaga.setCodVaga("A");
		VagaDto objEntrada = ConversaoUtils.converterVaga(vaga);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/vaga").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("codVaga deve ter 3 caracteres"));
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarCodVagaExcedente() throws Exception {
		Vaga vaga = criarVagaTeste();
		vaga.setCodVaga("A012");
		VagaDto objEntrada = ConversaoUtils.converterVaga(vaga);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/vaga").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("codVaga deve ter 3 caracteres"));
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testExcluirPorIdSucesso() throws Exception {

		mvc.perform(MockMvcRequestBuilders.delete("/api/vaga/excluir/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.dados").value("Vaga de id: 1 excluído com sucesso"))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
}
