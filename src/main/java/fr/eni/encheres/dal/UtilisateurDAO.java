package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;



public interface UtilisateurDAO {

	Utilisateur read(int noUtilisateur);
	
	List<Utilisateur> findAll();
	
	void ajouterUtilisateur(Utilisateur utilisateur);
	
}
