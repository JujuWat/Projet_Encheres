package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;

@Service
public class EnchereServiceImpl implements EnchereService{

	@Override
	public List<ArticleVendu> consulterArticles() {
		// TODO Auto-generated method stub
		return null;
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

}
