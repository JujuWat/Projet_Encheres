package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitsDAO {
	
	void ajouterRetrait(Retrait retrait, int noArticle);

}
