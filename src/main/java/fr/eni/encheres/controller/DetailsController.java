package fr.eni.encheres.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class DetailsController {
	
	// Associations : 
		private ArticleVenduService articleVenduService;
		private UtilisateurService utilisateurService;
		private RetraitService retraitService;
		private EnchereService enchereService;
		
	// Constructeur : 
		public DetailsController(ArticleVenduService articleVenduService, UtilisateurService utilisateurService,
				RetraitService retraitService, EnchereService enchereService) {
			super();
			this.articleVenduService = articleVenduService;
			this.utilisateurService = utilisateurService;
			this.retraitService = retraitService;
			this.enchereService = enchereService;
		}
		
		@GetMapping("/encheres/detailsobjet")
		public String afficherDetails(@RequestParam("id") int id, Model model) {
		    // Récupérer l'article
		    ArticleVendu article = this.articleVenduService.consulterArticleParID(id);
		    
		    // Récupérer l'utilisateur connecté
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String pseudoConnecte = authentication.getName();
		    
		    // Vérifier si l'enchère est terminée
		    boolean enchereTerminee = article.getDateFinEncheres().isBefore(LocalDateTime.now());
		    
		    // Récupérer le plus haut enchérisseur
		    Utilisateur plusHautEncherisseur = enchereService.obtenirPlusHautEncherisseur(id);
		    
		    // Ajouter les informations communes au modèle
		    model.addAttribute("detailsarticle", article);
		    model.addAttribute("libelle", article.getCategorieArticle().getLibelle());
		    model.addAttribute("retrait", article.getLieuRetrait());
		    model.addAttribute("utilisateur", article.getVend());
		    
		    if (plusHautEncherisseur != null) {
		        model.addAttribute("plusHautEncherisseurPseudo", plusHautEncherisseur.getPseudo());
		    } else {
		        model.addAttribute("plusHautEncherisseurPseudo", "Aucun enchérisseur pour cet article");
		    }
		    
		    // Si l'enchère est terminée, rediriger vers la page appropriée
		    if (enchereTerminee) {
		        if (plusHautEncherisseur != null && plusHautEncherisseur.getPseudo().equals(pseudoConnecte)) {
		            return "vente-remportee"; // L'utilisateur connecté a gagné
		        } else {
		            return "enchere-remportee-autre"; // Un autre utilisateur a gagné
		        }
		    }
		    
		    // Si l'enchère n'est pas terminée, afficher la page normale
		    return "detailsobjet";
		}
	@PostMapping("/encheres/detailsobjet")
	public String faireEnchere(@Valid @RequestParam("id") int id, @RequestParam("enchere") int nouvelleEnchere, Model model) {
		try {
			
	    // Récupérer l'article
	    ArticleVendu article = this.articleVenduService.consulterArticleParID(id);
	    
	        // Associer l'enchère à l'utilisateur connecté
	        	// Récupérer l'utilisateur connecté via Spring Security
	        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        	User utilisateurConnecte = (User) authentication.getPrincipal();  
	        	String pseudo = utilisateurConnecte.getUsername();
	        	Utilisateur utilisateur = this.utilisateurService.consulterParPseudo(pseudo); 
	        Enchere enchere = new Enchere();
	        enchere.setDateEnchere(LocalDate.now()); 
	        enchere.setMontant_enchere(nouvelleEnchere);
	        enchere.setConcerne(article); // Association de l'enchère à cet article
	        enchere.setEncherit(utilisateur); // Association de l'enchère à l'utilisateur
	        
	        this.enchereService.faireEnchere(enchere, article);
	        	        
	        model.addAttribute("successMessage", "Votre enchère a été enregistrée avec succès !");
		} catch (Exception e) {
			model.addAttribute("successMessage", "Une erreur s'est produite : " + e.getMessage());
			e.printStackTrace();
		}
  
	    // Recharge la page des détails avec les données mises à jour
	    return "redirect:/encheres/detailsobjet?id=" + id;
	}
	
	
	
}
