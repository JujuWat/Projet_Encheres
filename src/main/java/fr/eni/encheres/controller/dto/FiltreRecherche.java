package fr.eni.encheres.controller.dto;


import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;

public class FiltreRecherche {
	public enum TypeFiltre {
        TOUS,
        ACHATS,
        VENTES
    }
	
	public enum OptionAchat {
        ENCHERES_OUVERTES,
        MES_ENCHERES_EN_COURS,
        MES_ENCHERES_REMPORTEES
    }

    public enum OptionVente {
        VENTES_EN_COURS,
        VENTES_NON_DEBUTEES,
        VENTES_TERMINEES
    }
	
	
	
    private String motCle;
    private Categorie categorie;
    private TypeFiltre typeFiltre = TypeFiltre.TOUS;
    private List<OptionAchat> optionsAchat = new ArrayList<>();
    private List<OptionVente> optionsVente = new ArrayList<>();

	
		public String getMotCle() {
			return motCle;
		}
		public void setMotCle(String motCle) {
			this.motCle = motCle;
		}
		public Categorie getCategorie() {
			return categorie;
		}
		public void setCategorie(Categorie categorie) {
			this.categorie = categorie;
		}
		public TypeFiltre getTypeFiltre() {
			return typeFiltre;
		}
		public void setTypeFiltre(TypeFiltre typeFiltre) {
			this.typeFiltre = typeFiltre;
		}
		public List<OptionAchat> getOptionsAchat() {
			return optionsAchat;
		}
		public void setOptionsAchat(List<OptionAchat> optionsAchat) {
			this.optionsAchat = optionsAchat;
		}
		public List<OptionVente> getOptionsVente() {
			return optionsVente;
		}
		public void setOptionsVente(List<OptionVente> optionsVente) {
			this.optionsVente = optionsVente;
		}
	
	    public boolean isEncheresOuvertes() {
	        return optionsAchat.contains(OptionAchat.ENCHERES_OUVERTES);
	    }

	    public boolean isMesEncheres() {
	        return optionsAchat.contains(OptionAchat.MES_ENCHERES_EN_COURS);
	    }

	    public boolean isEncheresRemportees() {
	        return optionsAchat.contains(OptionAchat.MES_ENCHERES_REMPORTEES);
	    }

	    public boolean isVentesEnCours() {
	        return optionsVente.contains(OptionVente.VENTES_EN_COURS);
	    }

	    public boolean isVentesNonDebutees() {
	        return optionsVente.contains(OptionVente.VENTES_NON_DEBUTEES);
	    }

	    public boolean isVentesTerminees() {
	        return optionsVente.contains(OptionVente.VENTES_TERMINEES);
	    }

	
}
