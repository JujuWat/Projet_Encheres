package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;


public interface ArticleVenduService {
	List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie);
	List<Categorie> consulterCategorie();
	Categorie consulterParNoCategorie(int noCategorie);
	void ajouterArticle(ArticleVendu article);
	 

}
