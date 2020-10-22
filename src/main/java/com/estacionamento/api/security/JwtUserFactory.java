package com.estacionamento.api.security;

import com.estacionamento.api.entities.Usuario;

public class JwtUserFactory {
   	
   	public static JwtUser create(Usuario usuario) {
   	
         	return new JwtUser(usuario);	
   	
   	}
   	
}

