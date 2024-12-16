package fr.eni.encheres.bll;

import java.util.List;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;

public interface EnchereService {
	
	List<ArticleVendu> consulterArticles ();
	List<Categorie> consulterCategories();
	
	ArticleVendu consulterParEtat (int etatVente);
	
}
