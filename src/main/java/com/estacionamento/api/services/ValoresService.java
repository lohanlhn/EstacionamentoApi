package com.estacionamento.api.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.repositories.ValoresRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class ValoresService {

	private static final Logger log = LoggerFactory.getLogger(ValoresService.class);

	@Autowired
	private ValoresRepository valoresRepository;

	public Optional<Valores> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um valores com o id: {}", id);

		Optional<Valores> valores = valoresRepository.findById(id);

		if (!valores.isPresent()) {
			log.info("Service: Nenhum valores com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum valores com id: {} foi encontrado", id);
		}

		return valores;

	}

	public Valores salvar(Valores valores) throws ConsistenciaException {
		log.info("Sevice: salvando o valores: {}", valores);

		if (valores.getId() > 0)
			buscarPorId(valores.getId());

		return valoresRepository.save(valores);

	}
	
	public Optional<List<Valores>> buscarTodosOsValores() throws ConsistenciaException{
		log.info("Service: buscando todos os valores");
		
		Optional<List<Valores>> valores = Optional.ofNullable(valoresRepository.findAll());
		
		
		if(!valores.isPresent() || valores.get().size() < 0) {
			log.info("Service: nenhum valor foi encontrado");
			throw new ConsistenciaException("Nenhum valor foi encontrado");
		}
		return valores;
	}
	
	public void excluirPorId(int id) throws ConsistenciaException{
		
		log.info("Service: excluíndo valor de ID: {}", id);
		
		buscarPorId(id);
		
		valoresRepository.deleteById(id);
	}
}
