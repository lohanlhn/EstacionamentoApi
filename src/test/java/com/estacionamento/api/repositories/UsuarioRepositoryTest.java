package com.estacionamento.api.repositories;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

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
		usuarioTeste.setEmail("05887098082");
		usuarioTeste.setSenha("666");
		usuarioTeste.setTipo("2");
		usuarioTeste.setAtivo(true);
		
		
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
	
	
}
