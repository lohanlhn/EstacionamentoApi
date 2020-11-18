package com.estacionamento.api.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.entities.Veiculo;
import com.estacionamento.api.repositories.UsuarioRepository;
import com.estacionamento.api.repositories.VeiculoRepository;
import com.estacionamento.api.utils.ConsistenciaException;

@Service
public class VeiculoService {
	private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Veiculo> buscarPorId(int id) throws ConsistenciaException {
		log.info("Service: buscando um veiculo com o id: {}", id);

		Optional<Veiculo> veiculo = veiculoRepository.findById(id);

		if (!veiculo.isPresent()) {
			log.info("Service: Nenhum veiculo com id: {} foi encontrado", id);
			throw new ConsistenciaException("Nenhum veiculo com id: {} foi encontrado", id);
		}

		return veiculo;

	}
	
	@Cacheable("cacheVeiculosPorCliente")
	public Optional<List<Veiculo>> buscarPorClienteId (int usuarioId) throws ConsistenciaException {
		
		log.info("Service: Buscando os veiculos do cliente de id: {}", usuarioId);
		Optional<List<Veiculo>> veiculos = veiculoRepository.findByUsuarioId(usuarioId);
		
		if(!veiculos.isPresent() || veiculos.get().size() < 1) {
			log.info("Service: Nenhum veiculo encontrado do cliente de Id: {}", usuarioId);
			throw new ConsistenciaException("Nenhum veiculo encontrado do cliente de Id: {}", usuarioId);
			
		}
		
		return veiculos;
		
	}

	public Veiculo salvar(Veiculo veiculo) throws ConsistenciaException {
		log.info("Sevice: salvando o veiculo: {}", veiculo);

		if (veiculo.getId() > 0)
			buscarPorId(veiculo.getId());
		
		Optional<Usuario> usuario = usuarioRepository.findById(veiculo.getUsuario().getId());
		
		if(usuario.get().getId() == 0) {
			log.info("Service: O usuarioId: {} não está cadastrado", veiculo.getUsuario().getId());
			throw new ConsistenciaException("O usuarioId: {} não está cadastrado", veiculo.getUsuario().getId());
		}

		try {
			return veiculoRepository.save(veiculo);
		} catch (DataIntegrityViolationException e) {
			log.info("Service: A placa: {} já está cadastrada para outro veiculo", veiculo.getPlaca());
			throw new ConsistenciaException("A placa: {} já está cadastrada para outro veiculo", veiculo.getPlaca());
		}

	}
	
	@CachePut("cacheVeiculosPorCliente")
	public void excluirPorId(int id) throws ConsistenciaException {
		log.info("Service: excluindo veiculo de Id: {}", id);
		buscarPorId(id);
		
		veiculoRepository.deleteById(id);
	}
}
