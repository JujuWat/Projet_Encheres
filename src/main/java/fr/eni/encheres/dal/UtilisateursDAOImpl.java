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
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue,code_postal,ville, mot_de_passe) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue,:code_postal,:ville, :mot_de_passe)";
		
	
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

	
	
	
}
