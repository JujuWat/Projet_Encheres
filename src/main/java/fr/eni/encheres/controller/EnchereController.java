package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.ArticleVendu;

import org.springframework.web.bind.annotation.ModelAttribute;



import fr.eni.encheres.bll.UtilisateurService;


import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class EnchereController {


	private EnchereService enchereService;
	private UtilisateurService utilisateurService;
	private PasswordEncoder passwordEncoder;


	public EnchereController(EnchereService enchereService, UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
		this.passwordEncoder = passwordEncoder;
	}


	@GetMapping("/encheres")
	public String afficherUnObjet(@RequestParam("motCle") String motCle, Model model) {
		List<ArticleVendu> articles = new ArrayList<>();
		if(motCle != null && !motCle.isEmpty()) {
			articles = enchereService.afficheSiContient(motCle);
		}
		model.addAttribute("articles",articles);
		model.addAttribute("motCle",motCle);
		return "accueil";
	}
	
		
	@GetMapping("/")
	public String afficherAccueil() {
		System.out.println("affichage de l'accueil");
		return "accueil"; 
	} 
	
	
	
	
	@GetMapping("/logout")
	public String afficherLogout() {
		System.out.println("affichage de logout");


		return "/"; 

		

	} 
	
	
	
}
