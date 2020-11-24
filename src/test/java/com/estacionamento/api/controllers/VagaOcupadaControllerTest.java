package com.estacionamento.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
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


import com.estacionamento.api.dtos.VagaOcupadaDto;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.services.VagaOcupadaService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class VagaOcupadaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VagaOcupadaService vagaOcupadaService;

	private VagaOcupada criarVagaOcupadaTeste() {
		
		VagaOcupada vagaOcupada = new VagaOcupada();
		
		vagaOcupada.setId(1);
		vagaOcupada.setValor(10);
		vagaOcupada.setHoraEntrada(new Date());
		vagaOcupada.setHoraSaida(null);
		vagaOcupada.setPaga(true);
		vagaOcupada.setVaga(new Vaga());
		vagaOcupada.getVaga().setId(1);
		vagaOcupada.setVeiculo(new Veiculo());
		vagaOcupada.getVeiculo().setId(1);

		return vagaOcupada;
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarSucesso() throws Exception {
		
		VagaOcupada vagaOcupada = criarVagaOcupadaTeste();
		VagaOcupadaDto objEntrada = ConversaoUtils.converterVagaOcupada(vagaOcupada);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(vagaOcupadaService.salvar(Mockito.any(VagaOcupada.class)))
		.willReturn(vagaOcupada);

		mvc.perform(MockMvcRequestBuilders.post("/api/vagaOcupada")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.horaEntrada").value(objEntrada.getHoraEntrada()))
				.andExpect(jsonPath("$.dados.horaSaida").value(objEntrada.getHoraSaida()))
				.andExpect(jsonPath("$.dados.paga").value(objEntrada.getPaga()))
				.andExpect(jsonPath("$.dados.valor").value(objEntrada.getValor()))
				.andExpect(jsonPath("$.dados.vagaId").value(objEntrada.getVaga()))
				.andExpect(jsonPath("$.dados.veiculoId").value(objEntrada.getVeiculo()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarInconsistencia() throws Exception {
		VagaOcupada vagaOcupada = criarVagaOcupadaTeste();
		VagaOcupadaDto objEntrada = ConversaoUtils.converterVagaOcupada(vagaOcupada);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(vagaOcupadaService.salvar(Mockito.any(VagaOcupada.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/vagaOcupada").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	
}
