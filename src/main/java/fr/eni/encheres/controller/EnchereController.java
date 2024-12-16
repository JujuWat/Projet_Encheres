package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;

import fr.eni.encheres.bll.EnchereService;

@Controller
public class EnchereController {
	private EnchereService enchereService;

	public EnchereController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}
	
	
	

}
