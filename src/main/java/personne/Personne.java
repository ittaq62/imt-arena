/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/

package personne;

// Classe abstraite représentant une personne avec des attributs communs tels que l'identifiant, le nom, le prénom, le pseudo, le genre et le numéro de téléphone.
public abstract class Personne {

    // Attributs
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

    // Méthode hashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
        return result;
    }

    // Méthode equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Personne other = (Personne) obj;
        if (id != other.id)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        if (pseudo == null) {
            if (other.pseudo != null)
                return false;
        } else if (!pseudo.equals(other.pseudo))
            return false;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (telephone == null) {
            if (other.telephone != null)
                return false;
        } else if (!telephone.equals(other.telephone))
            return false;
        return true;
    }

}
