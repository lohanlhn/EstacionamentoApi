package com.estacionamento.api.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.estacionamento.api.dtos.UsuarioDto;
import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.dtos.VagaOcupadaDto;
import com.estacionamento.api.dtos.ValoresDto;
import com.estacionamento.api.dtos.VeiculoDto;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Valores;
import com.estacionamento.api.entities.Veiculo;
import java.text.ParseException;

public class ConversaoUtils {
	public static Valores converterValoresDto(ValoresDto valoresDto) throws ParseException{
		
		Valores valores = new Valores();
		
		if(valoresDto.getId() != null && valoresDto.getId() != "")
			valores.setId(Integer.parseInt(valoresDto.getId()));
		
		valores.setMinutagem(Integer.parseInt(valoresDto.getMinutagem()));
		valores.setValor(Double.parseDouble(valoresDto.getValor()));
		
		return valores;
	}
	
	public static ValoresDto converterValores(Valores valores) {
		ValoresDto valoresDto = new ValoresDto();
		
		valoresDto.setId(String.valueOf(valores.getId()));
		valoresDto.setMinutagem(String.valueOf(valores.getMinutagem()));
		valoresDto.setValor(String.valueOf(valores.getValor()));
		
		return valoresDto;
	}
	
	public static List<ValoresDto> converterListaValores(List<Valores> valores){
	
		List<ValoresDto> valoresDto = new ArrayList<ValoresDto>();
		
		for(Valores valor : valores) 
			valoresDto.add(converterValores(valor));
		
		return valoresDto;
	}
	
	public static Vaga converterVagaDto(VagaDto vagaDto) throws ParseException{
		Vaga vaga = new Vaga();
		
		if(vagaDto.getId() != null && vagaDto.getId() !="")
			vaga.setId(Integer.parseInt(vagaDto.getId()));
		
		vaga.setCodVaga(vagaDto.getCodVaga());
		vaga.setDisponivel(Boolean.parseBoolean(vagaDto.getDisponivel()));
		
		return vaga;
	}
	
	public static VagaDto converterVaga(Vaga vaga) {
		VagaDto vagaDto = new VagaDto();
		
		vagaDto.setId(String.valueOf(vaga.getId()));
		vagaDto.setDisponivel(String.valueOf(vaga.getDisponivel()));
		vagaDto.setCodVaga(String.valueOf(vaga.getCodVaga()));
		
		return vagaDto;
	}
	
	public static List<VagaDto> converterListaVaga(List<Vaga> vagas){
		
		List<VagaDto> vagasDto = new ArrayList<VagaDto>();
		
		for(Vaga vaga : vagas) 
			vagasDto.add(converterVaga(vaga));
		
		return vagasDto;
	}
	
	public static Usuario converterUsuarioDto(UsuarioDto usuarioDto) {
		 
     	Usuario usuario = new Usuario();

     	if (usuarioDto.getId() != null && usuarioDto.getId() != "")
            	usuario.setId(Integer.parseInt(usuarioDto.getId()));

     	usuario.setNome(usuarioDto.getNome());
     	usuario.setEmail(usuarioDto.getEmail());     	
     	usuario.setSenha(usuarioDto.getSenha());     	
     	usuario.setTelefone(usuarioDto.getTelefone());     	  

     	return usuario;

	}

	public static UsuarioDto converterUsuario(Usuario usuario) {

     	UsuarioDto usuarioDto = new UsuarioDto();

     	usuarioDto.setId(Integer.toString(usuario.getId()));

     	usuarioDto.setNome(usuario.getNome());
     	usuarioDto.setEmail(usuario.getEmail());     
     	usuarioDto.setSenha(usuario.getSenha());
     	usuarioDto.setTelefone(usuario.getTelefone());
     	usuarioDto.setTipo(usuario.getTipo());

     	return usuarioDto;

	}
	
	public static List<UsuarioDto> converterListaUsuario(List<Usuario> usuarios){
		
		List<UsuarioDto> usuariosDto = new ArrayList<UsuarioDto>();
		
		for(Usuario usuario : usuarios) 
			usuariosDto.add(converterUsuario(usuario));
		
		return usuariosDto;
	}

	
	public static VagaOcupadaDto converterVagaOcupada(VagaOcupada vagaOcupada) {
		VagaOcupadaDto vagaOcupadaDto = new VagaOcupadaDto();
		
		vagaOcupadaDto.setId(Integer.toString(vagaOcupada.getId()));
		vagaOcupadaDto.setHoraEntrada(vagaOcupada.getHoraEntrada().toString());
		if(!(vagaOcupada.getHoraSaida() == null)) {
			vagaOcupadaDto.setHoraSaida(vagaOcupada.getHoraSaida().toString());
		}
		vagaOcupadaDto.setValor(Double.toString(vagaOcupada.getValor()));
		vagaOcupadaDto.setPaga(Boolean.toString(vagaOcupada.isPaga()));
		
		
		vagaOcupadaDto.setVaga(String.valueOf(vagaOcupada.getVaga().getId()));
		vagaOcupadaDto.setVeiculo(String.valueOf(vagaOcupada.getVeiculo().getId()));
		
		
		return vagaOcupadaDto;
	}
	
