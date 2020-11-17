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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamento.api.entities.Cliente;
import com.estacionamento.api.entities.Regra;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.repositories.ClienteRepository;
import com.estacionamento.api.repositories.UsuarioRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException{
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Usuario()));
		
		Optional<Usuario> resultado = usuarioService.buscarPorId(1);
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException{
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		usuarioService.buscarPorId(1);
			
	}
	
	@Test
	public void testBuscarFuncionariosExistentes() throws ConsistenciaException{
		
		List<Usuario> lstUsuarios = new ArrayList<>(); 
		lstUsuarios.add(new Usuario());
		
		BDDMockito.given(usuarioRepository.findFuncionarios()).willReturn(Optional.of(lstUsuarios));
		
		Optional<List<Usuario>> resultado = usuarioService.buscarTodosOsFuncionarios();
		
		assertTrue(resultado.get().size() > 0);
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarFuncionariosInexistentes() throws ConsistenciaException{
		
		List<Usuario> lstUsuarios = new ArrayList<>(); 
		
		BDDMockito.given(usuarioRepository.findFuncionarios()).willReturn(Optional.of(lstUsuarios));
		
		usuarioService.buscarTodosOsFuncionarios();		
	}
	
	@Test
	public void testVerificarCredenciaisSucesso() throws ConsistenciaException{
		
		Usuario usuario = new Usuario();
		List<Regra> lstRegra = new ArrayList<>(); 						
		usuario.setRegras(lstRegra);		
		
		BDDMockito.given(usuarioRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(usuario));
		
		Optional<Usuario> resultado = usuarioService.verificarCredenciais("aaa");
		
		assertTrue(resultado.isPresent());
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testeVerificarCredenciaisFalha() throws ConsistenciaException{		
		BDDMockito.given(usuarioRepository.findByEmail(Mockito.anyString())).willReturn(Optional.empty());
		
		usuarioService.verificarCredenciais("aaa");
	}
	
	@Test
	public void testSalvarClienteSucesso() throws ConsistenciaException{
		
		Usuario usuario = new Usuario();
		usuario.setTipo("C");
		usuario.setEmail("teste");
		
		BDDMockito.given(usuarioRepository.save(Mockito.any(Usuario.class))).willReturn(new Usuario());
		BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class))).willReturn(new Cliente());
		BDDMockito.given(usuarioRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(usuario));
		
		Usuario resultado = usuarioService.salvarCliente(usuario, "999", "999");
		
		assertNotNull(resultado);
				
	}
}
