package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.controller.dto.FiltreRecherche;


public interface ArticleVenduService {
	
	
	List<Categorie> consulterCategorie();
	Categorie consulterParNoCategorie(int noCategorie);
	void ajouterArticle(ArticleVendu article);

	List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie, FiltreRecherche filtre,
			Integer userId);
	List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie);

	ArticleVendu consulterArticleParID(int id);

	void mettreAJourArticle(ArticleVendu article);
	void mettreAJourPrixArticle(ArticleVendu article);

	void debiterPrixVente(ArticleVendu article);
	void crediterPrixVente(ArticleVendu article, int nouvelleEnchere);



}
