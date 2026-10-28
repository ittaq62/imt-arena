/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package personne;

import equipe.Equipe;

// Class Coach est une sous-classe de Personne qui représente un coach. Elle possède des attributs pour le nombre total de victoires et l'équipe qu'il dirige, ainsi que des méthodes pour enregistrer une victoire et prendre en charge une équipe.
public class Coach extends Personne {

    // Attribut
    private int totalVictoires;
    private Equipe equipe;

    // Constructeur
    public Coach(int id, String nom, String prenom, String pseudo, String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.totalVictoires = 0;
        this.equipe = null;
    }

    // Getters
    public int getTotalVictoires() {
        return totalVictoires;
    }

    // Méthode pour enregistrer une victoire
    public void enregistrerVictoire() {
        totalVictoires++;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    // Méthode pour prendre en charge une équipe
    public void prendreEquipe(Equipe equipe) {
        if (equipe == null) {
            throw new IllegalArgumentException("L'équipe ne peut pas être null.");
        }
        if (this.equipe != null && this.equipe != equipe) {
            throw new IllegalStateException(
                    "Le coach dirige déjà une autre équipe.");
        }

        this.equipe = equipe;
    }
}