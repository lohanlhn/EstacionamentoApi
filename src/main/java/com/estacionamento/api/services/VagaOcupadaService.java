package com.estacionamento.api.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.repositories.VagaOcupadaRepository;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.repositories.VagaRepository;
import com.estacionamento.api.repositories.ValoresRepository;
import com.estacionamento.api.utils.ConsistenciaException;
import com.estacionamento.api.utils.CalculaValor;

@Service
public class VagaOcupadaService {

	private static final Logger log = LoggerFactory.getLogger(VagaOcupadaService.class);

	@Autowired
	private VagaOcupadaRepository vagaOcupadaRepository;
	
	@Autowired
	private VagaRepository vagaRepository;
	
	@Autowired
	private ValoresRepository valoresRepository;

	public Optional<VagaOcupada> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um vagaOcupada com o id: {}", id);

		Optional<VagaOcupada> vagaOcupada = vagaOcupadaRepository.findById(id);

		if (!vagaOcupada.isPresent()) {
			log.info("Service: Nenhum vagaOcupada com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum vagaOcupada com id: {} foi encontrado", id);
		}

		return vagaOcupada;

	}

	public VagaOcupada salvar(VagaOcupada vagaOcupada) throws ConsistenciaException {
		log.info("Sevice: salvando o vagaOcupada: {}", vagaOcupada);

		if (vagaOcupada.getId() > 0)
			buscarPorId(vagaOcupada.getId());

		return vagaOcupadaRepository.save(vagaOcupada);

	}
	
	public void ocuparVaga(Vaga ocuparVaga) throws ConsistenciaException {
		ocuparVaga.setDisponivel(false);
		
		log.info("Service: Ocupando a Vaga : {}", ocuparVaga.getCodVaga());
		
		Optional<Vaga> vaga = vagaRepository.findById(ocuparVaga.getId()); 
		
		if(!vaga.isPresent()) {
			log.info("Service: Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
			
			throw new ConsistenciaException("Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
		}
		
		if (vaga.get().getDisponivel()) {
			log.info(
					"Service: Não é possivel ocupar essa vaga, pois a vaga selecionada já está ocupada");
			
			throw new ConsistenciaException(
					"Não é possivel ocupar essa vaga, pois a vaga selecionada já está ocupada");
		}
		
		
		vagaRepository.alterarDisponibilidade(ocuparVaga.getDisponivel(), ocuparVaga.getId());
	}
	public void desocuparVaga(Vaga ocuparVaga) throws ConsistenciaException {
		ocuparVaga.setDisponivel(true);
		log.info("Service: Desocupando a Vaga : {}", ocuparVaga.getCodVaga());
		
		Optional<Vaga> vaga = vagaRepository.findById(ocuparVaga.getId()); 
		
		if(!vaga.isPresent()) {
			log.info("Service: Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
			
		
			throw new ConsistenciaException("Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
		}
		
		if (!vaga.get().getDisponivel()) {
			log.info(
					"Service: Não é possivel Desocupar essa vaga, pois a vaga selecionada já está Desocupada");
			
			throw new ConsistenciaException(
					"Não é possivel Desocupar essa vaga, pois a vaga selecionada já está Desocupada");
		}
		
		
		vagaRepository.alterarDisponibilidade(ocuparVaga.getDisponivel(), ocuparVaga.getId());
	}
	
	public VagaOcupada VerValor (VagaOcupada vagaOcupada) throws ConsistenciaException, ParseException{
		log.info("Service: Buscando o Valor.");
		
		List<Valores> valores = valoresRepository.findAll();
		vagaOcupada.setValor(CalculaValor.CalculaValores(valores, vagaOcupada));
		
		return vagaOcupada;
	}
}
