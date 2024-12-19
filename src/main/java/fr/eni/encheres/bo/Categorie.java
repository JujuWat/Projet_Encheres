package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {

	// Attributs d'instance
	private int noCategorie;
	private String libelle;
	
	// Associations
	private List<ArticleVendu> categorieArticle = new ArrayList<ArticleVendu>();
	
	// Constructeur par défaut
	public Categorie() {
		// TODO Auto-generated constructor stub
	}

	// Constructeur avec tous les champs
	public Categorie(int noCategorie, String libelle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	// Getters et setters
	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
	
}
