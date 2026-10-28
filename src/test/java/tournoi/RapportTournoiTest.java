/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 28/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package tournoi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

// Tests unitaires des informations accessibles dans le rapport d'un tournoi.
class RapportTournoiTest {

    // Vérifie que les getters retournent les informations enregistrées dans
    // lerapport.
    @Test
    void gettersRetournentLesInformationsDuRapport() {
        LocalDate date = LocalDate.of(2026, 11, 15);

        RapportTournoi rapport = new RapportTournoi(
                "Arena Cup", date, "Street Fighter", 6, 5, "Les Lions", true);

        assertEquals("Arena Cup", rapport.getNomTournoi());
        assertEquals(date, rapport.getDate());
        assertEquals("Street Fighter", rapport.getNomJeu());
        assertEquals(6, rapport.getMatchsPrevus());
        assertEquals(5, rapport.getMatchsJoues());
        assertEquals("Les Lions", rapport.getNomVainqueur());
        assertTrue(rapport.isReussi());
    }
}
