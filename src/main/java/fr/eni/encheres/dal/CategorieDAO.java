package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface CategorieDAO {

	// Méthodes :
	Categorie read(int id);
	
	List<Categorie> findAll();
}