	public static VagaOcupada converterVagaOcupadaDto(VagaOcupadaDto vagaOcupadaDto)throws ParseException, java.text.ParseException {
		VagaOcupada vagaOcupada = new VagaOcupada();
		
		if (vagaOcupadaDto.getId() != null && vagaOcupadaDto.getId() != "")
			vagaOcupada.setId(Integer.parseInt(vagaOcupadaDto.getId()));
		
		//vagaOcupada.setHoraEntrada(new SimpleDateFormat("dd/MM/yyyy").parse(vagaOcupadaDto.getHoraEntrada()));
		//vagaOcupada.setHoraSaida(new SimpleDateFormat("dd/MM/yyyy").parse(vagaOcupadaDto.getHoraSaida()));
		//vagaOcupada.setValor(Double.parseDouble(vagaOcupadaDto.getValor()));
		//vagaOcupada.setPaga(Boolean.parseBoolean(vagaOcupadaDto.getPaga()));
		
		Vaga vaga = new Vaga();
		
		vaga.setId(Integer.parseInt(vagaOcupadaDto.getVaga()));
		vagaOcupada.setVaga(vaga);
		
		Veiculo veiculo = new Veiculo();
		
		veiculo.setId(Integer.parseInt(vagaOcupadaDto.getVeiculo()));
		vagaOcupada.setVeiculo(veiculo);
		
		return vagaOcupada;
	}
	
public static List<VagaOcupadaDto> converterListaVagaOcupada (Optional<List<VagaOcupada>> listaVagaOcupada) throws ParseException {
		
		List<VagaOcupadaDto> listVagaOcupadaDto = new ArrayList<VagaOcupadaDto>();
		
		for (VagaOcupada vagaOcupada : listaVagaOcupada.get()) {
			listVagaOcupadaDto.add(converterVagaOcupada(vagaOcupada));
		}
		
		return listVagaOcupadaDto;
		
	}
	
	public static VeiculoDto conveterVeiculo (Veiculo veiculo) throws ParseException {
		VeiculoDto veiculoDto = new VeiculoDto();
		
		veiculoDto.setId(Integer.toString(veiculo.getId()));
		veiculoDto.setMarca(veiculo.getMarca());
		veiculoDto.setCor(veiculo.getCor());
		veiculoDto.setPlaca(veiculo.getPlaca());
		veiculoDto.setTipo(veiculo.getTipo());
		veiculoDto.setUsuarioId(Integer.toString(veiculo.getUsuario().getId()));
		
		return veiculoDto;
	}
	
	public static Veiculo coverterVeiculoDto (VeiculoDto veiculoDto) throws ParseException {
		Veiculo veiculo = new Veiculo();
		
		if (veiculoDto.getId() != null && veiculoDto.getId() != "")
			veiculo.setId(Integer.parseInt(veiculoDto.getId()));
		
		veiculo.setMarca(veiculoDto.getMarca());
		veiculo.setCor(veiculoDto.getCor());
		veiculo.setPlaca(veiculoDto.getPlaca());
		veiculo.setTipo(veiculoDto.getTipo());;
		
		Usuario usuario = new Usuario();
		usuario.setId(Integer.parseInt(veiculoDto.getUsuarioId()));
		
		veiculo.setUsuario(usuario);
		
		return veiculo;
		
	}
	
	public static List<VeiculoDto> converterListaVeiculo (Optional<List<Veiculo>> listaVeiculo) throws ParseException {
		
		List<VeiculoDto> listVeiculoDto = new ArrayList<VeiculoDto>();
		
		for (Veiculo veiculo : listaVeiculo.get()) {
			listVeiculoDto.add(conveterVeiculo(veiculo));
		}
		
		return listVeiculoDto;
		
	}
	
	public static Date conveterDataFuso (Date data) throws ParseException {
		
		long hora = data.getTime();
		long diff = hora - 10800000;
		String help = String.valueOf(diff);
		
		Date nova = new SimpleDateFormat("dd/MM/yyyy").parse(help);
		
		
		return nova;
	}
	
	

	
}
