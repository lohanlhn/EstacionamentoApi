package com.estacionamento.api.services;


import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamento.api.entities.Cliente;
import com.estacionamento.api.repositories.ClienteRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class ClienteServiceTest {

	@MockBean
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Test
	public void testBuscarPorUsuarioIdExistente() throws ConsistenciaException {	
		
		BDDMockito.given(clienteRepository.findByUsuarioId(Mockito.anyInt()))
			.willReturn(Optional.of(new Cliente()));
		
		Optional<Cliente> resultado = clienteService.buscarPorUsuarioId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorUsuarioIdNaoExistente() throws ConsistenciaException {	
		
		BDDMockito.given(clienteRepository.findByUsuarioId(Mockito.anyInt()))
			.willReturn(Optional.empty());
		
		clienteService.buscarPorUsuarioId(1);
		
	}
	
	/*@Test
	public void testBuscarPorCpfExistente() throws ConsistenciaException {	
		
		BDDMockito.given(clienteRepository.findByCpf(Mockito.anyString()))
			.willReturn(Optional.of(new Cliente()));
		
		Optional<Cliente> resultado = clienteService.buscarPorCPF("29041602054");
		
		assertTrue(resultado.isPresent());
		
	}*/
	
	
	/*@Test(expected = ConsistenciaException.class)
	public void testSalvarUsuarioIdNaoEncontrado() throws ConsistenciaException {	
		
		BDDMockito.given(clienteRepository.findByUsuarioId(Mockito.anyInt()))
		.willReturn(Optional.empty());
		
		Cliente c = new Cliente();
		c.setId(1);
		
		clienteService.salvar(c);

	}*/
	
}
