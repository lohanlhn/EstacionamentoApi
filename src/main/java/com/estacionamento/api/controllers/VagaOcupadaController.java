package com.estacionamento.api.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamento.api.services.VagaOcupadaService;

@RestController
@RequestMapping("api/vagaOcupada")
@CrossOrigin(origins = "*")
public class VagaOcupadaController {
	
	private static final Logger log = LoggerFactory.getLogger(VagaOcupadaController.class);
	
	@Autowired
	private VagaOcupadaService vagaOcupadaService;

}
