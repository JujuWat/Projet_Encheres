package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;

public interface CategorieService {
	List<Categorie> consulterCategories();
	List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie);
	//List<ArticleVendu> afficheSiContient(String motCle);
}
