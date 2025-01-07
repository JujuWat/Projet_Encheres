package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Controller
public class DetailsController {
	
	// Associations : 
		private ArticleVenduService articleVenduService;
		private UtilisateurService utilisateurService;
		private RetraitService retraitService;
		
	// Constructeur : 
		public DetailsController(ArticleVenduService articleVenduService, UtilisateurService utilisateurService,
				RetraitService retraitService) {
			super();
			this.articleVenduService = articleVenduService;
			this.utilisateurService = utilisateurService;
			this.retraitService = retraitService;
		}
		
	@GetMapping("/encheres/detailsobjet")
	public String afficherDetails(@RequestParam("id") int id ,Model model) {
		System.out.println("Affichage détails de l'objet dont l'id est " +id);
		// Récupérer l'objet article
		ArticleVendu article = this.articleVenduService.consulterArticleParID(id);
		model.addAttribute("detailsarticle", article);
		// Récupérer la catégorie de l'article vendu
		model.addAttribute("libelle", article.getCategorieArticle().getLibelle());
		// Récupérer l'adresse du retrait 
		model.addAttribute("retrait", article.getLieuRetrait());
		// Récupérer l'utilisateur 
		model.addAttribute("utilisateur", article.getVend());
				
		return "detailsobjet";
	}
	
	

}
