package com.estacionamento.api.repositories;

import static org.junit.Assert.assertEquals;

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

import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.entities.Veiculo;
import com.sun.el.parser.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VeiculoRepositoryTest {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RegraRepository regraRepository;
	
	private Veiculo veiculoTeste;
	private Usuario usuarioTeste;
	
	private void criarUsuarioTeste() throws ParseException {
		
		usuarioTeste = new Usuario();
		
		usuarioTeste.setNome("Nome Teste");
		usuarioTeste.setEmail("teste@email.com");
		usuarioTeste.setSenha("666");
		usuarioTeste.setTipo("F");		
		usuarioTeste.setTelefone("999");
		usuarioTeste.setRegras(regraRepository.findAll());
		
		
	}
		
	private void criarVeiculoTeste() throws ParseException{
			
		veiculoTeste = new Veiculo();
		
		veiculoTeste.setMarca("Fiat");
		veiculoTeste.setCor("Azul");
		veiculoTeste.setPlaca("ABC1D23");
		veiculoTeste.setTipo("C");
		
		criarUsuarioTeste();
		
		veiculoTeste.setUsuario(usuarioTeste);
		
		
	}
		
	@Before
	public void setUp() throws Exception{
		
		criarUsuarioTeste();
		criarVeiculoTeste();
		
		usuarioRepository.save(usuarioTeste);
		veiculoRepository.save(veiculoTeste);
	
	}
		
	@After
	public void tearDown() throws Exception{
		veiculoRepository.deleteAll();
		usuarioRepository.deleteAll();
	}
		
	@Test
	public void testFindById() {
		Veiculo veiculo = veiculoRepository.findById(veiculoTeste.getId()).get();
		assertEquals(veiculoTeste.getId(), veiculo.getId());
	}
		
	@Test
	public void testFindAll() {
		List<Veiculo> veiculo = veiculoRepository.findAll();
		assertEquals(veiculoTeste.getId(), veiculo.get(0).getId());
	}
	
	@Test
	public void testFindByUsuarioId() {
		Optional<List<Veiculo>> lstveiculos = veiculoRepository.findByUsuarioId(veiculoTeste.getUsuario().getId());
		assertEquals(veiculoTeste.getId(), lstveiculos.get().get(0).getId());
	}
}