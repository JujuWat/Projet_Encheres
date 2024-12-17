package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.ArticleVendu;

import org.springframework.web.bind.annotation.ModelAttribute;



import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class EnchereController {


	private EnchereService enchereService;
	private UtilisateurService utilisateurService;



	public EnchereController(EnchereService enchereService, UtilisateurService utilisateurService) {
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
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
	
	@GetMapping("/profil")
	public String afficherProfil() {
		System.out.println("affichage de profil");
		return "profil"; 
	} 
	
	@GetMapping("/creer")
	public String afficherCreationUtilisateur(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		
		return "view-utilisateur-creation";
		
	}
	
	@PostMapping("/creer")
	public String postMethodName(@ModelAttribute Utilisateur utilisateur) {
		this.utilisateurService.ajouterUtilisateur(utilisateur);
		return "accueil";
	}
	
	
	
	
	@GetMapping("/logout")
	public String afficherLogout() {
		System.out.println("affichage de logout");
		return "logout"; 
	} 
	
}
