package fr.eni.encheres.dal;

import java.util.List;



import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controller.dto.FiltreRecherche;

public interface ArticlesVendusDAO {

	void ajouterArticle(ArticleVendu article);
	
	List<ArticleVendu> findIfContainsAndCategorie(String keyword, int noCategorie, FiltreRecherche filtre, Integer userId);
	
	ArticleVendu findArticleByID(int id);


	void debiterPrixVente(ArticleVendu article);

	void crediterPrixVente(ArticleVendu article, int nouvelleEnchere);
	
	void modifierArticle(ArticleVendu article);
	
	void mettreAJourPrixArticle(ArticleVendu article);
	
}
