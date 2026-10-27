/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 26/10/2026                         #
##################################################################
*/

import java.util.ArrayList;
import java.util.List;

public class Arbitre extends Personne {

    // Attributs
    private List<Jeu> jeuxQualifies;
    private int matchsArbitres;

    public Arbitre(int id, String nom, String prenom, String pseudo,
            String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.jeuxQualifies = new ArrayList<>();
        this.matchsArbitres = 0;
    }

    public List<Jeu> getJeuxQualifies() {
        return new ArrayList<>(jeuxQualifies);
    }

    public int getMatchsArbitres() {
        return matchsArbitres;
    }

    public void ajouterQualification(Jeu jeu) {
        if (!jeuxQualifies.contains(jeu)) {
            jeuxQualifies.add(jeu);
        }
    }

    public boolean estQualifiePour(Jeu jeu) {
        return jeuxQualifies.contains(jeu);
    }

    public void enregistrerMatchArbitre() {
        matchsArbitres++;
    }
}