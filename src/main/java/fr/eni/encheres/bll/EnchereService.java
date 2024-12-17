package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;


public interface EnchereService {
	

	List<Categorie> consulterCategories();
	
	ArticleVendu consulterParEtat (int etatVente);
	List<ArticleVendu> afficheSiContient(String motCle);

	
}
