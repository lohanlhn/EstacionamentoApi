package com.estacionamento.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
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

import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.repositories.VagaOcupadaRepository;
import com.estacionamento.api.repositories.VagaRepository;
import com.estacionamento.api.utils.ConsistenciaException;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class VagaOcupadaServiceTest {
	@MockBean
	private VagaOcupadaRepository vagaOcupadaRepository;
	
	@Autowired
	private VagaOcupadaService vagaOcupadaService;
	
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException{
		BDDMockito.given(vagaOcupadaRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new VagaOcupada()));
		
		Optional<VagaOcupada> resultado = vagaOcupadaService.buscarPorId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException{
		BDDMockito.given(vagaOcupadaRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		vagaOcupadaService.buscarPorId(1);
			
	}
		
}
