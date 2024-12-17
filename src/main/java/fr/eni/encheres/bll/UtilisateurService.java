package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {

	public void ajouterUtilisateur(Utilisateur utilisateur);

	void supprimerUtilisateur(int no_utilisateur);

	Utilisateur consulterParPseudo(String pseudo);
	
}
