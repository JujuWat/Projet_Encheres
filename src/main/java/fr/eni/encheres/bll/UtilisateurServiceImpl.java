package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;



@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurDAO utilisateurDAO;
	
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}


	@Override
	public void ajouterUtilisateur(Utilisateur utilisateur) {
		
		utilisateurDAO.ajouterUtilisateur(utilisateur);
		
	}

	@Override
	public void supprimerUtilisateur(int no_utilisateur) {
	    utilisateurDAO.delete(no_utilisateur);
	}
	
	@Override
	public Utilisateur consulterParPseudo(String pseudo) {
		Utilisateur u = this.utilisateurDAO.read_pseudo(pseudo);
		
		return u;
		
	}
	
	
	@Override
	public void mettreAJourUtilisateur(Utilisateur utilisateur) {
		System.out.println("test service");
	    utilisateurDAO.update(utilisateur);
	}
	
	 @Override
	    public boolean existPseudo(String pseudo) {
	        return utilisateurDAO.existPseudo(pseudo); // Appel à la méthode du DAO
	    }
	 
	 @Override
	 public boolean existEmail(String email) {
		 return utilisateurDAO.existEmail(email);
	 }
 
	
	 @Override
		public List<Utilisateur> consulterUtilisateurs() {
			return utilisateurDAO.findAll();
			
		}
	 
	 @Override
	 public void toAdmin(int noUtilisateur) {
	     System.out.println("Promotion utilisateur dans le service, ID : " + noUtilisateur);

	     // Vérifier si l'utilisateur est déjà admin
	     if (utilisateurDAO.read_admin(noUtilisateur)) {
	         System.out.println("Utilisateur déjà administrateur : " + noUtilisateur);
	         throw new IllegalStateException("Cet utilisateur est déjà administrateur.");
	     }

	     // Promouvoir l'utilisateur
	     utilisateurDAO.toAdmin(noUtilisateur);
	     System.out.println("Utilisateur promu au rôle ADMIN, ID : " + noUtilisateur);
	 }




	 
}
