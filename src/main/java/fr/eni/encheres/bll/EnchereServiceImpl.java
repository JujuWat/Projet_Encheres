package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticlesVendusDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.dal.RetraitsDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

@Service
public class EnchereServiceImpl implements EnchereService{
	private EncheresDAO enchereDAO;
	private ArticlesVendusDAO articleVenduDAO;
	private UtilisateurDAO utilisateurDAO;
	private RetraitsDAO retraitDAO;
	
	
	// Constructeur
	public EnchereServiceImpl(EncheresDAO enchereDAO, ArticlesVendusDAO articleVenduDAO, UtilisateurDAO utilisateurDAO, RetraitsDAO retraitDAO) {
		super();
		this.enchereDAO = enchereDAO;
		this.articleVenduDAO = articleVenduDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.retraitDAO = retraitDAO;
	}

	@Override
	public ArticleVendu consulterParEtat(int etatVente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void faireEnchere(Enchere enchere, ArticleVendu article) throws Exception {
		// Vérifiez que l'enchère est valide
	    if (enchere.getMontant_enchere() <= article.getPrixVente()) {
	        throw new Exception("Le montant de l'enchère doit être supérieur au prix actuel de l'article.");
	    }
	    
	    // Vérifier si l'enchère est encore possible (avant la date de fin de l'enchère)
	    if (article.getDateFinEncheres().isBefore(LocalDateTime.now())) {
	        throw new Exception("L'enchère est terminée, vous ne pouvez plus enchérir.");
	    }

	    // Mettre à jour le prix de vente de l'article
	    article.setPrixVente(enchere.getMontant_enchere());
	    articleVenduDAO.mettreAJourPrixArticle(article); // Appeler la méthode pour mettre à jour le prix de l'article dans la BD
	    
	    // Enregistrer l'enchère
	    enchereDAO.creerEnchere(enchere);
	}

	@Override
	public Utilisateur obtenirPlusHautEncherisseur(int noArticle) {
		return enchereDAO.findHighestBidderForArticle(noArticle) ;
	}

}

	


