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

import com.estacionamento.api.dtos.UsuarioDto;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.services.UsuarioService;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;

	private Usuario criarUsuarioTeste() {
		Usuario usuario = new Usuario();

		usuario.setId(1);
		usuario.setEmail("teste@email.com");
		usuario.setNome("nome teste");
		usuario.setSenha("senha1234");
		usuario.setTelefone("43988887777");
		usuario.setTipo("T");

		return usuario;
	}

	@Test
	@WithMockUser(roles = "ADM")
	public void testBuscarTodosOsFuncionariosSucesso() throws Exception{		
		Usuario usuario = criarUsuarioTeste();
		
		List<Usuario> lstUsuarios = new ArrayList<>(); 
		lstUsuarios.add(usuario);		
		
		BDDMockito.given(usuarioService.buscarTodosOsFuncionarios())
		.willReturn(Optional.of(lstUsuarios));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/usuario/funcionario/todos")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados[0].id").value(usuario.getId()))
		.andExpect(jsonPath("$.dados[0].nome").value(usuario.getNome()))
		.andExpect(jsonPath("$.dados[0].email").value(usuario.getEmail()))
		.andExpect(jsonPath("$.dados[0].telefone").value(usuario.getTelefone()))		
		.andExpect(jsonPath("$.erros").isEmpty());
				
	}
	
	@Test
	@WithMockUser
	public void testBuscarPeloEmailSucesso() throws Exception{
		Usuario usuario = criarUsuarioTeste();
		
		BDDMockito.given(usuarioService.buscarPorEmail(Mockito.anyString()))
		.willReturn(Optional.of(usuario));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/usuario/teste@email.com")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados.id").value(usuario.getId()))
		.andExpect(jsonPath("$.dados.nome").value(usuario.getNome()))
		.andExpect(jsonPath("$.dados.email").value(usuario.getEmail()))
		.andExpect(jsonPath("$.dados.telefone").value(usuario.getTelefone()))		
		.andExpect(jsonPath("$.erros").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testBuscarPeloEmailInconsistencia() throws Exception{
		BDDMockito.given(usuarioService.buscarPorEmail(Mockito.anyString()))
		.willThrow(new ConsistenciaException("Teste inconsistência"));
		
		mvc.perform(MockMvcRequestBuilders.get("/api/usuario/teste@email.com")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Teste inconsistência"));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteSucesso() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(usuarioService.salvarCliente(Mockito.any(Usuario.class)))
		.willReturn(usuario);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
		.andExpect(jsonPath("$.dados.nome").value(objEntrada.getNome()))
		.andExpect(jsonPath("$.dados.email").value(objEntrada.getEmail()))
		.andExpect(jsonPath("$.dados.telefone").value(objEntrada.getTelefone()))		
		.andExpect(jsonPath("$.erros").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteInconsistencia() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(usuarioService.salvarCliente(Mockito.any(Usuario.class)))
		.willThrow(new ConsistenciaException("Teste inconsistência."));
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteNomeEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome não pode ser vazio."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteNomeInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome("abc");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteNomeExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome("abcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgija");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteEmailEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setEmail(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Email não pode ser vazio."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteEmailInvalido() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setEmail("a");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Email inválido."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteSenhaEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha não pode ser vazio."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteSenhaInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha("a");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha deve conter entre 8 e 25 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteSenhaExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha deve conter entre 8 e 25 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteTelefoneEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Telefone não pode ser vazio."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteTelefoneInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone("123");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("O telefone deve conter entre 10 e 11 caracteres."));
	}
	
	@Test
	@WithMockUser
	public void testSalvarClienteTelefoneExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone("123456789123");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/cliente")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("O telefone deve conter entre 10 e 11 caracteres."));
	}
	///////////////////////////////////////
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioSucesso() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(usuarioService.salvarFuncionario(Mockito.any(Usuario.class)))
		.willReturn(usuario);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
		.andExpect(jsonPath("$.dados.nome").value(objEntrada.getNome()))
		.andExpect(jsonPath("$.dados.email").value(objEntrada.getEmail()))
		.andExpect(jsonPath("$.dados.telefone").value(objEntrada.getTelefone()))		
		.andExpect(jsonPath("$.erros").isEmpty());
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioInconsistencia() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		BDDMockito.given(usuarioService.salvarFuncionario(Mockito.any(Usuario.class)))
		.willThrow(new ConsistenciaException("Teste inconsistência."));
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioNomeEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioNomeInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome("abc");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioNomeExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setNome("abcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgija");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioEmailEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setEmail(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Email não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioEmailInvalido() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setEmail("a");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Email inválido."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioSenhaEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioSenhaInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha("a");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha deve conter entre 8 e 25 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioSenhaExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setSenha("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Senha deve conter entre 8 e 25 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioTelefoneEmBranco() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone(null);
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("Telefone não pode ser vazio."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioTelefoneInsuficiente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone("123");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("O telefone deve conter entre 10 e 11 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testSalvarFuncionarioTelefoneExcedente() throws Exception {
		Usuario usuario = criarUsuarioTeste();
		usuario.setTelefone("123456789123");
		UsuarioDto objEntrada = ConversaoUtils.converterUsuario(usuario);
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/usuario/funcionario")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))		
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros").value("O telefone deve conter entre 10 e 11 caracteres."));
	}
	
	@Test
	@WithMockUser(roles = "ADM")
	public void testExcluirPorIdSucesso() throws Exception{				
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/usuario/excluir/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.dados").value("Usuario de id: 1 excluído com sucesso"))
		.andExpect(jsonPath("$.erros").isEmpty());
	}
	
}
