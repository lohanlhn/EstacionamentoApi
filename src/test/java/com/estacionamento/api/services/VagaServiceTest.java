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
import com.estacionamento.api.repositories.VagaRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VagaServiceTest {
	@MockBean
	private VagaRepository vagaRepository;
	
	@Autowired
	private VagaService vagaService;
	
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException{
		BDDMockito.given(vagaRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Vaga()));
		
		Optional<Vaga> resultado = vagaService.buscarPorId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException{
		BDDMockito.given(vagaRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		vagaService.buscarPorId(1);
			
	}
	
	@Test
	public void testSalvarClienteSucesso() throws ConsistenciaException{
		
		BDDMockito.given(vagaRepository.save(Mockito.any(Vaga.class))).willReturn(new Vaga());
		
		Vaga resultado = vagaService.salvar(new Vaga());
		
		assertNotNull(resultado);
				
	}
	
	@Test
	public void testBuscarTodasAsVagasExistentes() throws ConsistenciaException{
		
		List<Vaga> lstVagas = new ArrayList<>(); 
		lstVagas.add(new Vaga());
		
		BDDMockito.given(vagaRepository.findAll()).willReturn(lstVagas);
		
		Optional<List<Vaga>> resultado = vagaService.buscarTodasAsVagas();
		
		assertTrue(resultado.get().size() > 0);
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarTodasAsVagasInexistentes() throws ConsistenciaException{
		
		List<Vaga> lstVagas = new ArrayList<>(); 
		
		BDDMockito.given(vagaRepository.findAll()).willReturn(lstVagas);
		
		vagaService.buscarTodasAsVagas();		
	}
	
	@Test
	public void testExcluirPorIdSucesso() throws ConsistenciaException{
		
		BDDMockito.given(vagaRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Vaga()));				
		
		vagaService.excluirPorId(1);		
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testExcluirPorIdFalha() throws ConsistenciaException{
				
		vagaService.excluirPorId(1);		
	}
}
