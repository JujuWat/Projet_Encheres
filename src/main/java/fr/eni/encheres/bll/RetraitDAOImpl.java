package fr.eni.encheres.bll;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.RetraitsDAO;

@Service
@Primary
public class RetraitDAOImpl implements RetraitService {

	private ArticlesVendusDAO articlesVendusDAO;
    private RetraitsDAO retraitDAO;
    
	public RetraitDAOImpl(ArticlesVendusDAO articlesVendusDAO, RetraitsDAO retraitDAO) {
		super();
		this.articlesVendusDAO = articlesVendusDAO;
		this.retraitDAO = retraitDAO;
	}

	@Override
	public void ajouterRetrait(ArticleVendu articleVendu, Retrait retrait) {
		articlesVendusDAO.ajouterArticle(articleVendu);
		retraitDAO.ajouterRetrait(retrait, articleVendu.getNoArticle());
	}

	
   
    
}
