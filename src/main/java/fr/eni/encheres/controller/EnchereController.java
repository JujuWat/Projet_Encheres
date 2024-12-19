package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


import fr.eni.encheres.bll.ArticleVenduService;


import fr.eni.encheres.bll.EnchereService;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;

import org.springframework.web.bind.annotation.ModelAttribute;

import fr.eni.encheres.bll.UtilisateurService;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controller.dto.FiltreRecherche;
import jakarta.validation.Valid;


import org.springframework.web.bind.annotation.PostMapping;

@Controller
@SessionAttributes({ "listeCategorie" })
public class EnchereController {


	private UtilisateurService utilisateurService;

	private ArticleVenduService articleVenduService;
	private PasswordEncoder passwordEncoder;

	public EnchereController(UtilisateurService utilisateurService, ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;	
		this.utilisateurService = utilisateurService;
			
	

	}

	/*@PostMapping("/encheres")
	public String afficherUnObjet(@ModelAttribute FiltreRecherche filtreRecherche,Model model) {
		List<ArticleVendu> articles = new ArrayList<>();
		if ((filtreRecherche.getMotCle()!= null && !filtreRecherche.getMotCle().isEmpty()) || filtreRecherche.getCategorie()> 0) {
			articles = articleVenduService.afficheSiContientEtCategorie(
					filtreRecherche.getMotCle(), 
					filtreRecherche.getCategorie());
		}
		model.addAttribute("filtre", new FiltreRecherche());
		model.addAttribute("articles", articles);
		model.addAttribute("motCle", filtreRecherche.getMotCle());
		model.addAttribute("noCategorie", filtreRecherche.getCategorie());
		
		return "accueil";
	}*/

	@GetMapping("/")
	public String afficherAccueil(Model model) {
		System.out.println("affichage de l'accueil");
		model.addAttribute("filtre", new FiltreRecherche());

		 
		return "accueil"; 
	} 
	
	
	
	@GetMapping("/logout")
	public String afficherLogout() {
		System.out.println("affichage de logout");
		return "/"; 
	} 

	

	@ModelAttribute("listeCategorie")
	public List<Categorie> getCategorie() {
		System.out.println("charger la liste des catégories");
		return this.articleVenduService.consulterCategorie();
	}

	
	
}
