package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.controller.dto.FiltreRecherche;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;

import fr.eni.encheres.dal.RetraitsDAO;

import fr.eni.encheres.dal.UtilisateurDAO;


@Service
public class ArticleVenduServiceImpl implements ArticleVenduService{
	private ArticlesVendusDAO articleVenduDAO;
	private CategorieDAO categorieDAO;
	private RetraitsDAO retraitDAO;
	private UtilisateurDAO utilisateurDAO;


	public ArticleVenduServiceImpl(ArticlesVendusDAO articleVenduDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO, RetraitsDAO retraitDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.retraitDAO = retraitDAO;
	}

	 // Pour les recherches sans authentification
    @Override
    public List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie) {
        return this.articleVenduDAO.findIfContainsAndCategorie(motCle, noCategorie, null, null);
    }
    
	@Override
	public List<ArticleVendu> afficheSiContientEtCategorie(String motCle, int noCategorie, FiltreRecherche filtre, Integer userId)  {
		return this.articleVenduDAO.findIfContainsAndCategorie(motCle,noCategorie, filtre, userId);

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
		retraitDAO.ajouterRetrait(article.getLieuRetrait(), article.getNoArticle());
		
	}

	@Override
	public ArticleVendu consulterArticleParID(int id) {
		ArticleVendu article = this.articleVenduDAO.findArticleByID(id);
		return article;
	}

	@Override 
	public void debiterPrixVente(ArticleVendu article) {
		articleVenduDAO.debiterPrixVente(article);
	}
	
	@Override
	public void crediterPrixVente(ArticleVendu article, int nouvelleEnchere) {
		articleVenduDAO.crediterPrixVente(article, nouvelleEnchere);
		
	}
	
}