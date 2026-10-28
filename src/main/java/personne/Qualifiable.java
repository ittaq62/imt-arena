/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/

package personne;

import jeu.Jeu;

public interface Qualifiable {
    boolean estQualifiePour(Jeu jeu);
}
