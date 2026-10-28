/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 27/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package tournoi;

import java.time.LocalDate;

// Class RapportTournoi représente un rapport détaillé d'un tournoi, incluant le nom du tournoi, la date, le jeu joué, le nombre de matchs prévus et joués, le nom du vainqueur et si le tournoi a été réussi. Elle fournit des getters pour accéder à ces informations.
public class RapportTournoi {

    // Attributs
    private String nomTournoi;
    private LocalDate date;
    private String nomJeu;
    private int matchsPrevus;
    private int matchsJoues;
    private String nomVainqueur;
    private boolean reussi;

    // Constructeur
    public RapportTournoi(String nomTournoi, LocalDate date, String nomJeu, int matchsPrevus, int matchsJoues,
            String nomVainqueur, boolean reussi) {
        this.nomTournoi = nomTournoi;
        this.date = date;
        this.nomJeu = nomJeu;
        this.matchsPrevus = matchsPrevus;
        this.matchsJoues = matchsJoues;
        this.nomVainqueur = nomVainqueur;
        this.reussi = reussi;
    }

    // Getters
    public String getNomTournoi() {
        return nomTournoi;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNomJeu() {
        return nomJeu;
    }

    public int getMatchsPrevus() {
        return matchsPrevus;
    }

    public int getMatchsJoues() {
        return matchsJoues;
    }

    public String getNomVainqueur() {
        return nomVainqueur;
    }

    public boolean isReussi() {
        return reussi;
    }
}