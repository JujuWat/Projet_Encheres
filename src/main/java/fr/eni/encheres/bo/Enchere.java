package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.List;

public class Enchere {

	// Attributs d'instance
	private LocalDate dateEnchere;
	private int montant_enchere;
	// Associations
	private ArticleVendu concerne;
	private Utilisateur encherit;
	
	// Constructeur par défaut
	public Enchere() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructeur avec tous les champs
	public Enchere(LocalDate dateEnchere, int montant_enchere) {
		super();
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
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
	
	
	
	
}
