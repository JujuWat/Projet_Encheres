package fr.eni.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bll.ArticleVenduService;

import fr.eni.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {
	
	private ArticleVenduService articleVenduService;
	

	public StringToCategorieConverter(ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;
	}


	@Override
	public Categorie convert(String noCategorie) {
		Categorie categorie = this.articleVenduService.consulterParNoCategorie(Integer.parseInt(noCategorie));
		return categorie;
	}

}