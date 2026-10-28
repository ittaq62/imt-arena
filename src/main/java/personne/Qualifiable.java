/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package personne;

import jeu.Jeu;

// Interface Qualifiable est utilisée pour déterminer si une personne est qualifiée pour participer à un jeu spécifique. Elle définit une méthode estQualifiePour qui prend un objet Jeu en paramètre et retourne un booléen indiquant si la personne est qualifiée ou non.
public interface Qualifiable {
    boolean estQualifiePour(Jeu jeu);
}
