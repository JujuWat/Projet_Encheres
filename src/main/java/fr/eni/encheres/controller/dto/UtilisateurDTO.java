package fr.eni.encheres.controller.dto;

public class UtilisateurDTO {
    private String pseudo;
    private String ville;

    public UtilisateurDTO(String pseudo, String ville) {
        this.pseudo = pseudo;
        this.ville = ville;
    }

    // Getters et setters
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}

