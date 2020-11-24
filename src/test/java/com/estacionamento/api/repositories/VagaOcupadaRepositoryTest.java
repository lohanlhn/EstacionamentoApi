package com.estacionamento.api.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Veiculo;
import com.sun.el.parser.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class VagaOcupadaRepositoryTest {
	
	@Autowired
	private VagaOcupadaRepository vagaOcupadaRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private VagaRepository vagaRepository;
	
	
	private VagaOcupada vagaOcupadaTeste;
	private Veiculo veiculoTeste;
	private Vaga vagaTeste;
	
	private void criarVagaTeste() throws ParseException{
		
		vagaTeste = new Vaga();
		
		vagaTeste.setCodVaga("2");
		vagaTeste.setDisponivel(true);
		
				
	}
	
	private void criarVeiculoTeste() throws ParseException{
			
			veiculoTeste = new Veiculo();
			
			veiculoTeste.setMarca("Fiat");
			veiculoTeste.setCor("Vermelho");
			veiculoTeste.setPlaca("ABC1D23");
			veiculoTeste.setTipo("C");
	
			
		}
	
	private void criarVagaOcupadaTeste() throws ParseException{
		
		vagaOcupadaTeste = new VagaOcupada();
		
		vagaOcupadaTeste.setId(1);
		vagaOcupadaTeste.setValor(10);
		vagaOcupadaTeste.setHoraEntrada(new Date());
		vagaOcupadaTeste.setHoraSaida(null);
		vagaOcupadaTeste.setPaga(true);
	
		
		criarVeiculoTeste();
		criarVagaTeste();
		
		vagaOcupadaTeste.setVeiculo(veiculoTeste);
		vagaOcupadaTeste.setVaga(vagaTeste);
		
	}
	
	@Before
	public void setUp() throws Exception{
		
		criarVagaTeste();
		criarVeiculoTeste();
		criarVagaOcupadaTeste();
		
		vagaRepository.save(vagaTeste);
		vagaOcupadaRepository.save(vagaOcupadaTeste);
		veiculoRepository.save(veiculoTeste);
	}
	
	@After
	public void tearDown() throws Exception{
		vagaOcupadaRepository.deleteAll();
		veiculoRepository.deleteAll();
		vagaRepository.deleteAll();
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
