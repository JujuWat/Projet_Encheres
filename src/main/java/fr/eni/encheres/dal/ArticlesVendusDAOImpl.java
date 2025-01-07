package fr.eni.encheres.dal;


import java.time.LocalDateTime;
import java.util.List;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class ArticlesVendusDAOImpl implements ArticlesVendusDAO {

	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :no_utilisateur, :no_categorie)";

	private static final String FIND_IF_CONTAINS_AND_CATEGORIE = "SELECT image_article, nom_article, date_fin_encheres, prix_initial, prix_vente, pseudo " +
			"FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u " +
			"ON a.no_utilisateur = u.no_utilisateur WHERE 1=1 AND date_fin_encheres >= :currentDate" ;
	private static final String FIND_KEYWORD =" AND (:keyword IS NULL OR LOWER(nom_article) LIKE LOWER(CONCAT('%', :keyword, '%'))) ";
	private static final String FIND_CATEGORIE=" AND (:noCategorie = 0 OR a.no_categorie = :noCategorie)";
	
	private NamedParameterJdbcTemplate  jdbcTemplate;
	
	// Constructeur jdbcTemplate
	public ArticlesVendusDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<ArticleVendu> findIfContainsAndCategorie(String keyword, int noCategorie) {
		System.out.println("je remonte les infos de ma dal"); 
	    MapSqlParameterSource params = new MapSqlParameterSource();	    params.addValue("currentDate", LocalDateTime.now());
	     
	    String requete = FIND_IF_CONTAINS_AND_CATEGORIE;
	    if (!keyword.isEmpty()){
	    	System.out.println("je recherche par le mot");
	    	params.addValue("keyword", keyword);
	    	requete += FIND_KEYWORD; 
	    }
	    if(noCategorie != 0) {
	    	System.out.println("je recherche la categorie");
	    	params.addValue("noCategorie", noCategorie);
	    	requete += FIND_CATEGORIE;
	    	  
	    }
	    
	   
	    
	 return jdbcTemplate.query(requete, params, (rs, rowNum) -> {
	    	System.out.println("je récupère les données");
	        ArticleVendu article = new ArticleVendu();
	        article.setImageUrl(rs.getString("image_article"));
	        article.setNomArticle(rs.getString("nom_article"));
	        article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
	        article.setMiseAPrix(rs.getInt("prix_initial"));
	        article.setPrixVente(rs.getInt("prix_vente"));
	        article.setVend(new Utilisateur(rs.getString("pseudo")));
	        return article;
	    });
	}



	@Override
	public void ajouterArticle(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("image_article",  article.getImageUrl());
		map.addValue("nom_article",article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("date_debut_encheres", article.getDateDebutEncheres());
		map.addValue("date_fin_encheres", article.getDateFinEncheres());
		map.addValue("prix_initial", article.getMiseAPrix());
		map.addValue("no_utilisateur", article.getVend().getNoUtilisateur());	
		KeyHolder keyHolder = new GeneratedKeyHolder();	
		map.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
		this.jdbcTemplate.update(CREATE, map, keyHolder);
		// Mise à jour de l'ID du film avec celui généré par la BDD
				if (keyHolder != null && keyHolder.getKey() != null) {
					article.setNoArticle(keyHolder.getKey().intValue());
				}	
	}
	
	
}

