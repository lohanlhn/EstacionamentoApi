package com.estacionamento.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.estacionamento.api.repositories.ClienteRepository;
import com.estacionamento.api.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class ClienteRepositoryTest {
	

	@Autowired
	private ClienteRepository clienteRepository;
	
	private Cliente clienteTeste;
	
	private void CriarClienteTestes() throws ParseException {
			
			clienteTeste = new Cliente();
			
			clienteTeste.setTelefone("12345678910");
			clienteTeste.setCpf("05887098082");
			
		}
		
		@Before
		public void setUp() throws Exception {
			
			CriarClienteTestes();
			clienteRepository.save(clienteTeste);
			
		}
		
		@After
		public void tearDown() throws Exception {
			
			clienteRepository.deleteAll();
			
		}
	
		
	@Test
	public void testFindById() {
		
		Cliente cliente = clienteRepository.findById(clienteTeste.getId()).get();
		assertEquals(clienteTeste.getId(), cliente.getId());
		
	}
	
	
	
	
}
