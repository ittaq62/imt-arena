/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 26/10/2026                         #
##################################################################
*/

public class Coach extends Personne {

    // Attribut
    private int totalVictoires;

    // Constructeur
    public Coach(int id, String nom, String prenom, String pseudo, String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.totalVictoires = 0;
    }

    // Getters
    public int getTotalVictoires() {
        return totalVictoires;
    }

    public void enregistrerVictoire() {
        totalVictoires++;
    }
}