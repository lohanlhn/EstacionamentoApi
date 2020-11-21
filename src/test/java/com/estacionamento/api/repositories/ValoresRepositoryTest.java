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

import com.estacionamento.api.entities.Valores;
import com.sun.el.parser.ParseException;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ValoresRepositoryTest {
	
	@Autowired
	private ValoresRepository valoresRepository;
	
	private Valores valoresTeste;

	private void criarValoresTeste() throws ParseException{
		
		valoresTeste = new Valores();
		
		valoresTeste.setMinutagem(15);
		valoresTeste.setValor(7.5);
		
		
	}
	
	@Before
	public void setUp() throws Exception{
	
		criarValoresTeste();
		
		valoresRepository.save(valoresTeste);
	
	}
		
	@After
	public void tearDown() throws Exception{
		valoresRepository.deleteAll();
	}
		
	@Test
	public void testFindById() {
		Valores valores = valoresRepository.findById(valoresTeste.getId()).get();
		assertEquals(valoresTeste.getId(), valores.getId());
	}
		
	@Test
	public void testFindAll() {
		List<Valores> valores = valoresRepository.findAll();
		assertEquals(valoresTeste.getId(), valores.get(0).getId());
	}
	
}
