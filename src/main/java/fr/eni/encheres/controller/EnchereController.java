package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import org.springframework.web.bind.annotation.ModelAttribute;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controller.dto.FiltreRecherche;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@SessionAttributes({ "listeCategorie" })
public class EnchereController {


	private UtilisateurService utilisateurService;
	private ArticleVenduService articleVenduService;
	
	public EnchereController(UtilisateurService utilisateurService, ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;	
		this.utilisateurService = utilisateurService;	
	}
	 @ModelAttribute("filtre")
	    public FiltreRecherche getFiltreRecherche() {
	        return new FiltreRecherche();
	    }

	 @GetMapping("/encheres")
	    public String afficherUnObjet(Model model) {
	        // Toujours initialiser un filtre vide
	        FiltreRecherche filtreRecherche = new FiltreRecherche();
	        initializeFiltreRecherche(filtreRecherche);
	        model.addAttribute("filtre", filtreRecherche);
	        return "accueil";
	    }
	@PostMapping("/encheres")
	public String afficherUnObjet(@ModelAttribute FiltreRecherche filtreRecherche, 
									Model model, 
									 @AuthenticationPrincipal UserDetails userDetails) {
		 // Initialisation des valeurs par défaut
        initializeFiltreRecherche(filtreRecherche);
        
     // Récupération de l'ID utilisateur si connecté
        Integer userId = null;
        if (userDetails != null) {
            Utilisateur utilisateur = utilisateurService.consulterParPseudo(userDetails.getUsername());
            if (utilisateur != null) {
                userId = utilisateur.getNoUtilisateur();
            }
        }
//effectuer la recherche
        List<ArticleVendu> articles = articleVenduService.afficheSiContientEtCategorie(
        		filtreRecherche.getMotCle() != null ? filtreRecherche.getMotCle().trim() : "",
                        filtreRecherche.getCategorie() != null ? filtreRecherche.getCategorie().getNoCategorie() : 0,
                        filtreRecherche,
                        userId     		
        		);
        
	    // Ajout des données au modèle
	    model.addAttribute("filtre", filtreRecherche);
	    model.addAttribute("articles", articles);
	    return "accueil";
	}
      
	private void initializeFiltreRecherche(FiltreRecherche filtreRecherche) {
	    
	    if (filtreRecherche == null) {
	        filtreRecherche = new FiltreRecherche();
	    }
	    if (filtreRecherche.getCategorie() == null) {
	        filtreRecherche.setCategorie(new Categorie());
	        filtreRecherche.getCategorie().setNoCategorie(0); 
	    }
	    if (filtreRecherche.getMotCle() == null) {
	        filtreRecherche.setMotCle("");
	    }
	    if (filtreRecherche.getOptionsAchat() == null) {
            filtreRecherche.setOptionsAchat(new ArrayList<>());
        }
        
        if (filtreRecherche.getOptionsVente() == null) {
            filtreRecherche.setOptionsVente(new ArrayList<>());
        }
	}
	
	  @GetMapping("/")
	    public String afficherAccueil(Model model) {
	        // Toujours initialiser un filtre vide
	        FiltreRecherche filtreRecherche = new FiltreRecherche();
	        initializeFiltreRecherche(filtreRecherche);
	        model.addAttribute("filtre", filtreRecherche);
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
