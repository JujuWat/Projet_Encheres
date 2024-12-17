package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {

	// Attributs d'instance
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private int etatVente; // Pas de 'etatVente' sur SQL ?
	// Associations
	private Retrait lieuRetrait;
	private Categorie categorieArticle;
	private Utilisateur achete;
	private Utilisateur vend;
	private List<Enchere> concerne;
	
	// Constructeur par défaut
	public ArticleVendu() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructeur avec tous les champs

	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, int prixVente, int etatVente, Retrait lieuRetrait,
			Categorie categorieArticle, Utilisateur achete, Utilisateur vend, List<Enchere> concerne) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
		this.achete = achete;
		this.vend = vend;
		this.concerne = concerne;
	}
	// Getters et setters
		public int getNoArticle() {
			return noArticle;
		}
	public Retrait getLieuRetrait() {
			return lieuRetrait;
		}

		public void setLieuRetrait(Retrait lieuRetrait) {
			this.lieuRetrait = lieuRetrait;
		}

		public Categorie getCategorieArticle() {
			return categorieArticle;
		}

		public void setCategorieArticle(Categorie categorieArticle) {
			this.categorieArticle = categorieArticle;
		}

		public Utilisateur getAchete() {
			return achete;
		}

		public void setAchete(Utilisateur achete) {
			this.achete = achete;
		}

		public Utilisateur getVend() {
			return vend;
		}

		public void setVend(Utilisateur vend) {
			this.vend = vend;
		}

		public List<Enchere> getConcerne() {
			return concerne;
		}

		public void setConcerne(List<Enchere> concerne) {
			this.concerne = concerne;
		}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public int getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(int etatVente) {
		this.etatVente = etatVente;
	}
	
	
	
	
	
}
