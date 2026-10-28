/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package tournoi;

import equipe.Equipe;
import jeu.Jeu;
import personne.Arbitre;

public class Match {

    private Equipe equipe1;
    private Equipe equipe2;
    private Jeu jeu;
    private Arbitre arbitre;
    private Equipe vainqueur;
    private Etat etat;

    public Match(Equipe equipe1, Equipe equipe2, Jeu jeu, Arbitre arbitre) {
        if (equipe1 == equipe2) {
            throw new IllegalArgumentException("Une équipe ne peut pas jouer contre elle-même.");
        }
        if (!arbitre.estQualifiePour(jeu)) {
            throw new IllegalArgumentException("L'arbitre n'est pas qualifié pour ce jeu.");
        }
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.jeu = jeu;
        this.arbitre = arbitre;
        this.vainqueur = null;
        this.etat = Etat.PREVU;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public Arbitre getArbitre() {
        return arbitre;
    }

    public Equipe getVainqueur() {
        return vainqueur;
    }

    public Etat getEtat() {
        return etat;
    }

    public void designerVainqueur(Equipe equipe) {
        if (etat != Etat.PREVU) {
            throw new IllegalStateException("Le match est déjà terminé.");
        }
        if (equipe != equipe1 && equipe != equipe2) {
            throw new IllegalArgumentException("Le vainqueur doit être une des deux équipes du match.");
        }
        this.vainqueur = equipe;
        this.etat = Etat.JOUE;
    }

    public void annuler() {
        if (etat != Etat.PREVU) {
            throw new IllegalStateException("Le match est déjà terminé.");
        }
        this.etat = Etat.ANNULE;
    }

    public enum Etat {
        PREVU,
        JOUE,
        ANNULE
    }
}