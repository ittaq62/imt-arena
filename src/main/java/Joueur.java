/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 26/10/2026                         #
##################################################################
*/

public class Joueur extends Personne {

    // Attributs
    private Statut statut;
    private int victoiresAvecEquipe;

    // Constructeur
    public Joueur(int id, String nom, String prenom, String pseudo,
            String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.statut = Statut.AMATEUR;
        this.victoiresAvecEquipe = 0;
    }

    // Getters
    public Statut getStatut() {
        return statut;
    }

    public int getVictoiresAvecEquipe() {
        return victoiresAvecEquipe;
    }

    // Méthode pour enregistrer une victoire avec l'équipe
    public void enregistrerVictoireAvecEquipe() {
        victoiresAvecEquipe++;

        if (victoiresAvecEquipe >= 3) {
            statut = Statut.PRO;
        }
    }

    // Les différents statuts possibles pour un joueur
    public enum Statut {
        AMATEUR,
        PRO
    }
}