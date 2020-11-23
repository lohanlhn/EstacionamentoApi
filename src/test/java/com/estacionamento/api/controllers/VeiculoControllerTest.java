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
import com.estacionamento.api.entities.Usuario;
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
		veiculo.setUsuario(new Usuario());
		veiculo.getUsuario().setId(1);
		return veiculo;
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testBuscarPorClienteIdSucesso() throws Exception {

		Veiculo veiculo = criarVeiculoTeste();
		List<Veiculo> lstVeiculo = new ArrayList<>();
		lstVeiculo.add(veiculo);

		BDDMockito.given(veiculoService.buscarPorClienteId(Mockito.anyInt()))
				.willReturn(Optional.of(lstVeiculo));

		mvc.perform(
				MockMvcRequestBuilders.get("/api/veiculo/usuario/4")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dados[0].id").value(veiculo.getId()))
				.andExpect(jsonPath("$.dados[0].marca").value(veiculo.getMarca()))
				.andExpect(jsonPath("$.dados[0].cor").value(veiculo.getCor()))
				.andExpect(jsonPath("$.dados[0].placa").value(veiculo.getPlaca()))
				.andExpect(jsonPath("$.dados[0].tipo").value(veiculo.getTipo()))
				.andExpect(jsonPath("$.dados[0].usuarioId").value(veiculo.getUsuario().getId()))
				.andExpect(jsonPath("$.erros").isEmpty());

	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testBuscarPorClienteIdInconsistencia() throws Exception {
		BDDMockito.given(veiculoService.buscarPorClienteId(Mockito.anyInt()))
				.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(
				MockMvcRequestBuilders.get("/api/veiculo/usuario/4").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.erros").value("Teste inconsistência"));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarSucesso() throws Exception {
		
		Veiculo veiculo = criarVeiculoTeste();
		VeiculoDto objEntrada = ConversaoUtils.conveterVeiculo(veiculo);

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class))).willReturn(veiculo);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
				.content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.marca").value(objEntrada.getMarca()))
				.andExpect(jsonPath("$.dados.cor").value(objEntrada.getCor()))
				.andExpect(jsonPath("$.dados.placa").value(objEntrada.getPlaca()))		
				.andExpect(jsonPath("$.dados.tipo").value(objEntrada.getTipo()))	
				.andExpect(jsonPath("$.dados.usuarioId").value(objEntrada.getUsuarioId()))
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
	public void testSalvarMarcaEmBranco() throws Exception {
		
		VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setCor("Preto");
		objEntrada.setPlaca("JYK2750");
		objEntrada.setTipo("C");
		objEntrada.setUsuarioId("2");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Marca não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarCorEmBranco() throws Exception {
		
		VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setMarca("Fiat");
		objEntrada.setPlaca("JYK2750");
		objEntrada.setTipo("C");
		objEntrada.setUsuarioId("2");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Cor não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarPlacaEmBranco() throws Exception {
		
		VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setMarca("Fiat");
		objEntrada.setCor("Preto");
		objEntrada.setTipo("C");
		objEntrada.setUsuarioId("2");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Placa não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarPlacaExcedente() throws Exception {
		
		VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setMarca("Fiat");
		objEntrada.setCor("Preto");
		objEntrada.setPlaca("JYK27501");
		objEntrada.setTipo("C");
		objEntrada.setUsuarioId("2");
		
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("A placa deve conter apenas 7 digitos"));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarTipoEmBranco() throws Exception {
		
		VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setMarca("Fiat");
		objEntrada.setCor("Preto");
		objEntrada.setPlaca("JYK2750");
		objEntrada.setUsuarioId("2");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("Tipo não pode ser vazio."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarTipoExcedente() throws Exception {
		
VeiculoDto objEntrada = new VeiculoDto();
		
		objEntrada.setMarca("Fiat");
		objEntrada.setCor("Preto");
		objEntrada.setPlaca("JYK2750");
		objEntrada.setTipo("C");
		objEntrada.setUsuarioId("2");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/veiculo")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.erros").value("O tipo deve conter apenas 1 digito"));
	}
}
