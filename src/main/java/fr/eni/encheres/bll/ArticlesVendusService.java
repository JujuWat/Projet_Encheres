package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;

public interface ArticlesVendusService {

	void ajouterArticle(ArticleVendu article);
	
	// Consulter genres :
		public List<Categorie> consulterCategories();
		
		// Consulter genre par id : 
		public Categorie consulterCategorieParId(int id);
	
}
