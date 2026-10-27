/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 26/10/2026                         #
##################################################################
*/

// Classe abstraite représentant une personne avec des attributs communs tels que l'identifiant, le nom, le prénom, le pseudo, le genre et le numéro de téléphone.
public abstract class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String pseudo;
    private String genre;
    private String telephone;

    // Constructeur de la classe Personne
    public Personne(int id, String nom, String prenom, String pseudo, String genre, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.genre = genre;
        this.telephone = telephone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getGenre() {
        return genre;
    }

    public String getTelephone() {
        return telephone;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Méthode toString
    @Override
    public String toString() {
        return prenom + " \"" + pseudo + "\" " + nom;
    }
}