package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;
@Service
public class CategorieServiceImpl implements CategorieService{
	private CategorieDAO categorieDAO;
	private ArticlesVendusDAO articleVenduDAO;


	public CategorieServiceImpl(CategorieDAO categorieDAO, ArticlesVendusDAO articleVenduDAO) {
		this.categorieDAO = categorieDAO;
		this.articleVenduDAO = articleVenduDAO;
	}

	@Override
	public List<Categorie> consulterCategories() {
		return this.categorieDAO.findAll();
	}

	@Override
	public List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie) {
		
		return this.articleVenduDAO.findIfContainsAndCategorie(motCle,noCategorie);
	}
	//@Override
		//public List<ArticleVendu> afficheSiContient(String motCle) {
		//	return this.articleVenduDAO.findIfContains(motCle);
		//}
}
