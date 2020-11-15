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
	
	public Optional<Cliente> buscarPorUsuarioId(int usuarioId) throws ConsistenciaException {
		log.info("Service: buscando um cliente com o usuarioId: {}", usuarioId);

		Optional<Cliente> cliente = clienteRepository.findByUsuarioId(usuarioId);

		if (!cliente.isPresent()) {
			log.info("Service: Nenhum cliente com usuarioId: {} foi encontrado", usuarioId);
			throw new ConsistenciaException("Nenhum cliente com usuarioId: {} foi encontrado", usuarioId);
		}

		return cliente;

	}
	
	public Cliente salvar(Cliente cliente) throws ConsistenciaException {
		log.info("Sevice: salvando o cliente: {}", cliente);

		Optional<Cliente> clienteAux = clienteRepository.findByUsuarioId(cliente.getUsuario().getId());
		
		cliente.setId(clienteAux.get().getId());
		
		return clienteRepository.save(cliente);
		

	}
}
