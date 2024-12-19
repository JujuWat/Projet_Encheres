package fr.eni.encheres.dal;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	private static final String FIND_CATEGORIE ="SELECT no_categorie, libelle FROM CATEGORIES";
	private static final String FIND_BY_NUMCAT ="SELECT no_categorie, libelle FROM categories WHERE no_categorie = :noCategorie" ;
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Categorie> findAll() {
		
		return this.jdbcTemplate.query(FIND_CATEGORIE, new BeanPropertyRowMapper<>(Categorie.class));
	}

	@Override
	public Categorie read(int noCategorie) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noCategorie", noCategorie);
		return this.jdbcTemplate.queryForObject(FIND_BY_NUMCAT, map, new BeanPropertyRowMapper<>(Categorie.class));
	}

}
