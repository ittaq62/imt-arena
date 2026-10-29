/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package equipe;

import java.util.ArrayList;
import java.util.List;

import personne.Coach;
import personne.Joueur;

// Class Equipe représente une équipe de joueurs avec un coach. Elle permet d'ajouter et de retirer des joueurs, d'enregistrer des victoires et de suivre les points de l'équipe.
public class Equipe implements Classable, Comparable<Equipe> {

    // Attributs
    private String nom;
    private Coach coach;
    private List<Joueur> joueurs;
    private int points;

    // Constructeur
    public Equipe(String nom, Coach coach) {
        this.nom = nom;
        this.coach = coach;
        this.joueurs = new ArrayList<>();
        this.points = 0;
        coach.prendreEquipe(this);
    }

    // Getters
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

    // Méthodes pour ajouter un joueur à l'équipe
    public void ajouterJoueur(Joueur joueur) {
        if (joueurs.contains(joueur)) {
            return;
        }
        joueur.rejoindreEquipe(this);
        joueurs.add(joueur);
    }

    // Méthode pour retirer un joueur de l'équipe
    public void retirerJoueur(Joueur joueur) {
        if (!joueurs.contains(joueur)) {
            return;
        }
        joueurs.remove(joueur);
        joueur.quitterEquipe();
    }

    // Méthode pour enregistrer une victoire de l'équipe
    public void enregistrerVictoire() {
        points += 3;
        coach.enregistrerVictoire();
        for (Joueur joueur : joueurs) {
            joueur.enregistrerVictoireAvecEquipe();
        }
    }

    @Override
    public String toString() {
        return nom + " (" + points + " pts)";
    }

    @Override
    public int compareTo(Equipe autre) {
        if (points != autre.points) {
            return Integer.compare(autre.points, points);
        }
        return nom.compareTo(autre.nom);
    }
}