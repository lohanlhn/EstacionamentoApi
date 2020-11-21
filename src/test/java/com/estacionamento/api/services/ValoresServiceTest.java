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

import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.repositories.ValoresRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ValoresServiceTest {
	
	@MockBean
	private ValoresRepository valoresRepository;
	
	@Autowired
	private ValoresService valoresService;
	
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException{
		BDDMockito.given(valoresRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Valores()));
		
		Optional<Valores> resultado = valoresService.buscarPorId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException{
		BDDMockito.given(valoresRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		valoresService.buscarPorId(1);
			
	}
	
	@Test
	public void testSalvarValoresSucesso() throws ConsistenciaException{
		
		BDDMockito.given(valoresRepository.save(Mockito.any(Valores.class))).willReturn(new Valores());
		
		Valores resultado = valoresService.salvar(new Valores());
		
		assertNotNull(resultado);
				
	}
	
	@Test
	public void testBuscarTodasOsValoresExistentes() throws ConsistenciaException{
		
		List<Valores> lstValores = new ArrayList<>(); 
		lstValores.add(new Valores());
		
		BDDMockito.given(valoresRepository.findAll()).willReturn(lstValores);
		
		Optional<List<Valores>> resultado = valoresService.buscarTodosOsValores();
		
		assertTrue(resultado.get().size() > 0);
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarTodasOsValoresInexistentes() throws ConsistenciaException{
		
		List<Valores> lstValores = new ArrayList<>(); 
		
		BDDMockito.given(valoresRepository.findAll()).willReturn(lstValores);
		
		valoresService.buscarTodosOsValores();		
	}
	
	@Test
	public void testExcluirPorIdSucesso() throws ConsistenciaException{
		
		BDDMockito.given(valoresRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Valores()));				
		
		valoresService.excluirPorId(1);		
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testExcluirPorIdFalha() throws ConsistenciaException{
				
		valoresService.excluirPorId(1);		
	}

}
