package fr.eni.encheres.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String CREATE_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)"; 
	
	private static final String HIGHEST_ENCHERE = 
	        "SELECT TOP 1 e.montant_enchere, u.no_utilisateur, u.pseudo " +
	                "FROM ENCHERES e " +
	                "JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur " +
	                "WHERE e.no_article = :no_article " +
	                "ORDER BY e.montant_enchere DESC";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
		
	public EncheresDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}



	@Override
	public void creerEnchere(Enchere enchere) {
		 MapSqlParameterSource map = new MapSqlParameterSource();
		 map.addValue("no_utilisateur", enchere.getEncherit().getNoUtilisateur());
		 map.addValue("no_article", enchere.getConcerne().getNoArticle());
		 map.addValue("date_enchere", enchere.getDateEnchere());
		 map.addValue("montant_enchere", enchere.getMontant_enchere());
		
		 jdbcTemplate.update(CREATE_ENCHERE, map);
	}



	@Override
	public Utilisateur findHighestBidderForArticle(int noArticle) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);

        return jdbcTemplate.query(HIGHEST_ENCHERE, params, rs -> {
            if (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                utilisateur.setPseudo(rs.getString("pseudo"));
                return utilisateur;
            }
            return null;
        });

	}
	
}
	
	
	


