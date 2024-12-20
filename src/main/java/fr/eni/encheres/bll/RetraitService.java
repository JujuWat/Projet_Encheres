package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.RetraitsDAO;

public interface RetraitService {

	public void ajouterRetrait(ArticleVendu articleVendu, Retrait retrait);
	
}
