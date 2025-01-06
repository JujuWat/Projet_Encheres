package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("categoriesSession")
public class VendreController {

	// Associations : 
	private ArticleVenduService articleVenduService;
	private UtilisateurService utilisateurService;
	private RetraitService retraitService;
	
	// Constructeur : 
	public VendreController(ArticleVenduService articleVenduService, UtilisateurService utilisateurService, RetraitService retraitService) {
		super();
		this.articleVenduService = articleVenduService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}

	
	@GetMapping("/vendre")
	public String afficherVendre(Model model) {
		// Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utilisateurConnecte = (User) authentication.getPrincipal();  // Cast en User
        String pseudo = utilisateurConnecte.getUsername();  
        // Récupérer l'objet utilisateur 
        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
        System.out.println("Utilisateur = " +utilisateur);
        // Passer l'utilisateur ou son id au modèle pour l'utiliser dans le formulaire
        model.addAttribute("utilisateur", utilisateur);
        // Passer l'article vendu au modèle
        ArticleVendu articleVendu = new ArticleVendu();
        Retrait retrait = new Retrait();
        retrait.setCode_postal(utilisateur.getCode_postal());
        retrait.setRue(utilisateur.getRue());
        retrait.setVille(utilisateur.getVille());
        articleVendu.setLieuRetrait(retrait);
        model.addAttribute("articleVendu", articleVendu);
		System.out.println("affichage de vendre");
		return "vendre";
	}
		

	@PostMapping("/vendre")
	public String creerArticle (@Valid @ModelAttribute ArticleVendu article, BindingResult bindingResult, @ModelAttribute Retrait retrait, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			// Récupérer l'utilisateur authentifié
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        User utilisateurConnecte = (User) authentication.getPrincipal();  // Cast en User
	        String pseudo = utilisateurConnecte.getUsername();  
	        // Récupérer l'objet utilisateur 
	        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
	        System.out.println("Utilisateur = " +utilisateur);
	        // Passer l'utilisateur ou son id au modèle pour l'utiliser dans le formulaire
	        model.addAttribute("utilisateur", utilisateur);
			return "vendre"; /// Renvoie à la page avec les erreurs affichées
		} else {
		// Récupérer l'utilisateur connecté via Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utilisateurConnecte = (User) authentication.getPrincipal();  
        String pseudo = utilisateurConnecte.getUsername();
        // Récupérer l'objet utilisateur 
        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
        // Associer l'utilisateur connecté à l'article
        article.setVend(utilisateur);
        // Ajouter l'article dans la base de données
		this.articleVenduService.ajouterArticle(article);

		return "objetvendu"; // J'ai changé au lieu de mettre redirect:/vendre
		}
	}
	
	@ModelAttribute("categoriesSession")
	public List<Categorie> getCategoriesSession() {
		return this.articleVenduService.consulterCategorie();
	}
	
	
}
