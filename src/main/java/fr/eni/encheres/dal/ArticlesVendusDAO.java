package fr.eni.encheres.dal;

import java.util.List;



import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

public interface ArticlesVendusDAO {
	List<ArticleVendu> findIfContains(String nomArticle);

	void ajouterArticle(ArticleVendu article);
	
	

	
	
}
