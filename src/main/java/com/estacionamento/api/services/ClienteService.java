package com.estacionamento.api.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Cliente;
import com.estacionamento.api.repositories.ClienteRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class ClienteService {
	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
	
	@Autowired
	private ClienteRepository clienteRepository ;
	
	public Optional<Cliente> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um cliente com o id: {}", id);

		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (!cliente.isPresent()) {
			log.info("Service: Nenhum cliente com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum cliente com CPf: {} foi encontrado", id);
		}

		return cliente;

	}

	public Optional<Cliente> buscarPorCPF(String cpf) throws ConsistenciaException {
		log.info("Service: buscando um cliente com o CPF: {}", cpf);

		Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);

		if (!cliente.isPresent()) {
			log.info("Service: Nenhum cliente com CPF: {} foi encontrado", cpf);
			throw new ConsistenciaException("Nenhum cliente com CPf: {} foi encontrado", cpf);
		}

		return cliente;

	}
//test
	public Cliente salvar(Cliente cliente) throws ConsistenciaException {
		log.info("Sevice: salvando o cliente: {}", cliente);

		if (cliente.getId() > 0)
			buscarPorId(cliente.getId());
		
			return clienteRepository.save(cliente);
		

	}
}
