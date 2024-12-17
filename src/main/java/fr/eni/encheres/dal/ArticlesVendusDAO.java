package fr.eni.encheres.dal;

import java.util.List;



import fr.eni.encheres.bo.ArticleVendu;

public interface ArticlesVendusDAO {
	List<ArticleVendu> findIfContains(String nomArticle);

	
	
	

	
	
}
