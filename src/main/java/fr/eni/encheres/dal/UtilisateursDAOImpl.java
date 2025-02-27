package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;




@Repository
public class UtilisateursDAOImpl implements UtilisateurDAO {
	
	private static final String FIND_ALL = "Select no_utilisateur,pseudo, nom, prenom , email, telephone, rue, code_postal,ville, credit from Utilisateurs";
	private static final String FIND_ADMIN = "SELECT administrateur FROM Utilisateurs WHERE no_utilisateur = :no_utilisateur";
	private static final String FIND_BY_NO = "Select no_utilisateur AS noUtilisateur, nom, prenom , email, telephone, rue, code_postal,ville, mot_de_passe, credit from  Utilisateurs where no_utilisateur = :no_utilisateur ";
	private static final String FIND_BY_PSEUDO = "Select no_utilisateur AS noUtilisateur,pseudo, nom, prenom , email, telephone, rue, code_postal,ville, mot_de_passe, credit  from  Utilisateurs where pseudo = :pseudo ";
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue,code_postal,ville, mot_de_passe) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue,:code_postal,:ville, :mot_de_passe)";
	private static final String UPDATE_USER = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe WHERE no_utilisateur  = :no_utilisateur";
	private static final String CASCADE1 = "DELETE FROM ENCHERES WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur)";
	private static final String CASCADE2 = "DELETE FROM ENCHERES WHERE no_utilisateur = :no_utilisateur"; 
	private static final String CASCADE3 = "DELETE FROM RETRAITS WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur)";
	private static final String CASCADE4 = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur";
	private static final String CASCADE5 = "DELETE FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	private static final String COUNT_PSEUDO = "select count(*) from UTILISATEURS where pseudo = :pseudo";
	private static final String COUNT_EMAIL = "select count (*) from UTILISATEURS where email = :email"; 
	private static final String CHANGE_ROLE = "UPDATE UTILISATEURS SET administrateur = 1, role = 'ROLE_ADMIN' WHERE no_utilisateur  = :no_utilisateur";
	private static final String DEBIT = "UPDATE Utilisateurs SET credit = credit - :montant WHERE pseudo = :pseudo AND credit >= :montant"; 
	private static final String CREDIT = "UPDATE Utilisateurs SET credit = credit + :versement where pseudo = :pseudo"; 
	
	
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
	public Boolean read_admin(int noUtilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", noUtilisateur);
	    return jdbcTemplate.queryForObject(FIND_ADMIN, params, Boolean.class);
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
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String motDePasseHache = encoder.encode(utilisateur.getMot_de_passe());
		
		MapSqlParameterSource map = new MapSqlParameterSource(); 
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("code_postal", utilisateur.getCode_postal());
		map.addValue("ville", utilisateur.getVille()); 
		map.addValue("mot_de_passe", motDePasseHache); 
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
	
	

	@Override
	public void update(Utilisateur utilisateur) {
		System.out.println("debut update utilisateur");
	   
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("pseudo", utilisateur.getPseudo());
	    params.addValue("nom", utilisateur.getNom());
	    params.addValue("prenom", utilisateur.getPrenom());
	    params.addValue("email", utilisateur.getEmail());
	    params.addValue("telephone", utilisateur.getTelephone());
	    params.addValue("rue", utilisateur.getRue());
	    params.addValue("code_postal", utilisateur.getCode_postal());
	    params.addValue("ville", utilisateur.getVille());
	    params.addValue("no_utilisateur", utilisateur.getNoUtilisateur());
	    params.addValue("mot_de_passe", utilisateur.getMot_de_passe() /*motDePasseHache*/);
	    
	    
	   jdbcTemplate.update(UPDATE_USER, params);
	}
	
	
	@Override
	public boolean existPseudo(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		
		int count = jdbcTemplate.queryForObject(COUNT_PSEUDO, map, Integer.class);
		
		return count> 0 ? true : false ;
	}
	
	
	@Override
	public boolean existEmail(String email) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("email", email);
		
		int count = jdbcTemplate.queryForObject(COUNT_EMAIL, map, Integer.class); 
		return count > 0 ? true : false; 
	}
	

	@Override
	public void toAdmin(int noUtilisateur) {
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("no_utilisateur", noUtilisateur);
	    jdbcTemplate.update(CHANGE_ROLE, params); 
	    
	}

	
	@Override
	public void enleverArgent(Utilisateur utilisateur, int montant) {
	    if (montant <= 0) {
	        throw new IllegalArgumentException("Le montant à débiter doit être supérieur à 0.");
	    }

	    System.out.println("Début du débit d'argent pour l'utilisateur : " + utilisateur.getNoUtilisateur());

	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("montant", montant);
	    params.addValue("no_utilisateur", utilisateur.getNoUtilisateur());

	    int rowsUpdated = jdbcTemplate.update(DEBIT, params);

	    if (rowsUpdated == 0) {
	        throw new IllegalStateException("Débit échoué : crédit insuffisant ou utilisateur introuvable.");
	    }

	    System.out.println("Débit effectué avec succès pour l'utilisateur : " + utilisateur.getPseudo());
	}
	
	@Override
	public void donnerArgent(Utilisateur utilisateur, int versement) {
		
		if (versement <= 0) {
			throw new IllegalArgumentException("Le montant crédité doit être supérieur à 0");
		}
		
		MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("versement", versement);
	    params.addValue("pseudo", utilisateur.getPseudo());
	    
	    int rowsUpdated = jdbcTemplate.update(CREDIT, params);
	    
	    if(rowsUpdated == 0) {
	    	throw new IllegalStateException("Crédit échoué : crédit à 0 impossible ou utilisateur introuvable");
	    }
	    
	    
		
	}
	
}
