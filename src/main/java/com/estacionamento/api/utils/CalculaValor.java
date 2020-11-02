package com.estacionamento.api.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Valores;

public class CalculaValor {
	
	private double valor;
	
	private int tik;
	
	public static double CalculaValores (List<Valores> valores, Optional<VagaOcupada> vagaOcupada ) throws ParseException {
		
		List<CalculaValor> calcula = new ArrayList<CalculaValor>();
		CalculaValor aux = new CalculaValor();
		
		long tempo1 = vagaOcupada.get().getHoraEntrada().getTime();
		long tempo2 = vagaOcupada.get().getHoraSaida().getTime();
		long diff = tempo2 - tempo1;
		long diffMin = diff/60000;
		long total = 0;
		ArrayList<Long> totais = new ArrayList<Long>();
		total =(diffMin % valores.get(0).getMinutagem());
		
		for(int i = 0; i < valores.size(); i++) {
			totais.add(total);
			total =(diffMin % valores.get(i).getMinutagem());
			
			
			
			if (total == 0) {
				aux.valor = valores.get(i).getValor();
				aux.tik = (int) (totais.get(i) / valores.get(i).getMinutagem());
				calcula.add(aux);
				
				
				break;
			}
			
		}
		vagaOcupada.get().setValor(0);
		
		for(int i = 0; i < calcula.size(); i++) {
			double b = calcula.get(i).tik * calcula.get(i).valor;
			vagaOcupada.get().setValor(vagaOcupada.get().getValor() + b);
		}
		
		
		
		return vagaOcupada.get().getValor();
	}

}
