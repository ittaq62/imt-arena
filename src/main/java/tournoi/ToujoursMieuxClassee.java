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

// Class ToujoursMieuxClassee fait toujours gagner l'équipe la mieux classée (points, puis nom). Le résultat est prévisible, ce qui est pratique pour les tests.
public class ToujoursMieuxClassee implements ChoixVainqueur {

    @Override
    public Equipe choisir(Match match, Random random) {
        if (match.getEquipe1().compareTo(match.getEquipe2()) <= 0) {
            return match.getEquipe1();
        }
        return match.getEquipe2();
    }
}