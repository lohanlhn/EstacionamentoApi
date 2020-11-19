package com.estacionamento.api.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamento.api.entities.Vaga;
import com.sun.el.parser.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VagaRepositoryTest {

	@Autowired
	private VagaRepository vagaRepository;
	
	private Vaga vagaTeste;
	
	private void criarVagaTeste() throws ParseException{
		
		vagaTeste = new Vaga();
		
		vagaTeste.setCodVaga("A01");
		vagaTeste.setDisponivel(true);
	}
	
	@Before
	public void setUp() throws Exception{
		criarVagaTeste();
		vagaRepository.save(vagaTeste);
	}
	
	@After
	public void tearDown() throws Exception{
		vagaRepository.deleteAll();
	}
	
	@Test
	public void testFindById() {
		Vaga vaga = vagaRepository.findById(vagaTeste.getId()).get();
		assertEquals(vagaTeste.getId(), vaga.getId());
	}
	
	@Test
	public void testFindAll() {
		List<Vaga> vaga = vagaRepository.findAll();
		assertEquals(vagaTeste.getId(), vaga.get(0).getId());
	}
}
