package fr.eni.encheres.controller.dto;

import fr.eni.encheres.bo.Categorie;

public class FiltreRecherche {
	
	private String motCle;
	private Categorie categorie;
	public String getMotCle() {
		return motCle;
	}
	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	

}
