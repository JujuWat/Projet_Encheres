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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		if ((filtreRecherche.getMotCle()!= null && !filtreRecherche.getMotCle().isEmpty()) || 
				(filtreRecherche.getCategorie().getNoCategorie()>0 && filtreRecherche.getCategorie()!= null)) {
			articles = articleVenduService.afficheSiContientEtCategorie(
					filtreRecherche.getMotCle(), 
					filtreRecherche.getCategorie().getNoCategorie());
			System.out.println("prise en compte des filtres");
		} else {
			
		}
		
		model.addAttribute("filtre", new FiltreRecherche());
		model.addAttribute("articles", articles);
		model.addAttribute("motCle", filtreRecherche.getMotCle());
		model.addAttribute("noCategorie", filtreRecherche.getCategorie());
		
		return "accueil";
		
	}*/
	@PostMapping("/encheres")
	public String afficherUnObjet(@ModelAttribute FiltreRecherche filtreRecherche, Model model) {
	    // Initialiser les valeurs par défaut si elles sont nulles
	    if (filtreRecherche == null) {
	        filtreRecherche = new FiltreRecherche();
	    }
	    if (filtreRecherche.getCategorie() == null) {
	        filtreRecherche.setCategorie(new Categorie());
	        filtreRecherche.getCategorie().setNoCategorie(0); // Par défaut : "Toutes les catégories"
	    }
	    if (filtreRecherche.getMotCle() == null) {
	        filtreRecherche.setMotCle("");
	    }

	    // Appel au service avec les valeurs validées
	    List<ArticleVendu> articles = articleVenduService.afficheSiContientEtCategorie(
	        filtreRecherche.getMotCle().trim(),
	        filtreRecherche.getCategorie().getNoCategorie()
	    );

	    // Ajout des données au modèle
	    model.addAttribute("filtre", filtreRecherche);
	    model.addAttribute("articles", articles);
	    return "accueil";
	}
	
	@GetMapping("/encheres")
	public String afficherUnObjet(Model model) {
	
		return "accueil";
	}
	

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
