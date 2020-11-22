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

import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.repositories.VeiculoRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class VeiculoServiceTest {
	@MockBean
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException{
		BDDMockito.given(veiculoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Veiculo()));
		
		Optional<Veiculo> resultado = veiculoService.buscarPorId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException{
		BDDMockito.given(veiculoRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		veiculoService.buscarPorId(1);
			
	}
	
	@Test
	public void testExcluirSucesso() throws ConsistenciaException{
		
		BDDMockito.given(veiculoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Veiculo()));				
		
		veiculoService.excluirPorId(1);		
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testExcluirFalha() throws ConsistenciaException{
				
		veiculoService.excluirPorId(1);		
	}
}
