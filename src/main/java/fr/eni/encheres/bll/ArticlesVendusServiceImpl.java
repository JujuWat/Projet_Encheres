package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class ArticlesVendusServiceImpl implements ArticlesVendusService {

	// Associations :
	private ArticlesVendusDAO articlesVendusDAO;
	private CategorieDAO categorieDAO;
	
	// Constructeur	:
	public ArticlesVendusServiceImpl(ArticlesVendusDAO articlesVendusDAO, CategorieDAO categorieDAO) {
		super();
		this.articlesVendusDAO = articlesVendusDAO;
		this.categorieDAO = categorieDAO;
	}


	// Méthodes : 
	
	@Override
	public void ajouterArticle(ArticleVendu article) {
		// TO DO : Throws Business Exception ?
		articlesVendusDAO.ajouterArticle(article);
		
	}
	
	@Override
	public List<Categorie> consulterCategories() {
		return this.categorieDAO.findAll();
	}

	@Override
	public Categorie consulterCategorieParId(int id) {
		return this.categorieDAO.read(id);
	}

	
}
