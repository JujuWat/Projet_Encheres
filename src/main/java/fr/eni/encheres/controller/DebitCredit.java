package fr.eni.encheres.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

@Controller
public class DebitCredit {

	private UtilisateurService utilisateurService;
	
	
	
	public DebitCredit(UtilisateurService utilisateurService) {
		super();
		this.utilisateurService = utilisateurService;
	}


	@GetMapping("/credit")
	public String afficherFormCredite(@AuthenticationPrincipal UserDetails userDetails, Model model) {
	    // Récupérer le pseudo de l'utilisateur connecté
	    String pseudo = userDetails.getUsername();

	    // Récupérer l'utilisateur correspondant à ce pseudo
	    Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);

	    // Ajouter l'utilisateur comme attribut au modèle
	    model.addAttribute("utilisateur", utilisateur);

	    // Retourner la vue "credit" pour afficher le formulaire
	    return "credit";
	}

	
	@PostMapping("/credit/envoi")
	public String credite(@ModelAttribute Utilisateur utilisateur, 
	                      BindingResult result, 
	                      @AuthenticationPrincipal UserDetails userDetails, 
	                      Model model) {

	    // Vérification des erreurs du formulaire
	    if (result.hasErrors()) {
	        return "credit"; // Retourne à la vue si des erreurs sont détectées
	    }

	    // Récupérer le pseudo de l'utilisateur connecté
	    String pseudo = userDetails.getUsername();

	    // Récupérer l'utilisateur en base de données
	    Utilisateur utilisateurExistant = utilisateurService.consulterParPseudo(pseudo);

	    if (utilisateurExistant == null) {
	        model.addAttribute("error", "Utilisateur introuvable !");
	        return "credit";
	    }

	    // Vérifier si le montant est valide
	    int montant = utilisateur.getCredit();
	    if (montant <= 0) {
	        model.addAttribute("error", "Le montant à créditer doit être supérieur à zéro !");
	        model.addAttribute("utilisateur", utilisateurExistant); // Recharger les données de l'utilisateur
	        return "credit"; // Rester sur la même vue
	    }

	    try {
	        // Ajouter le montant au crédit de l'utilisateur
	        utilisateurService.donnerArgent(utilisateurExistant, montant);

	        // Ajouter un message de succès et mettre à jour l'utilisateur
	        model.addAttribute("success", "Crédit ajouté avec succès !");
	        model.addAttribute("utilisateur", utilisateurExistant);

	    } catch (IllegalStateException e) {
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("utilisateur", utilisateurExistant); // Recharger les données de l'utilisateur
	        return "credit";
	    }

	    // Rester sur la vue mise à jour
	    model.addAttribute("utilisateur", utilisateurExistant);
	    return "redirect:/credit";
	}

	
	
}
