package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class ModifierArticleController {
	
	// Associations : 
			private ArticleVenduService articleVenduService;
			private UtilisateurService utilisateurService;
			private RetraitService retraitService;
			private EnchereService enchereService;
			
			
	// Constructeur : 
			public ModifierArticleController(ArticleVenduService articleVenduService,
					UtilisateurService utilisateurService, RetraitService retraitService,
					EnchereService enchereService) {
				super();
				this.articleVenduService = articleVenduService;
				this.utilisateurService = utilisateurService;
				this.retraitService = retraitService;
				this.enchereService = enchereService;
			}
	
	
	@GetMapping("/mesarticles")
	public String afficherMesArticles(Model model) {
	    // Récupérer l'utilisateur connecté via Spring Security
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String pseudoUtilisateur = authentication.getName();
	    
	    // Rechercher l'utilisateur dans la base de données
	    Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudoUtilisateur);
	    
	    // Récupération des articles de l'utilisateur connecté
	    List<ArticleVendu> mesArticles = articleVenduService.afficheArticleUtilisateur(utilisateur.getNoUtilisateur());
	    
	    // Ajouter la liste des articles au modèle pour l'affichage
	    model.addAttribute("mesArticles", mesArticles);
	    
	    return "mesarticles";
	}
	
	@GetMapping("/mesarticles/modifier")
	public String afficherModifierArticle(@RequestParam("id") int id, Model model) {
		// Récupérer l'utilisateur connecté via Spring Security
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String pseudoUtilisateur = authentication.getName();
	    
	    // Récupérer l'utilisateur dans la base de données
	    Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudoUtilisateur);
	    
	    // Récupérer l'objet article
	    ArticleVendu article = this.articleVenduService.consulterArticleParID(id);
	    
	    // Vérifier si l'article existe
	    if (article == null) {
	        model.addAttribute("error", "Article non trouvé");
	        return "error"; // Afficher une page d'erreur ou rediriger ailleurs
	    }
	    
	    // Vérifier si l'utilisateur connecté est bien le propriétaire de l'article
	    if (article.getVend().getNoUtilisateur() != utilisateur.getNoUtilisateur()) {
	        model.addAttribute("error", "Vous n'êtes pas autorisé à modifier cet article");
	        return "error"; // Afficher une page d'erreur ou rediriger ailleurs
	    }
	    
	    // Ajouter l'article au modèle pour affichage
	    model.addAttribute("article", article);
	    
	    // Récupérer l'adresse du retrait
	    model.addAttribute("retrait", article.getLieuRetrait());
	    
	    return "modifier"; // Retourner la vue de modification
	}
	
	@PostMapping("/mesarticles/modifier")
	public String modifierArticle(@Valid @ModelAttribute ArticleVendu article, BindingResult bindingResult, Model model) {
	    // Récupérer l'objet article
	    ArticleVendu articleTrouve = this.articleVenduService.consulterArticleParID(article.getNoArticle());
	    	    
	    // Vérifier si l'article existe
	    if (articleTrouve == null) {
	        model.addAttribute("error", "Article non trouvé");
	        return "error"; // Afficher une page d'erreur
	    }

	    // Mettre à jour les informations de l'article
	    articleTrouve.setNomArticle(article.getNomArticle());
	    articleTrouve.setDescription(article.getDescription());
	    articleTrouve.setMiseAPrix(article.getMiseAPrix());
	    articleTrouve.setDateDebutEncheres(article.getDateDebutEncheres());
	    articleTrouve.setDateFinEncheres(article.getDateFinEncheres());
	    
	    // Appel au service pour mettre à jour l'article en base de données
	    this.articleVenduService.mettreAJourArticle(articleTrouve);
	    
	    // Rediriger vers la liste des articles de l'utilisateur
	    return "redirect:/mesarticles";
	}
	
	
	
		

}
