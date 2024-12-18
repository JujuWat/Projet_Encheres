package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;


public interface EnchereService {

	ArticleVendu consulterParEtat (int etatVente);
	
}
