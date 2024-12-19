package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService{
	private ArticlesVendusDAO articleVenduDAO;
	private CategorieDAO categorieDAO;


	public ArticleVenduServiceImpl(ArticlesVendusDAO articleVenduDAO, CategorieDAO categorieDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
	}


	@Override
	public List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie) {
		return this.articleVenduDAO.findIfContainsAndCategorie(motCle,noCategorie);

}
	

	@Override
	public List<Categorie> consulterCategorie() {
		return this.categorieDAO.findAll();
	}


	@Override
	public Categorie consulterParNoCategorie(int noCategorie) {
		return categorieDAO.read(noCategorie);
	}

	@Override
	public void ajouterArticle(ArticleVendu article) {
		// TO DO : Throws Business Exception ?
		articleVenduDAO.ajouterArticle(article);
		
	}

}