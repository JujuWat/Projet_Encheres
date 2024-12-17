package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;




@Repository
public class UtilisateursDAOImpl implements UtilisateurDAO {
	
	private static final String FIND_ALL = "Select no_utilisateur, nom, prenom from Utilisateurs";
	private static final String FIND_BY_NO = "Select no_utilisateur, nom, prenom , email, telephone, rue, code_postal,ville, mot_de_passe, credit  from  Utilisateurs where no_utilisateur = :no_utilisateur ";
	private static final String FIND_BY_PSEUDO = "Select no_utilisateur, nom, prenom , email, telephone, rue, code_postal,ville, mot_de_passe, credit  from  Utilisateurs where pseudo = :pseudo ";
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue,code_postal,ville, mot_de_passe) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue,:code_postal,:ville, :mot_de_passe)";
	private static final String CASCADE1 = "DELETE FROM ENCHERES WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur)";
	private static final String CASCADE2 = "DELETE FROM ENCHERES WHERE no_utilisateur = :no_utilisateur"; 
	private static final String CASCADE3 = "DELETE FROM RETRAITS WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur)";
	private static final String CASCADE4 = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur";
	private static final String CASCADE5 = "DELETE FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	
	private NamedParameterJdbcTemplate jdbcTemplate; 
	
	public UtilisateursDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Utilisateur read(int noUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource(); 
		map.addValue("no_utilisateur", noUtilisateur);
		return jdbcTemplate.queryForObject(FIND_BY_NO, map, new BeanPropertyRowMapper<>(Utilisateur.class));
		
	}
	
	@Override
	public Utilisateur read_pseudo(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map .addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, map, new BeanPropertyRowMapper<>(Utilisateur.class));
		
		
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	@Override
	public void ajouterUtilisateur(Utilisateur utilisateur) {
		
		MapSqlParameterSource map = new MapSqlParameterSource(); 
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("code_postal", utilisateur.getCode_postal());
		map.addValue("ville", utilisateur.getVille()); 
		map.addValue("mot_de_passe", utilisateur.getMot_de_passe()); 
		jdbcTemplate.update(INSERT, map);
	}

	
	@Override
	public void deleteRelation1(int no_utilisateur) {
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", no_utilisateur);
	    jdbcTemplate.update(CASCADE1, params);
	}
	
	@Override
	public void deleteRelation2(int no_utilisateur ) {
		deleteRelation1(no_utilisateur);
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", no_utilisateur);
	    jdbcTemplate.update(CASCADE2, params);
		
	}
	
	@Override
	public void deleteRelation3(int no_utilisateur ) {
		deleteRelation2(no_utilisateur);
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", no_utilisateur);
	    jdbcTemplate.update(CASCADE3, params);
		
	}
	
	@Override
	public void deleteRelation4(int no_utilisateur ) {
		deleteRelation3(no_utilisateur);
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", no_utilisateur);
	    jdbcTemplate.update(CASCADE4, params);
		
	}
	
	@Override
	public void delete(int no_utilisateur ) {
		deleteRelation4(no_utilisateur);
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", no_utilisateur);
	    jdbcTemplate.update(CASCADE5, params);
		
	}
	
	
}
