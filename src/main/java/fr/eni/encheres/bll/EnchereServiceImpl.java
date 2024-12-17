package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class EnchereServiceImpl implements EnchereService{
	private ArticlesVendusDAO articleVenduDAO;
	private CategorieDAO categorieDAO;
	
	

	public EnchereServiceImpl(ArticlesVendusDAO articleVenduDAO, CategorieDAO categorieDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
	}

	@Override
	public List<Categorie> consulterCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVendu consulterParEtat(int etatVente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> afficheSiContient(String motCle) {
		return this.articleVenduDAO.findIfContains(motCle);
	}

}
