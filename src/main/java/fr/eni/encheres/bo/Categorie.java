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
		
	}

	public Categorie(int noCategorie, String libelle, List<ArticleVendu> categorieArticle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.categorieArticle = categorieArticle;
	}

	//constructeur sans no de categorie
	public Categorie(String libelle, List<ArticleVendu> categorieArticle) {
		this.libelle = libelle;
		this.categorieArticle = categorieArticle;
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
