/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/
package jeu;

import java.util.List;
import java.util.Random;

import equipe.Equipe;
import personne.Joueur;

public abstract class Jeu {

    // Attributs
    private final String nom;
    private final int nombreManches;

    // Constructeur
    protected Jeu(String nom, int nombreManches) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du jeu est obligatoire.");
        }
        if (nombreManches <= 0) {
            throw new IllegalArgumentException("Le nombre de manches doit être supérieur à 0.");
        }
        this.nom = nom;
        this.nombreManches = nombreManches;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getNombreManches() {
        return nombreManches;
    }

    // Méthodes abstraites
    public abstract int getNombreJoueursRequis();

    public abstract List<Joueur> aligner(Equipe equipe, Random random);

    // Méthode hashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + nombreManches;
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
        Jeu other = (Jeu) obj;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (nombreManches != other.nombreManches)
            return false;
        return true;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Jeu [nom=" + nom + ", nombreManches=" + nombreManches + "]";
    }

}
