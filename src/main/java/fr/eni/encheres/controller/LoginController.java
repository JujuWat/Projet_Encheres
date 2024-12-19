package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	// Association : 
	private UtilisateurDAO utilisateurDAO;
	
	// Constructeur : 
	public LoginController(UtilisateurDAO utilisateurDAO) {
		super();
		this.utilisateurDAO = utilisateurDAO;
	}
		
	
	@GetMapping("/login")
	public String affichageLogin() {
		return "login";
	}
	

}
