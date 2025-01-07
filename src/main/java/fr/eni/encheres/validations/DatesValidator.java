package fr.eni.encheres.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import fr.eni.encheres.bo.ArticleVendu;

import java.time.LocalDateTime;

public class DatesValidator implements ConstraintValidator<ValidDates, ArticleVendu> {

	@Override
	public boolean isValid(ArticleVendu article, ConstraintValidatorContext context) {
	    if (article == null) {
	        return true; // Ne valide rien si l'objet est null
	    }

	    LocalDateTime debut = article.getDateDebutEncheres();
	    LocalDateTime fin = article.getDateFinEncheres();

	    if (debut != null && fin != null && !fin.isAfter(debut)) {
	        // Ajout explicite du message d'erreur
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("La date de fin doit être strictement postérieure à la date de début.")
	        .addPropertyNode("dateFinEncheres") // Lie le message à ce champ
	        .addConstraintViolation();
	        return false;
	    }

	    return true;
	}
}