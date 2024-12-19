package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;



public interface UtilisateurDAO {

	Utilisateur read(int noUtilisateur);
	
	List<Utilisateur> findAll();
	
	void ajouterUtilisateur(Utilisateur utilisateur);

	void deleteRelation1(int no_utilisateur);

	void deleteRelation2(int no_utilisateur);

	void deleteRelation3(int no_utilisateur);

	void deleteRelation4(int no_utilisateur);

	void delete(int no_utilisateur);

	Utilisateur read_pseudo(String pseudo);

	void update(Utilisateur utilisateur);

	boolean existPseudo(String pseudo);

	boolean existEmail(String email);
	
}
