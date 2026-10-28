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

// Class FavoriseMieuxClassee donne plus de chances de gagner à l'équipe qui a le plus de points.
public class FavoriseMieuxClassee implements ChoixVainqueur {

    // Pourcentage de chances de gagner pour l'équipe la mieux classée
    private static final int CHANCE_MIEUX_CLASSEE = 70;

    @Override
    public Equipe choisir(Match match, Random random) {
        Equipe equipe1 = match.getEquipe1();
        Equipe equipe2 = match.getEquipe2();

        // Même nombre de points, une chance sur deux
        if (equipe1.getPoints() == equipe2.getPoints()) {
            if (random.nextBoolean()) {
                return equipe1;
            }
            return equipe2;
        }

        Equipe mieuxClassee = equipe1;
        Equipe autre = equipe2;
        if (equipe2.getPoints() > equipe1.getPoints()) {
            mieuxClassee = equipe2;
            autre = equipe1;
        }

        if (random.nextInt(100) < CHANCE_MIEUX_CLASSEE) {
            return mieuxClassee;
        }
        return autre;
    }
}