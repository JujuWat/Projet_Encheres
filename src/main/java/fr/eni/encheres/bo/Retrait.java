package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Retrait {

	// Attributs d'instance
	@NotBlank(message = "Merci d'ajouter votre rue")
	private String rue;
	@NotBlank(message = "Merci d'ajouter votre code postal")
	@Size(min = 5, message = "Le code postal doit contenir 5 caractères")
	@Size(max = 5, message = "Le code postal doit contenir 5 caractères")
	@Pattern(regexp = "^[0-9]{5}$", message = "Le code postal ne doit comporter que des chiffres")
	private String code_postal;
	@NotBlank(message = "Merci d'ajouter votre ville")
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
