package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import fr.eni.encheres.validations.Creation;
import fr.eni.encheres.validations.Modification;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur implements Serializable, UserDetails {

	private static final long serialVersionUID = 2L;
	
	// Attributs d'instance 
	private int noUtilisateur;
	@NotBlank(message = "Le pseudo est obligatoire")
	@Size(min = 3, max = 30, message = "Le pseudo doit contenir entre 3 et 30 caractères")
	@Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Le pseudo ne doit contenir que des caractères alphanumériques et pas d'espaces", groups = {Creation.class, Modification.class})
	private String pseudo;
	@NotBlank(message = "Le nom est obligatoire")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s-]+$", message = "Le nom ne doit contenir que des lettres",groups = {Creation.class, Modification.class})
	private String nom;
	@NotBlank(message = "Le prénom est obligatoire")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s-]+$", message = "Le prénom ne doit contenir que des lettres", groups = {Creation.class, Modification.class})
	private String prenom;
	@NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", groups = {Creation.class, Modification.class})
	private String email;
	@Pattern(regexp = "^(|[0-9]{10})$", message = "Le téléphone doit contenir exactement 10 chiffres ou être vide" , groups = {Creation.class, Modification.class})
	private String telephone;
	@NotBlank(message = "La rue est obligatoire")
	private String rue;
	@Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres", groups = {Creation.class, Modification.class})
	private String code_postal;
	@NotBlank(message = "La ville est obligatoire")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s-]+$", message = "La ville ne doit contenir que des lettres", groups = {Creation.class, Modification.class})
	private String ville;
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "Le mot de passe ne doit contenir que des caractères alphanumériques", groups = Creation.class)
	private String mot_de_passe;
	private int credit;
	private boolean administrateur=false;
	// Associations
	private List<ArticleVendu> achete = new ArrayList<ArticleVendu>();
	private List<ArticleVendu> vend = new ArrayList<ArticleVendu>();
	private List<Enchere> encherit = new ArrayList<Enchere>();
	
	// Constructeur par défaut
	public Utilisateur() {
	
	}
	
	public Utilisateur(boolean administrateur) {
		super();
		this.administrateur = administrateur;
	}
	
	public Utilisateur(
			@NotBlank(message = "Le pseudo est obligatoire") @Size(min = 3, max = 30, message = "Le pseudo doit contenir entre 3 et 30 caractères") String pseudo) {
		super();
		this.pseudo = pseudo;
	}


	// Constructeur avec tous les champs
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean admnistrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.mot_de_passe = mot_de_passe;
		this.credit = credit;
		this.administrateur = admnistrateur;
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Associez les rôles de l'utilisateur
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.administrateur) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

	// Getters et setters
	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

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

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdmnistrateur() {
		return administrateur;
	}

	public void setAdmnistrateur(boolean admnistrateur) {
		this.administrateur = admnistrateur;
	}

	@Override
	public String getPassword() {
		return this.mot_de_passe;
	}

	@Override
	public String getUsername() {
		return this.pseudo;
	}


	@Override
	public String toString() {
		return String.format(
				"Utilisateur [noUtilisateur=%s, pseudo=%s, nom=%s, prenom=%s, email=%s, telephone=%s, rue=%s, code_postal=%s, ville=%s, mot_de_passe=%s, credit=%s, admnistrateur=%s, achete=%s, vend=%s, encherit=%s]",
				noUtilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit,
				administrateur, achete, vend, encherit);
	}
	
	
	
	
	
}
