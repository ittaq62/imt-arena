/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 28/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package tournoi;

import java.util.Random;

import equipe.Equipe;

// Interface ChoixVainqueur représente une façon de désigner le vainqueur d'un match (pattern Stratégie). On peut changer de façon de faire sans toucher à la classe Match.
public interface ChoixVainqueur {
    Equipe choisir(Match match, Random random);
}