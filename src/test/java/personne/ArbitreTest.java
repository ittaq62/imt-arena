/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 27/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package personne;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jeu.Jeu;
import jeu.JeuSolo;

// Création de test pour la class arbitre, pour vérifier les qualifications pour un jeu et le compteur de matchs arbitrés.
class ArbitreTest {

    private Arbitre arbitre;
    private Jeu jeu;

    @BeforeEach
    void creerArbitre() {
        arbitre = new Arbitre(1, "Durand", "Emma", "Emmy", "F", "0633333333");
        jeu = new JeuSolo("Street Fighter", 3);
    }

    @Test
    void arbitreCommenceSansQualificationNiMatch() {
        assertFalse(arbitre.estQualifiePour(jeu));
        assertEquals(0, arbitre.getMatchsArbitres());
    }

    @Test
    void arbitreEstQualifieApresAjout() {
        arbitre.ajouterQualification(jeu);

        assertTrue(arbitre.estQualifiePour(jeu));
    }

    @Test
    void qualificationAjouteeUneSeuleFois() {
        arbitre.ajouterQualification(jeu);
        arbitre.ajouterQualification(jeu);

        assertEquals(1, arbitre.getJeuxQualifies().size());
    }

    @Test
    void arbitreCompteSesMatchs() {
        arbitre.enregistrerMatchArbitre();
        arbitre.enregistrerMatchArbitre();

        assertEquals(2, arbitre.getMatchsArbitres());
    }
}
