package com.estacionamento.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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
import com.estacionamento.api.utils.ConversaoUtils;
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

	public VagaOcupada salvar(VagaOcupada vagaOcupada) throws ConsistenciaException, ParseException {
		log.info("Sevice: salvando o vagaOcupada: {}", vagaOcupada);
		
		Date now = new Date();
		
		vagaOcupada.setHoraEntrada(now);

		if (vagaOcupada.getId() > 0)
			buscarPorId(vagaOcupada.getId());
		
		ocuparVaga(vagaOcupada);

		return vagaOcupadaRepository.save(vagaOcupada);

	}
	
	public void ocuparVaga(VagaOcupada ocuparVaga) throws ConsistenciaException {
		
		Optional<Vaga> vaga = vagaRepository.findById(ocuparVaga.getVaga().getId());
		
		log.info("Service: Ocupando a Vaga : {}", vaga.get().getCodVaga()); 
		
		if(!vaga.isPresent()) {
			log.info("Service: Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
			
			throw new ConsistenciaException("Nunhuma vaga com id: {} foi encontrada", ocuparVaga.getId());
		}
		
		if (!vaga.get().getDisponivel()) {
			log.info(
					"Service: Não é possivel ocupar essa vaga, pois a vaga selecionada já está ocupada");
			
			throw new ConsistenciaException(
					"Não é possivel ocupar essa vaga, pois a vaga selecionada já está ocupada");
		}
		
		vaga.get().setDisponivel(false);
		
		vagaRepository.alterarDisponibilidade(vaga.get().getDisponivel(), ocuparVaga.getVaga().getId());
	}
	
	public void desocuparVaga(Optional<VagaOcupada> desocuparVaga) throws ConsistenciaException {
		
		Optional<Vaga> vaga = vagaRepository.findById(desocuparVaga.get().getVaga().getId());
		
		log.info("Service: Desocupando a Vaga : {}", vaga.get().getCodVaga()); 
		
		if(!vaga.isPresent()) {
			log.info("Service: Nunhuma vaga com id: {} foi encontrada", vaga.get().getId());
			
		
			throw new ConsistenciaException("Nunhuma vaga com id: {} foi encontrada", vaga.get().getId());
		}
		
		if (vaga.get().getDisponivel()) {
			log.info(
					"Service: Não é possivel Desocupar essa vaga, pois a vaga selecionada já está Desocupada");
			
			throw new ConsistenciaException(
					"Não é possivel Desocupar essa vaga, pois a vaga selecionada já está Desocupada");
		}
		
		vaga.get().setDisponivel(true);
		
		vagaRepository.alterarDisponibilidade(vaga.get().getDisponivel(), desocuparVaga.get().getVaga().getId());
	}
	
	public double VerValor (int id) throws ConsistenciaException, ParseException{
		log.info("Service: Buscando o Valor.");
		
		
		
		Date now = new Date();
		
		Optional<VagaOcupada> vagaOcupada = vagaOcupadaRepository.findById(id);

		if (!vagaOcupada.isPresent()) {
			log.info("Service: Nenhum vagaOcupada com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum vagaOcupada com id: {} foi encontrado", id);
		}
		
		vagaOcupada.get().setHoraSaida(now);
		
		alterarHoraSaida(vagaOcupada);
		
		List<Valores> valores = valoresRepository.findAll();
		vagaOcupada.get().setValor(CalculaValor.CalculaValores(valores, vagaOcupada));
		
		alterarValor(vagaOcupada);
		
		return vagaOcupada.get().getValor();
	}
	
	public void alterarValor (Optional<VagaOcupada> vagaOcupada) throws ConsistenciaException{
		log.info("Service: Alterando o valor salvo.");
		
		if(!vagaOcupada.isPresent()) {
			log.info("Service nenhuma VagaOcupada com id: {} foi encontado", vagaOcupada.get().getId());
			throw new ConsistenciaException("Nenhum vagaOcupada com id: {} foi encontrado", vagaOcupada.get().getId());
		}
		
		vagaOcupadaRepository.alterarValor(vagaOcupada.get().getValor(), vagaOcupada.get().getId());
	}
	
	public void alterarHoraSaida (Optional<VagaOcupada> vagaOcupada) throws ConsistenciaException{
		log.info("Service: Alterando a HoraSaida salvo.");
		
		if(!vagaOcupada.isPresent()) {
			log.info("Service: nenhuma VagaOcupada com id: {} foi encontado", vagaOcupada.get().getId());
			throw new ConsistenciaException("Nenhum vagaOcupada com id: {} foi encontrado", vagaOcupada.get().getId());
		}
		
		vagaOcupadaRepository.alterarHoraSaida(vagaOcupada.get().getHoraSaida(), vagaOcupada.get().getId());
	}
	
	public Optional<List<VagaOcupada>> buscarVagasNaoPagas() throws ConsistenciaException{
		log.info("Service: Buscando VagasOcupadas não pagas");
		
		Optional<List<VagaOcupada>> listaVagasOcupadas = vagaOcupadaRepository.buscarVagaNaoPagas();
		
		if(!listaVagasOcupadas.isPresent()) {
			log.info("Service: nenhuma VagaOcupada sem estar paga encontrada");
			throw new ConsistenciaException("Nenhuma VagaOcupada sem estar paga encontrada");
		}
		
		
		return listaVagasOcupadas;
	}
	
	public Optional<List<VagaOcupada>> buscarTodasVagaOcupadas() throws ConsistenciaException{
		log.info("Services: Buscando todas as VagasOcupadas");
		
		Optional<List<VagaOcupada>> tudo = Optional.of(vagaOcupadaRepository.findAll());
		
		if(!tudo.isPresent()) {
			log.info("Service: Nenhuma VagaOcupada encontrada");
			throw new ConsistenciaException("Nenhuma VagaOcupada encontrada");
		}
		
		return tudo;
	}
	
	public void confirmarPaga(int id) throws ConsistenciaException{
		log.info("Services: Confirmando pagamento da vagaOcupda de id: {]", id);
		
		Optional<VagaOcupada> vaga = vagaOcupadaRepository.findById(id);
		
		if(!vaga.isPresent()) {
			log.info("Service: Nenhuma Vaga Ocupada de id: {} foi encontrada");
			throw new ConsistenciaException("Nenhuma Vaga Ocupada de id: {} foi encontrada");
		}
		
		vagaOcupadaRepository.alterarPaga(id);
		
		desocuparVaga(vaga);
		
	}
	
	
}
