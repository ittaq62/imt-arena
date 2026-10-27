/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/

public class Joueur extends Personne {

    // Attributs
    private Statut statut;
    private int victoiresAvecEquipe;
    private Equipe equipe;

    // Constructeur
    public Joueur(int id, String nom, String prenom, String pseudo,
            String genre, String telephone) {
        super(id, nom, prenom, pseudo, genre, telephone);
        this.statut = Statut.AMATEUR;
        this.victoiresAvecEquipe = 0;
        this.equipe = null;
    }

    // Getters
    public Statut getStatut() {
        return statut;
    }

    public int getVictoiresAvecEquipe() {
        return victoiresAvecEquipe;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    // Méthode pour enregistrer une victoire avec l'équipe
    public void enregistrerVictoireAvecEquipe() {
        victoiresAvecEquipe++;

        if (victoiresAvecEquipe >= 3) {
            statut = Statut.PRO;
        }
    }

    // Méthode pour rejoindre une équipe
    public void rejoindreEquipe(Equipe equipe) {
        if (equipe == null) {
            throw new IllegalArgumentException("L'équipe ne peut pas être null.");
        }
        if (this.equipe != null && this.equipe != equipe) {
            throw new IllegalStateException(
                    "Le joueur appartient déjà à une autre équipe.");
        }

        if (this.equipe != equipe) {
            this.equipe = equipe;
            this.victoiresAvecEquipe = 0;
        }
    }

    // Méthode pour quitter l'équipe (remise du compteur de victoires à 0)
    public void quitterEquipe() {
        this.equipe = null;
        this.victoiresAvecEquipe = 0;
    }

    // Les différents statuts possibles pour un joueur
    public enum Statut {
        AMATEUR,
        PRO
    }
}