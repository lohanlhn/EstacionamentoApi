package com.estacionamento.api.utils;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.estacionamento.api.entities.VagaOcupada;
import com.estacionamento.api.entities.Valores;

public class CalculaValor {
	
	public static double CalculaValores (List<Valores> valores, Optional<VagaOcupada> vagaOcupada ) throws ParseException {
		
		long tempo1 = vagaOcupada.get().getHoraEntrada().getTime();
		long tempo2 = vagaOcupada.get().getHoraSaida().getTime();
		long diff = tempo2 - tempo1;
		long diffMin = diff/60000;
		
		
		
		int total30 = (int) (diffMin/30);
		int total15 = (int) ((diffMin%30)/15);
		int total1 = (int) ((diffMin%30)%15);
		
		double Valor30 = total30 * valores.get(2).getValor();
		double Valor15 = total15 * valores.get(1).getValor();
		double Valor1 = total1 * valores.get(0).getValor();
		
		double valotTotal = Valor30 + Valor15 + Valor1;
		
		vagaOcupada.get().setValor(valotTotal);
		
		
		
		return vagaOcupada.get().getValor();
	}

}
