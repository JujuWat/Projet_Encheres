package fr.eni.encheres.dal;

import java.util.List;



import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

public interface ArticlesVendusDAO {

	void ajouterArticle(ArticleVendu article);
	
	List<ArticleVendu> findIfContainsAndCategorie(String keyword, int noCategorie);
	
	ArticleVendu findArticleByID(int id);
	
}
