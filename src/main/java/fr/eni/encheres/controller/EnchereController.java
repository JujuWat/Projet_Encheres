package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.encheres.bll.EnchereService;

@Controller
public class EnchereController {

	private EnchereService enchereService;

	public EnchereController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}
	
	

	
	// Association : 
	/* private EnchereService enchereService;
	// Consructeur pour instancier l'association : 
	public EnchereController(EnchereService enchereService) {
		this.enchereService = enchereService;
	} */

		
	@GetMapping("/")
	public String afficherAccueil() {
		System.out.println("affichage de l'accueil");
		return "accueil"; 
	} 
	
	@GetMapping("/profil")
	public String afficherProfil() {
		System.out.println("affichage de profil");
		return "profil"; 
	} 
	
	@GetMapping("/logout")
	public String afficherLogout() {
		System.out.println("affichage de logout");
		return "profil"; 
	} 
	
}
