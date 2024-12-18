package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.CategorieService;
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
	private CategorieService categorieService;

	public EnchereController(CategorieService categorieService, UtilisateurService utilisateurService) {
		this.categorieService = categorieService;	
		this.utilisateurService = utilisateurService;
	}

	@PostMapping("/encheres")
	public String afficherUnObjet(@ModelAttribute Categorie categorie, @RequestParam(required = false) String motCle,
			Model model) {
		List<ArticleVendu> articles = new ArrayList<>();
		if ((motCle != null && !motCle.isEmpty()) || categorie.getNoCategorie() > 0) {
			articles = categorieService.afficheSiContientEtCategorie(motCle, categorie.getNoCategorie());
		}
		model.addAttribute("filtre", new FiltreRecherche());
		model.addAttribute("articles", articles);
		
		model.addAttribute("motCle", motCle);
		model.addAttribute("noCategorie", categorie.getNoCategorie());
		return "accueil";
	}

	@GetMapping("/")
	public String afficherAccueil(Model model) {
		System.out.println("affichage de l'accueil");
		model.addAttribute("filtre", new FiltreRecherche());

		return "accueil";
	}

	@GetMapping("/profil")
	public String afficherProfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		String pseudo = userDetails.getUsername();
		Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		System.out.println("affichage de profil");
		return "profil";
	}

	@GetMapping("/creer")
	public String afficherCreationUtilisateur(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());

		return "view-utilisateur-creation";

	}

	@PostMapping("/creer")
	public String postMethodName(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("utilisateur", utilisateur);

			return "view-utilisateur-creation"; // Retourner le formulaire avec l'erreur
		} else {

			this.utilisateurService.ajouterUtilisateur(utilisateur);

			return "redirect:/accueil";
		}

	}

	@GetMapping("/logout")
	public String afficherLogout() {
		System.out.println("affichage de logout");

		return "/";

	}

	@ModelAttribute("listeCategorie")
	public List<Categorie> getCategorie() {
		System.out.println("charger la liste des catégories");
		return this.categorieService.consulterCategories();
	}

	@GetMapping("/profil/modifier")
	public String afficherFormulaireModification(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		String pseudo = userDetails.getUsername();
		Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);

		model.addAttribute("utilisateur", utilisateur);
		return "modifierProfil";
	}

	@PostMapping("/profil/modifier")
	public String modifierProfil(@Valid Utilisateur utilisateur, BindingResult result,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {

		if (result.hasErrors()) {
			return "modifierProfil";
		}

		String pseudoConnecte = userDetails.getUsername();

		if (!pseudoConnecte.equals(utilisateur.getPseudo())) {
			model.addAttribute("errorMessage", "Vous ne pouvez modifier que vos propres informations.");
			return "modifierProfil";
		}

		utilisateurService.mettreAJourUtilisateur(utilisateur);

		return "redirect:/profil";
	}

}
