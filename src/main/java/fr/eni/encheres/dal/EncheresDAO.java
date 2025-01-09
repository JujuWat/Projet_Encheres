package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDAO {
	
	void creerEnchere(Enchere enchere);
	
	Utilisateur findHighestBidderForArticle(int noArticle);

}
