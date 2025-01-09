package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;


public interface EnchereService {

	ArticleVendu consulterParEtat (int etatVente);
	
	void faireEnchere(Enchere enchere, ArticleVendu article) throws Exception;
	
	Utilisateur obtenirPlusHautEncherisseur(int noArticle);
	
}
