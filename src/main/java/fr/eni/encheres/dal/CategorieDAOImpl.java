package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

	// Attributs de classe : 
		private static final String FIND_BY_ID = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie=:idCategorie"; 
		private static final String FIND_ALL = "SELECT no_categorie, libelle FROM CATEGORIES";
	// Attributs d'instance : 
		private NamedParameterJdbcTemplate jdbcTemplate; // C'est un bean Spring
	// Constructeur pour instancier le bean Spring
		public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
			super();
			this.jdbcTemplate = jdbcTemplate;
		}

	// Méthodes :
		
		@Override
		public Categorie read(int id) {
			MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("idCategorie", id); // La première valeur c'est le nom, le deuxième c'est la valeur qui doit la remplacer
			return this.jdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<>(Categorie.class)); // RowMapper qui convertit notre tableau en objet Genre
		}

		@Override
		public List<Categorie> findAll() {
			return this.jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper(Categorie.class)); // Pas de QueryForObject ici car on veut un tableau
		}
	
}
