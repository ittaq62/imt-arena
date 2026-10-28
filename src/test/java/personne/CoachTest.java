/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 26/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package personne;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Création de test pour la class coach, pour vérifier que les méthodes fonctionnent correctement, principalement le compteur de victoires et et que le coach commence avec 0 victoire.
class CoachTest {

    @Test
    void coachCommenceAvecZeroVictoire() {
        Coach coach = new Coach(
                3, "Petit", "David", "Dave", "M", "0622222222");

        assertEquals(0, coach.getTotalVictoires());
    }

    @Test
    void coachCompteLesVictoiresEnregistrees() {
        Coach coach = new Coach(
                3, "Petit", "David", "Dave", "M", "0622222222");

        coach.enregistrerVictoire();
        coach.enregistrerVictoire();

        assertEquals(2, coach.getTotalVictoires());
    }
}
