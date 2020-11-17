package com.estacionamento.api.repositories;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamento.api.entities.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Usuario usuarioTeste;
	
	private void CriarUsuarioTestes() throws ParseException {
		
		usuarioTeste = new Usuario();
		
		usuarioTeste.setNome("Nome Teste");
		usuarioTeste.setEmail("teste@email.com");
		usuarioTeste.setSenha("666");
		usuarioTeste.setTipo("F");		
		
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		CriarUsuarioTestes();
		usuarioRepository.save(usuarioTeste);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		usuarioRepository.deleteAll();
		
	}
	
	@Test
	public void testFindById() {
		
		Usuario usuario = usuarioRepository.findById(usuarioTeste.getId()).get();
		assertEquals(usuarioTeste.getId(), usuario.getId());
		
	}
	
	@Test
	public void testFindByEmail() {
		
		Usuario usuario = usuarioRepository.findByEmail(usuarioTeste.getEmail()).get();
		assertEquals(usuarioTeste.getEmail(), usuario.getEmail());
		
	}
	
	@Test
	public void testFindByFuncionarios() {
		
		Optional<List<Usuario>> usuarios = usuarioRepository.findFuncionarios();		
		assertEquals(usuarioTeste.getId(), usuarios.get().get(0).getId());
		
	}
	
	
}
