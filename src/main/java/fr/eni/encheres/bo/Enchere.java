package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class Enchere {

	// Attributs d'instance
	private LocalDate dateEnchere;
	@NotNull(message = "Le montant de votre enchère ne peut être nulle")
	@Min(value = 1, message = "Le prix de vente doit être au moins 1 crédit")
	private int montant_enchere;
	// Associations
	private ArticleVendu concerne; // L'article concerné par l'enchère
	private Utilisateur encherit;  // L'utilisateur qui a fait l'enchère
	
	// Constructeur par défaut
	public Enchere() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructeur avec tous les champs
	public Enchere(LocalDate dateEnchere, int montant_enchere, ArticleVendu concerne, Utilisateur encherit) {
		super();
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
		this.concerne = concerne;
		this.encherit = encherit;
	}

	// Getters et setters
	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public ArticleVendu getConcerne() {
		return concerne;
	}

	public void setConcerne(ArticleVendu concerne) {
		this.concerne = concerne;
	}

	public Utilisateur getEncherit() {
		return encherit;
	}

	public void setEncherit(Utilisateur encherit) {
		this.encherit = encherit;
	}
	
	
	
	
	
}
