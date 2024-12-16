package fr.eni.encheres.bo;

public class Retrait {

	// Attributs d'instance
	private String rue;
	private String code_postal;
	private String ville;
	// Associations
	private ArticleVendu lieuRetrait;
	
	// Constructeur par défaut
	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructeur avec tous les champs
	public Retrait(String rue, String code_postal, String ville) {
		super();
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	// Getters et setters
	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
	
}
