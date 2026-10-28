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

// Class TirageEquitable donne une chance sur deux à chaque équipe du match.
public class TirageEquitable implements ChoixVainqueur {

    @Override
    public Equipe choisir(Match match, Random random) {
        if (random.nextBoolean()) {
            return match.getEquipe1();
        }
        return match.getEquipe2();
    }
}