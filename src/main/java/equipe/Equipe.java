/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package equipe;

import java.util.ArrayList;
import java.util.List;

import personne.Coach;
import personne.Joueur;

public class Equipe implements Classable {

    private String nom;
    private Coach coach;
    private List<Joueur> joueurs;
    private int points;

    public Equipe(String nom, Coach coach) {
        this.nom = nom;
        this.coach = coach;
        this.joueurs = new ArrayList<>();
        this.points = 0;
        coach.prendreEquipe(this);
    }

    public String getNom() {
        return nom;
    }

    public Coach getCoach() {
        return coach;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs);
    }

    public int getPoints() {
        return points;
    }

    public void ajouterJoueur(Joueur joueur) {
        if (joueurs.contains(joueur)) {
            return;
        }
        joueur.rejoindreEquipe(this);
        joueurs.add(joueur);
    }

    public void retirerJoueur(Joueur joueur) {
        if (!joueurs.contains(joueur)) {
            return;
        }
        joueurs.remove(joueur);
        joueur.quitterEquipe();
    }

    public void enregistrerVictoire() {
        points += 3;
        coach.enregistrerVictoire();
        for (Joueur joueur : joueurs) {
            joueur.enregistrerVictoireAvecEquipe();
        }
    }
}