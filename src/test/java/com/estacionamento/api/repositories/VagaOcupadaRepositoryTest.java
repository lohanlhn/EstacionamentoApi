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

import com.estacionamento.api.entities.VagaOcupada;
import com.sun.el.parser.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class VagaOcupadaRepositoryTest {
	@Autowired
	private VagaOcupadaRepository vagaOcupadaRepository;
	
	private VagaOcupada vagaOcupadaTeste;
	
	private void criarVagaOcupadaTeste() throws ParseException{
		
		vagaOcupadaTeste = new VagaOcupada();
		
		vagaOcupadaTeste.setId(1);
		vagaOcupadaTeste.setValor(10);
		vagaOcupadaTeste.setPaga(true);
	}
	
	@Before
	public void setUp() throws Exception{
		criarVagaOcupadaTeste();
		vagaOcupadaRepository.save(vagaOcupadaTeste);
	}
	
	@After
	public void tearDown() throws Exception{
		vagaOcupadaRepository.deleteAll();
	}
	
	@Test
	public void testFindById() {
		VagaOcupada vagaOcupada = vagaOcupadaRepository.findById(vagaOcupadaTeste.getId()).get();
		assertEquals(vagaOcupadaTeste.getId(), vagaOcupada.getId());
	}
	
	@Test
	public void testFindAll() {
		List<VagaOcupada> vagaOcupada = vagaOcupadaRepository.findAll();
		assertEquals(vagaOcupadaTeste.getId(), vagaOcupada.get(0).getId());
	}
}
