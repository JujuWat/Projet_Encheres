package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.encheres.bll.ArticlesVendusService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("categoriesSession")
public class VendreController {

	// Associations : 
	private ArticlesVendusService articlesVendusService;
	private UtilisateurService utilisateurService;
	
	// Constructeur : 
	public VendreController(ArticlesVendusService articlesVendusService, UtilisateurService utilisateurService) {
		super();
		this.articlesVendusService = articlesVendusService;
		this.utilisateurService = utilisateurService;
	}

	
	@GetMapping("/vendre")
	public String afficherVendre(Model model) {
		// Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utilisateurConnecte = (User) authentication.getPrincipal();  // Cast en User
        String pseudo = utilisateurConnecte.getUsername();  
        // Récupérer l'objet utilisateur 
        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
        // Passer l'utilisateur ou son id au modèle pour l'utiliser dans le formulaire
        model.addAttribute("utilisateur", utilisateur);
        // Passer l'article vendu au modèle
        model.addAttribute("articleVendu", new ArticleVendu());
		System.out.println("affichage de vendre");
		return "vendre";
	}
		

	@PostMapping("/vendre")
	// TO DO : Try catch ? 
	public String creerArticle (@ModelAttribute ArticleVendu article) {
		// Récupérer l'utilisateur connecté via Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User utilisateurConnecte = (User) authentication.getPrincipal();  
        String pseudo = utilisateurConnecte.getUsername();
        // Récupérer l'objet utilisateur 
        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
        // Associer l'utilisateur connecté à l'article
        article.setVend(utilisateur);
        
		this.articlesVendusService.ajouterArticle(article);
		return "vendre";
	}
	
	@ModelAttribute("categoriesSession")
	public List<Categorie> getCategoriesSession() {
		return this.articlesVendusService.consulterCategories();
	}
	
	
}
