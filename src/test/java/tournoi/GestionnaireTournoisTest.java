/* Tests unitaires de la planification et du démarrage des tournois. */
package tournoi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import equipe.Equipe;
import jeu.Jeu;
import jeu.JeuSolo;
import personne.Arbitre;
import personne.Coach;
import personne.Joueur;

class GestionnaireTournoisTest {

    private static Tournoi creerTournoiAvecArbitre(
            String nom, LocalDate date, Jeu jeu, Arbitre arbitre, int idDepart) {
        Tournoi tournoi = new Tournoi(nom, jeu, date, 1500.0, 1);

        for (int i = 0; i < 4; i++) {
            int id = idDepart + i;
            Coach coach = new Coach(
                    id, "Coach", "Prenom", "Coach" + id, "M", "0600000000");
            Equipe equipe = new Equipe("Equipe" + id, coach);
            equipe.ajouterJoueur(new Joueur(
                    1000 + id, "Joueur", "Prenom", "Joueur" + id, "M", "0600000000"));
            tournoi.inscrireEquipe(equipe);
        }

        tournoi.ajouterArbitre(arbitre);
        return tournoi;
    }

    // Vérifie l'ajout d'un tournoi planifié et le refus de null ou d'un doublon.
    @Test
    void planifierAjouteLeTournoiEtRefuseNullOuDoublon() {
        GestionnaireTournois gestionnaire = new GestionnaireTournois(0, 0, 42L);
        Tournoi tournoi = creerTournoi("Arena Cup", LocalDate.of(2026, 11, 15));

        try {
            gestionnaire.planifier(tournoi);

            assertEquals(1, gestionnaire.getTournoisPlanifies().size());
            assertSame(tournoi, gestionnaire.getTournoisPlanifies().get(0));
            assertThrows(IllegalArgumentException.class, () -> gestionnaire.planifier(null));
            assertThrows(IllegalArgumentException.class, () -> gestionnaire.planifier(tournoi));
        } finally {
            gestionnaire.fermer();
        }
    }

    // Vérifie que le gestionnaire démarre le tournoi dont la date est la plus
    // proche.
    @Test
    void demarrerProchainChoisitLeTournoiLePlusProche() {
        GestionnaireTournois gestionnaire = new GestionnaireTournois(0, 0, 42L);
        Tournoi tournoiLointain = creerTournoi("Tournoi lointain", LocalDate.of(2026, 12, 1));
        Tournoi tournoiProche = creerTournoi("Tournoi proche", LocalDate.of(2026, 11, 1));
        gestionnaire.planifier(tournoiLointain);
        gestionnaire.planifier(tournoiProche);

        try {
            Tournoi demarre = gestionnaire.demarrerProchain();

            assertSame(tournoiProche, demarre);
            assertEquals(6, demarre.getMatchs().size());
            assertEquals(1, gestionnaire.getTournoisPlanifies().size());
            assertSame(tournoiLointain, gestionnaire.getTournoisPlanifies().get(0));
        } finally {
            gestionnaire.fermer();
        }
    }

    private static Tournoi creerTournoi(String nom, LocalDate date) {
        Jeu jeu = new JeuSolo("Street Fighter", 3);
        Tournoi tournoi = new Tournoi(nom, jeu, date, 1500.0, 2);

        for (int i = 1; i <= 4; i++) {
            Coach coach = new Coach(i, "Coach", "Prenom", "Coach" + i, "M", "0600000000");
            Equipe equipe = new Equipe("Equipe" + i, coach);
            equipe.ajouterJoueur(new Joueur(100 + i, "Joueur", "Prenom", "Joueur" + i, "M", "0600000000"));
            tournoi.inscrireEquipe(equipe);
        }

        Arbitre arbitre = new Arbitre(10, "Arbitre", "Prenom", "Arbitre", "F", "0633333333");
        arbitre.ajouterQualification(jeu);
        tournoi.ajouterArbitre(arbitre);
        return tournoi;
    }

    @Test
    void arbitreOccupeNePeutPasEtreReutiliseEtRedevientDisponible() throws Exception {
        GestionnaireTournois gestionnaire = new GestionnaireTournois(500, 0, 42L);

        Jeu jeu = new JeuSolo("Street Fighter", 3);
        Arbitre arbitre = new Arbitre(
                50, "Durand", "Emma", "Emmy", "F", "0633333333");
        arbitre.ajouterQualification(jeu);

        Tournoi premier = creerTournoiAvecArbitre(
                "Premier tournoi", LocalDate.of(2026, 11, 1), jeu, arbitre, 100);
        Tournoi second = creerTournoiAvecArbitre(
                "Deuxieme tournoi", LocalDate.of(2026, 11, 2), jeu, arbitre, 200);

        try {
            gestionnaire.planifier(premier);
            gestionnaire.planifier(second);

            assertSame(premier, gestionnaire.demarrerProchain());
            assertTrue(gestionnaire.getArbitresOccupes().contains(arbitre));
            assertFalse(gestionnaire.getArbitresDisponibles().contains(arbitre));

            assertThrows(
                    IllegalStateException.class,
                    gestionnaire::demarrerProchain);

            premier.attendreFin(10, TimeUnit.SECONDS);

            assertFalse(gestionnaire.getArbitresOccupes().contains(arbitre));
            assertTrue(gestionnaire.getArbitresDisponibles().contains(arbitre));
        } finally {
            gestionnaire.fermer();
        }
    }

}
