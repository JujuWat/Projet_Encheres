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
import fr.eni.encheres.controller.dto.FiltreRecherche;
import fr.eni.encheres.controller.dto.FiltreRecherche.TypeFiltre;

@Repository
public class ArticlesVendusDAOImpl implements ArticlesVendusDAO {

	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :no_utilisateur, :no_categorie)";

	private static final String FIND_IF_CONTAINS_AND_CATEGORIE = "SELECT a.no_article, image_article, nom_article, date_fin_encheres, prix_initial, prix_vente, pseudo " +
			"FROM ARTICLES_VENDUS a " +
            "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN ENCHERES e ON a.no_article = e.no_article " +
            "WHERE 1=1 ";
	
		
	private static final String FIND_KEYWORD =" AND (:keyword IS NULL OR LOWER(nom_article) LIKE LOWER(CONCAT('%', :keyword, '%'))) ";
	private static final String FIND_CATEGORIE=" AND (:noCategorie = 0 OR a.no_categorie = :noCategorie)";
	private static final String ENCHERES_OUVERTES = " AND a.date_fin_encheres > :currentDate";
    private static final String MES_ENCHERES_EN_COURS = " AND e.no_utilisateur = :userId AND a.date_fin_encheres > :currentDate";
    private static final String MES_ENCHERES_REMPORTEES = " AND e.no_utilisateur = :userId AND a.date_fin_encheres <= :currentDate";
    private static final String MES_VENTES = " AND a.no_utilisateur = :userId";
    private static final String VENTES_EN_COURS = " AND a.date_fin_encheres > :currentDate";
    private static final String VENTES_NON_DEBUTEES = " AND a.date_debut_encheres > :currentDate";
    private static final String VENTES_TERMINEES = " AND a.date_fin_encheres <= :currentDate";
	
	private NamedParameterJdbcTemplate  jdbcTemplate;
	
	// Constructeur jdbcTemplate
	public ArticlesVendusDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private String applyAchatFilters(FiltreRecherche filtre, MapSqlParameterSource params) {
	    StringBuilder filterQuery = new StringBuilder();
	    
	    if (filtre.isEncheresOuvertes()) {
	        filterQuery.append(ENCHERES_OUVERTES);
	    }
	    if (filtre.isMesEncheres()) {
	        filterQuery.append(MES_ENCHERES_EN_COURS);
	    }
	    if (filtre.isEncheresRemportees()) {
	        filterQuery.append(MES_ENCHERES_REMPORTEES);
	    }
	    
	    return filterQuery.toString();
	}

	private String applyVenteFilters(FiltreRecherche filtre, MapSqlParameterSource params) {
	    StringBuilder filterQuery = new StringBuilder();
	    
	    filterQuery.append(MES_VENTES);
	    
	    if (filtre.isVentesEnCours()) {
	        filterQuery.append(VENTES_EN_COURS);
	    }
	    if (filtre.isVentesNonDebutees()) {
	        filterQuery.append(VENTES_NON_DEBUTEES);
	    }
	    if (filtre.isVentesTerminees()) {
	        filterQuery.append(VENTES_TERMINEES);
	    }
	    
	    return filterQuery.toString();
	}

	@Override
	public List<ArticleVendu> findIfContainsAndCategorie( String keyword, int noCategorie, 
			FiltreRecherche filtre, Integer userId) {
		StringBuilder requete = new StringBuilder (FIND_IF_CONTAINS_AND_CATEGORIE);
	    MapSqlParameterSource params = new MapSqlParameterSource();

	    params.addValue("currentDate", LocalDateTime.now());
	    
	    if (keyword != null &&  !keyword.isEmpty()){
	    	System.out.println("je recherche par le mot");
	    	params.addValue("keyword", keyword);
	    	requete.append(FIND_KEYWORD);
	    }
	   
	    if(noCategorie != 0) {
	    	System.out.println("je recherche la categorie");
	    	params.addValue("noCategorie", noCategorie);
	    	requete.append(FIND_CATEGORIE);
	    }
	  
	    //application des filtres utilisateurs si connecté
	    if(userId != null && filtre != null) {
	    	params.addValue("userId", userId);
	    	if (filtre.getTypeFiltre() == TypeFiltre.ACHATS) {
                System.out.println("Application des filtres achats");
                requete.append(applyAchatFilters(filtre, params));
            } else if (filtre.getTypeFiltre() == TypeFiltre.VENTES) {
                System.out.println("Application des filtres ventes");
                requete.append(applyVenteFilters(filtre, params));
            }
        }
	   
	    
	 return jdbcTemplate.query(requete.toString(), params, (rs, yourRowMapper) -> {
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
	
	
}



