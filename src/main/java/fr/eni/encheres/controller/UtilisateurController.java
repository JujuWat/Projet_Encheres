package fr.eni.encheres.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.validations.Creation;
import fr.eni.encheres.validations.Modification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {

	
	private UtilisateurService utilisateurService;
	private PasswordEncoder passwordEncoder;
	
	
	
	
	public UtilisateurController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
		super();
		this.utilisateurService = utilisateurService;
		this.passwordEncoder = passwordEncoder;
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
	public String creerUtilisateur(@Validated(Creation.class) @ModelAttribute Utilisateur utilisateur, 
	                                BindingResult bindingResult, 
	                                Model model) {
	    // Vérifier les erreurs de validation
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("utilisateur", utilisateur);
	        return "view-utilisateur-creation"; // Retourner le formulaire avec les erreurs
	    }
	    
	    // Vérifier si le pseudo est déjà pris
	    if (utilisateurService.existPseudo(utilisateur.getPseudo())) {
	        // Ajouter un message d'erreur
	        bindingResult.rejectValue("pseudo", "error.utilisateur", "Ce pseudo est déjà pris.");
	        
	        model.addAttribute("utilisateur", utilisateur);
	        return "view-utilisateur-creation"; // Retourner le formulaire avec l'erreur
	    }
	    
	    if (utilisateurService.existEmail(utilisateur.getEmail())) {
	        // Ajouter un message d'erreur
	        bindingResult.rejectValue("email", "error.utilisateur", "Cet email est déjà pris.");
	        
	        model.addAttribute("utilisateur", utilisateur);
	        return "view-utilisateur-creation"; // Retourner le formulaire avec l'erreur
	    }
	    
	    // Si tout est bon, ajouter l'utilisateur
	    utilisateurService.ajouterUtilisateur(utilisateur);
	    return "redirect:/accueil"; 
	} 
	
	

	
	 @GetMapping("/profil/modifier")
	    public String afficherFormulaireModification(@AuthenticationPrincipal UserDetails userDetails, Model model) {
	        String pseudo = userDetails.getUsername();
	        Utilisateur utilisateur = utilisateurService.consulterParPseudo(pseudo);

	        model.addAttribute("utilisateur", utilisateur);
	        return "modifierProfil";
	    }

	    
	 @PostMapping("/profil/modifier")
	 public String modifierProfil(@Validated(Modification.class) @ModelAttribute Utilisateur utilisateur,
	                              BindingResult result, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		 
		 

	     if (result.hasErrors()) {
	         return "modifierProfil";
	     }

	     String pseudoConnecte = userDetails.getUsername();
	     Utilisateur utilisateurExistant = utilisateurService.consulterParPseudo(pseudoConnecte);
	     utilisateur.setPseudo(utilisateurExistant.getPseudo());
	     
	     
	     if (!utilisateur.getEmail().equals(utilisateurExistant.getEmail()) 
	             && utilisateurService.existEmail(utilisateur.getEmail())) {
	         result.rejectValue("email", "error.utilisateur", "Cet email est déjà utilisé.");
	         return "modifierProfil";
	     }
	     
	     
	     // Conserver l'ancien mot de passe si le champ est vide
	     if (utilisateur.getMot_de_passe() == null || utilisateur.getMot_de_passe().isEmpty()) {
	         utilisateur.setMot_de_passe(utilisateurExistant.getMot_de_passe());
	     } else {
	         // Hachage du nouveau mot de passe
	         String motDePasseHache = passwordEncoder.encode(utilisateur.getMot_de_passe());
	         utilisateur.setMot_de_passe(motDePasseHache);
	     }

	     utilisateur.setNoUtilisateur(utilisateurExistant.getNoUtilisateur());
	     
	     utilisateurService.mettreAJourUtilisateur(utilisateur);

	     return "redirect:/profil";
	 } 

	
	 
	 @GetMapping("/supprimerUtilisateur")
	 public String supprimerUtilisateur(@RequestParam("no_utilisateur") int id, 
	                                    HttpServletRequest request, 
	                                    HttpServletResponse response) {
	     // Suppression de l'utilisateur
	     utilisateurService.supprimerUtilisateur(id);
	     
	     // Déconnexion automatique de l'utilisateur
	     new SecurityContextLogoutHandler().logout(request, response, 
	             SecurityContextHolder.getContext().getAuthentication());

	     // Redirection vers la page d'accueil
	     return "redirect:/";
	 }
	 

	
	
}
