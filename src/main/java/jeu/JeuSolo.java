/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package jeu;

import java.util.List;
import java.util.Random;

import equipe.Equipe;
import personne.Joueur;

public class JeuSolo extends Jeu {

    // Constructeur
    public JeuSolo(String nom, int nombreManches) {
        super(nom, nombreManches);
    }

    // Redéfinition des méthodes
    @Override
    public int getNombreJoueursRequis() {
        return 1;
    }

    // Prend un joueur au hasard dans l'équipe
    @Override
    public List<Joueur> aligner(Equipe equipe, Random random) {
        List<Joueur> joueurs = equipe.getJoueurs();
        if (joueurs.isEmpty()) {
            throw new IllegalStateException("L'équipe n'a aucun joueur.");
        }
        int index = random.nextInt(joueurs.size());
        return List.of(joueurs.get(index));
    }
}
