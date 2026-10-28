/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package personne;

import java.util.HashSet;
import java.util.Set;
import jeu.Jeu;

// Classe représentant un arbitre, qui hérite de la classe abstraite Personne. 
// Un arbitre possède des qualifications pour différents jeux et peut arbitrer des matchs.
public class Arbitre extends Personne implements Qualifiable{

    // Attributs
    private Set<Jeu> jeuxQualifies;
    private int matchsArbitres;

    // Constructeur
    public Arbitre(int id, String nom, String prenom, String pseudo,
            String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.jeuxQualifies = new HashSet<>();
        this.matchsArbitres = 0;
    }

    // Getters
    public Set<Jeu> getJeuxQualifies() {
        return new HashSet<>(jeuxQualifies);
    }

    public int getMatchsArbitres() {
        return matchsArbitres;
    }

    // Méthodes qui permettent d'ajouter des qualifications à un jeu passé en
    // paramètre
    public void ajouterQualification(Jeu jeu) {
        jeuxQualifies.add(jeu);
    }

    // Méthode qui permet de vérifier si l'arbitre est qualifié pour un jeu
    @Override
    public boolean estQualifiePour(Jeu jeu) {
        return jeuxQualifies.contains(jeu);
    }

    // Méthode qui permet d'enregistrer un match arbitré
    public void enregistrerMatchArbitre() {
        matchsArbitres++;
    }
}