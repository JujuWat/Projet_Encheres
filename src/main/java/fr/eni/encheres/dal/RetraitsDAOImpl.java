package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Retrait;

@Repository
public class RetraitsDAOImpl implements RetraitsDAO{

	private static final String CREATE_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (:no_article, :rue, :code_postal, :ville)";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
		
	public RetraitsDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public void ajouterRetrait(Retrait retrait, int noArticle) {
		 MapSqlParameterSource map = new MapSqlParameterSource();
		    map.addValue("no_article", noArticle);  // Utilisation de 'map' ici
		    map.addValue("rue", retrait.getRue());
		    map.addValue("code_postal", retrait.getCode_postal());
		    map.addValue("ville", retrait.getVille());

		    jdbcTemplate.update(CREATE_RETRAIT, map);
		
	}
	
	

}
