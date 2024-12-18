package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticlesVendusDAOImpl implements ArticlesVendusDAO {
	private static final String FIND_IF_CONTAINS = "SELECT nom_article, date_fin_encheres, prix_vente, pseudo "
			+ "FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u "
			+ "ON a.no_utilisateur =  u.no_utilisateur "
			+ "WHERE LOWER (nom_article) LIKE LOWER(CONCAT('%',:keyword,'%' ))";
	private static final String FIND_IF_CONTAINS_AND_CATEGORIE = "SELECT nom_article, date_fin_encheres, prix_vente, pseudo " +
					"FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u " +
					"ON a.no_utilisateur = u.no_utilisateur " +
					"WHERE (:keyword IS NULL OR LOWER(nom_article) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
					"AND (:noCategorie = 0 OR a.no_categorie = :noCategorie)";
	
	private NamedParameterJdbcTemplate  jdbcTemplate;
	
	//constructor jdbcTemplate
	public ArticlesVendusDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ArticleVendu> findIfContains(String keyword){
		MapSqlParameterSource saisieRecherche = new MapSqlParameterSource();
	saisieRecherche.addValue("keyword",keyword);
	
	return jdbcTemplate.query(FIND_IF_CONTAINS, saisieRecherche,(rs,rowNum)->{
		// Mapping des résultats vers un objet ArticleVendu
        ArticleVendu article = new ArticleVendu();
        article.setNomArticle(rs.getString("nom_article"));
        article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
        article.setPrixVente(rs.getInt("prix_vente"));
        //article.setVend(rs.getString("pseudo"));      
        
        return article;	
	});
	}
	
	@Override
	public List<ArticleVendu> findIfContainsAndCategorie(String keyword, int noCategorie) {
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("keyword", keyword);
	    params.addValue("noCategorie", noCategorie);

	    return jdbcTemplate.query(FIND_IF_CONTAINS_AND_CATEGORIE, params, (rs, rowNum) -> {
	        ArticleVendu article = new ArticleVendu();
	        article.setNomArticle(rs.getString("nom_article"));
	        article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	        article.setPrixVente(rs.getInt("prix_vente"));
	        article.setVend(new Utilisateur(rs.getString("pseudo")));
	        return article;
	    });
	}
}//fin de classe