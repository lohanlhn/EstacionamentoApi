package com.estacionamento.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.estacionamento.api.dtos.RegraDto;
import com.estacionamento.api.dtos.UsuarioDto;
import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.dtos.ValoresDto;
import com.estacionamento.api.entities.Regra;
import com.estacionamento.api.entities.Usuario;
import com.estacionamento.api.entities.Vaga;
import com.estacionamento.api.entities.Valores;
import com.sun.el.parser.ParseException;

public class ConversaoUtils {
	public static Valores ConverterValoresDto(ValoresDto valoresDto) throws ParseException{
		
		Valores valores = new Valores();
		
		if(valoresDto.getId() != null && valoresDto.getId() != "")
			valores.setId(Integer.parseInt(valoresDto.getId()));
		
		valores.setMinutagem(Integer.parseInt(valoresDto.getMinutagem()));
		valores.setValor(Double.parseDouble(valoresDto.getValor()));
		
		return valores;
	}
	
	public static ValoresDto ConverterValores(Valores valores) {
		ValoresDto valoresDto = new ValoresDto();
		
		valoresDto.setId(String.valueOf(valores.getId()));
		valoresDto.setMinutagem(String.valueOf(valores.getMinutagem()));
		valoresDto.setValor(String.valueOf(valores.getValor()));
		
		return valoresDto;
	}
	
	public static List<ValoresDto> ConverterListaValores(List<Valores> valores){
	
		List<ValoresDto> valoresDto = new ArrayList<ValoresDto>();
		
		for(Valores valor : valores) 
			valoresDto.add(ConverterValores(valor));
		
		return valoresDto;
	}
	
	public static Vaga ConverterVagaDto(VagaDto vagaDto) throws ParseException{
		Vaga vaga = new Vaga();
		
		if(vagaDto.getId() != null && vagaDto.getId() !="")
			vaga.setId(Integer.parseInt(vagaDto.getId()));
		
		vaga.setCodVaga(vagaDto.getCodVaga());
		vaga.setDisponivel(Boolean.parseBoolean(vagaDto.getDisponivel()));
		
		return vaga;
	}
	
	public static VagaDto ConverterVaga(Vaga vaga) {
		VagaDto vagaDto = new VagaDto();
		
		vagaDto.setId(String.valueOf(vaga.getId()));
		vagaDto.setDisponivel(String.valueOf(vaga.getDisponivel()));
		vagaDto.setCodVaga(String.valueOf(vaga.getCodVaga()));
		
		return vagaDto;
	}
	
	public static List<VagaDto> ConverterListaVaga(List<Vaga> vagas){
		
		List<VagaDto> vagasDto = new ArrayList<VagaDto>();
		
		for(Vaga vaga : vagas) 
			vagasDto.add(ConverterVaga(vaga));
		
		return vagasDto;
	}
	
	public static Usuario ConverterUsuarioDto(UsuarioDto usuarioDto) {
		 
     	Usuario usuario = new Usuario();

     	if (usuarioDto.getId() != null && usuarioDto.getId() != "")
            	usuario.setId(Integer.parseInt(usuarioDto.getId()));

     	usuario.setNome(usuarioDto.getNome());
     	usuario.setEmail(usuarioDto.getEmail());
     	usuario.setAtivo(Boolean.parseBoolean(usuarioDto.getAtivo()));
     	usuario.setSenha(usuarioDto.getSenha());
     	usuario.setTipo(usuarioDto.getTipo());

     	if (usuarioDto.getRegras() != null && usuarioDto.getRegras().size() > 0) {

            	usuario.setRegras(new ArrayList<Regra>());

            	for (RegraDto regraDto : usuarioDto.getRegras()) {

                   	Regra regra = new Regra();
                   	regra.setNome(regraDto.getNome());

                   	usuario.getRegras().add(regra);

            	}

     	}

     	return usuario;

	}

	public static UsuarioDto ConverterUsuario(Usuario usuario) {

     	UsuarioDto usuarioDto = new UsuarioDto();

     	usuarioDto.setId(Integer.toString(usuario.getId()));

     	usuarioDto.setNome(usuario.getNome());
     	usuarioDto.setEmail(usuario.getEmail());
     	usuarioDto.setAtivo(Boolean.toString(usuario.getAtivo()));
     	usuarioDto.setSenha(usuario.getSenha());
     	usuarioDto.setTipo(usuario.getTipo());

     	if (usuario.getRegras() != null) {
            	
            	usuarioDto.setRegras(new ArrayList<RegraDto>());

            	for (int i = 0; i < usuario.getRegras().size(); i++) {
                   	
                   	RegraDto regraDto = new RegraDto();
                   	
                   	regraDto.setNome(usuario.getRegras().get(i).getNome());
                   	regraDto.setDescricao(usuario.getRegras().get(i).getDescricao());
                   	regraDto.setAtivo(usuario.getRegras().get(i).getAtivo());
                   	
                   	usuarioDto.getRegras().add(regraDto);
                   	
            	}

     	}

     	return usuarioDto;

	}

	public static RegraDto ConverterRegra(Regra regra) {

     	RegraDto regraDto = new RegraDto();

     	regraDto.setNome(regra.getNome());
     	regraDto.setDescricao(regra.getDescricao());
     	regraDto.setAtivo(regra.getAtivo());

     	return regraDto;

	}

	public static List<RegraDto> ConverterListaRegras(List<Regra> regras) {

     	List<RegraDto> regrasDto = new ArrayList<RegraDto>();

     	for (Regra regra : regras)
            	regrasDto.add(ConverterRegra(regra));

     	return regrasDto;

	}

	
}
