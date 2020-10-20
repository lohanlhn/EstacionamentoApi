package com.estacionamento.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.estacionamento.api.dtos.VagaDto;
import com.estacionamento.api.dtos.ValoresDto;
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
	
}
