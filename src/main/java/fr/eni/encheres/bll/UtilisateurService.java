package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

public interface UtilisateurService {

	public void ajouterUtilisateur(Utilisateur utilisateur);

	void supprimerUtilisateur(int no_utilisateur);

	Utilisateur consulterParPseudo(String pseudo);

	void mettreAJourUtilisateur(Utilisateur utilisateur);

	boolean existPseudo(String pseudo);

	boolean existEmail(String email);
	
}
