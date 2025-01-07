package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.validations.ValidDates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ValidDates
public class ArticleVendu {

	// Attributs d'instance
	private int noArticle;
	@NotBlank(message = "Le nom de l'article est obligatoire")
	private String nomArticle;
	@NotBlank(message = "Merci d'ajouter une courte description")
	@Size(min = 10, message = "La description doit contenir au moins 10 caractères")
	private String description;
	@FutureOrPresent(message = "La date de début d'enchère ne peut pas etre inférieure à la date du jour")
	@NotNull(message = "La date ne peut pas etre nulle")
	private LocalDateTime dateDebutEncheres;
	@Future(message = "La date de fin d'enchère ne peut pas etre inférieure à la date du jour")
	@NotNull(message = "La date ne peut pas etre nulle")
	private LocalDateTime dateFinEncheres;
	@NotNull(message = "Le prix ne peut être nul")
	@Min(value = 1, message = "Le prix de vente doit être au moins 1 crédit")
	private int miseAPrix;
	private int prixVente;
	private int etatVente; // Pas de 'etatVente' sur SQL ?
	private String imageUrl;
	// Associations
	@Valid
	private Retrait lieuRetrait;
	@Valid
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


	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, int etatVente, String imageUrl, Retrait lieuRetrait,
			Categorie categorieArticle, Utilisateur achete, Utilisateur vend, List<Enchere> concerne) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.imageUrl = imageUrl;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
		this.achete = achete;
		this.vend = vend;
		this.concerne = concerne;
		
	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
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

	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", imageUrl=" + imageUrl
				+ ", lieuRetrait=" + lieuRetrait + ", categorieArticle=" + categorieArticle + ", achete=" + achete
				+ ", vend=" + vend + ", concerne=" + concerne + "]";
	}
	
	
	
	
	
	
}
