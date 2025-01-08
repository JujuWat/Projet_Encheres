package fr.eni.encheres.dal;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;


@Repository
public class ArticlesVendusDAOImpl implements ArticlesVendusDAO {

	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :no_utilisateur, :no_categorie)";

	private static final String FIND_IF_CONTAINS_AND_CATEGORIE = "SELECT no_article, image_article, nom_article, date_fin_encheres, prix_initial, prix_vente, pseudo " +
			"FROM ARTICLES_VENDUS a INNER JOIN UTILISATEURS u " +
			"ON a.no_utilisateur = u.no_utilisateur WHERE 1=1 AND date_fin_encheres >= :currentDate" ;
	private static final String FIND_KEYWORD =" AND (:keyword IS NULL OR LOWER(nom_article) LIKE LOWER(CONCAT('%', :keyword, '%'))) ";
	private static final String FIND_CATEGORIE=" AND (:noCategorie = 0 OR a.no_categorie = :noCategorie)";
	private static final String FIND_BY_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_fin_encheres, a.image_article, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, u.pseudo, r.rue, r.ville, r.code_postal, c.libelle FROM ARTICLES_VENDUS a INNER JOIN RETRAITS r ON r.no_article = a.no_article INNER JOIN UTILISATEURS u ON u.no_utilisateur = a.no_utilisateur INNER JOIN CATEGORIES c ON c.no_categorie = a.no_categorie WHERE a.no_article = :no_article";
	private static final String UPDATE_OBJECT = "UPDATE ARTICLES_VENDUS SET \r\n"
			+ "nom_article = :nom_article,\r\n"
			+ "description = :description,\r\n"
			+ "date_debut_encheres = :date_debut_encheres,\r\n"
			+ "date_fin_encheres = :date_fin_encheres,\r\n"
			+ "prix_initial = :prix_initial,\r\n"
			+ "prix_vente = :prix_vente\r\n"
			+ "WHERE no_article = :no_article";
	private static final String UPDATE_PRICE = "UPDATE a\r\n"
			+ "SET a.prix_vente = :prix_vente\r\n"
			+ "FROM ARTICLES_VENDUS a\r\n"
			+ "JOIN ENCHERES e ON e.no_article = a.no_article\r\n"
			+ "WHERE a.no_article = :no_article;";
	
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
	        article.setNoArticle(rs.getInt("no_article"));
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
	
	public ArticleVendu findArticleByID(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", id);
		return this.jdbcTemplate.queryForObject(FIND_BY_ID, map, new ArticleRowMapper());
	}
	
	
	@Override
	public void modifierArticle(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		map.addValue("no_article", article.getNoArticle());
		map.addValue("nom_article", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("dates_debut_encheres", article.getDateDebutEncheres());
		map.addValue("date_fin_encheres", article.getDateFinEncheres());
		map.addValue("image_article", article.getImageUrl());
		map.addValue("prix_initial", article.getMiseAPrix());
		map.addValue("prix_vente", article.getPrixVente());
		
		jdbcTemplate.update(UPDATE_OBJECT, map);
	}
	
	@Override
	public void mettreAJourPrixArticle(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		map.addValue("prix_vente", article.getPrixVente());
		map.addValue("no_article", article.getNoArticle());
		
		jdbcTemplate.update(UPDATE_PRICE, map);
	}
		
	
	class ArticleRowMapper implements RowMapper<ArticleVendu> {
		/* Pour la méthode ArticleVendu findArticleByID(int id), notre requête SQL va chercher pseudo, rue, ville, code postal, or, ils n'y sont pas
		Il faut donc créer notre propre RowMapper */

			@Override
			public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException { // ResultSet contient une ligne résultat de l'exécution de la requête avec des colonnes
				ArticleVendu articleVendu = new ArticleVendu();
				articleVendu.setNoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDateFinEncheres(rs.getObject("date_fin_encheres", LocalDateTime.class));
				articleVendu.setImageUrl(rs.getString("image_article"));
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setDescription(rs.getString("description"));
				
				// Dans ma classe ArticleVendu, je n'ai pas le pseudo, j'ai un objet Utilisateur vend
				Utilisateur vendeur = new Utilisateur();
				vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				vendeur.setPseudo(rs.getString("pseudo"));
				articleVendu.setVend(vendeur);
				
				// Dans ma classe ArticleVendu, je n'ai pas le nom de la catégorie, j'ai un objet Categorie categorieArticle
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				articleVendu.setCategorieArticle(categorie);
				
				// Dans ma classe Article Vendu, je n'ai pas ni la rue, ni le CP, ni la ville, j'ai un objet Retrait lieuRetrait
				Retrait retrait = new Retrait();
				retrait.setRue(rs.getString("rue"));
				retrait.setCode_postal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));
				articleVendu.setLieuRetrait(retrait);
				
				return articleVendu;
			}
		}


	
}



