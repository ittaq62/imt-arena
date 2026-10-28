/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/

package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import equipe.Equipe;
import personne.Joueur;

public class JeuEquipe extends Jeu {

    private final int nbJoueursParEquipe;

    // Constructeur
    public JeuEquipe(String nom, int nombreManches, int nbJoueursParEquipe) {
        super(nom, nombreManches);
        if (nbJoueursParEquipe < 3 || nbJoueursParEquipe > 5) {
            throw new IllegalArgumentException("Le nombre de joueurs doit être compris entre 3 et 5 pour ce jeu.");
        }
        this.nbJoueursParEquipe = nbJoueursParEquipe;
    }

    // Redéfinition des méthodes
    @Override
    public int getNombreJoueursRequis() {
        return nbJoueursParEquipe;
    }

    // Mélange une copie de la liste des joueurs
    // puis prend le nombre de joueurs demandé par le jeu
    @Override
    public List<Joueur> aligner(Equipe equipe, Random random) {
        List<Joueur> joueurs = new ArrayList<>(equipe.getJoueurs());
        if (joueurs.size() < nbJoueursParEquipe) {
            throw new IllegalStateException("L'équipe n'a pas assez de joueurs pour ce jeu.");
        }
        Collections.shuffle(joueurs, random);
        return new ArrayList<>(joueurs.subList(0, nbJoueursParEquipe));
    }

    // Redéfinition des méthode hashCode et Equals
    // pour prendre en compte le nombre de joueurs par équipe
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + nbJoueursParEquipe;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        JeuEquipe other = (JeuEquipe) obj;
        return nbJoueursParEquipe == other.nbJoueursParEquipe;
    }
}
