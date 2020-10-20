package com.estacionamento.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.estacionamento.api.dtos.ValoresDto;
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
}
