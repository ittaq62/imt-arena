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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import equipe.Equipe;
import jeu.Jeu;
import jeu.JeuSolo;
import personne.Arbitre;
import personne.Coach;
import personne.Joueur;

// Tests unitaires de la création, de la préparation et des résultats d'un tournoi. 
class TournoiTest {

    private Jeu jeu;

    @BeforeEach
    void creerJeu() {
        jeu = new JeuSolo("Street Fighter", 3);
    }

    // Vérifie que le constructeur conserve les informations et planifie le tournoi.
    @Test
    void constructeurConserveLesInformationsEtPlanifieLeTournoi() {
        LocalDate date = LocalDate.of(2026, 11, 15);

        Tournoi tournoi = new Tournoi("Arena Cup", jeu, date, 1500.0, 2);

        assertEquals("Arena Cup", tournoi.getNom());
        assertSame(jeu, tournoi.getJeu());
        assertEquals(date, tournoi.getDate());
        assertEquals(1500.0, tournoi.getBudget());
        assertEquals(2, tournoi.getNombreArenes());
        assertEquals(Tournoi.Etat.PLANIFIE, tournoi.getEtat());
        assertNull(tournoi.getRapport());
    }

    // Vérifie que les informations obligatoires et le nombre d'arènes sont
    // contrôlés.
    @Test
    void constructeurRefuseLesInformationsObligatoiresManquantes() {
        LocalDate date = LocalDate.of(2026, 11, 15);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Tournoi(" ", jeu, date, 0, 1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Tournoi("Cup", null, date, 0, 1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Tournoi("Cup", jeu, null, 0, 1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Tournoi("Cup", jeu, date, 0, 0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Tournoi("Cup", jeu, date, 0, 5)));
    }

    // Vérifie l'inscription d'une équipe et le refus d'une équipe nulle ou déjà
    // inscrite.
    @Test
    void inscriptionAjouteEquipeEtRefuseNullOuDoublon() {
        Tournoi tournoi = creerTournoi();
        Equipe equipe = creerEquipe("Les Lions", 1);

        tournoi.inscrireEquipe(equipe);

        assertEquals(1, tournoi.getEquipes().size());
        assertSame(equipe, tournoi.getEquipes().get(0));
        assertThrows(IllegalArgumentException.class, () -> tournoi.inscrireEquipe(null));
        assertThrows(IllegalArgumentException.class, () -> tournoi.inscrireEquipe(equipe));
    }

    // Vérifie que la liste retournée ne permet pas de modifier les équipes du
    // tournoi.
    @Test
    void collectionsRetourneesNeModifientPasLeTournoi() {
        Tournoi tournoi = creerTournoi();
        tournoi.inscrireEquipe(creerEquipe("Les Lions", 1));

        tournoi.getEquipes().clear();

        assertEquals(1, tournoi.getEquipes().size());
    }

    // Vérifie la création des matchs et le passage du tournoi à l'état en cours.
    @Test
    void preparationCreeUnMatchParPaireEtPasseLeTournoiEnCours() {
        Tournoi tournoi = creerTournoi();
        tournoi.inscrireEquipe(creerEquipe("Les Lions", 1));
        tournoi.inscrireEquipe(creerEquipe("Les Tigres", 2));
        tournoi.inscrireEquipe(creerEquipe("Les Aigles", 3));
        tournoi.inscrireEquipe(creerEquipe("Les Loups", 4));
        Arbitre arbitre = new Arbitre(10, "Durand", "Emma", "Emmy", "F", "0633333333");
        arbitre.ajouterQualification(jeu);
        tournoi.ajouterArbitre(arbitre);

        tournoi.preparerDemarrage();

        assertEquals(Tournoi.Etat.EN_COURS, tournoi.getEtat());
        assertEquals(6, tournoi.getMatchs().size());
        assertTrue(tournoi.getMatchs().stream().allMatch(m -> m.getEtat() == Match.Etat.PREVU));
    }

    // Vérifie qu'un tournoi ne peut pas démarrer avec moins de quatre équipes.
    @Test
    void preparationRefuseMoinsDeQuatreEquipes() {
        Tournoi tournoi = creerTournoi();

        assertThrows(IllegalStateException.class, tournoi::preparerDemarrage);
    }

    // Vérifie qu'une victoire met à jour le match et les points de l'équipe
    // gagnante.
    @Test
    void jouerMatchEnregistreLeVainqueurEtSesPoints() {
        Tournoi tournoi = creerTournoi();
        Equipe equipe1 = creerEquipe("Les Lions", 1);
        Equipe equipe2 = creerEquipe("Les Tigres", 2);
        Arbitre arbitre = new Arbitre(10, "Durand", "Emma", "Emmy", "F", "0633333333");
        arbitre.ajouterQualification(jeu);
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        tournoi.jouerMatch(match, equipe2);

        assertSame(equipe2, match.getVainqueur());
        assertEquals(Match.Etat.JOUE, match.getEtat());
        assertEquals(3, equipe2.getPoints());
        assertEquals(0, equipe1.getPoints());
    }

    private Tournoi creerTournoi() {
        return new Tournoi("Arena Cup", jeu, LocalDate.of(2026, 11, 15), 1500.0, 2);
    }

    private Equipe creerEquipe(String nom, int idCoach) {
        Coach coach = new Coach(idCoach, "Nom", "Prenom", "Pseudo" + idCoach, "M", "0600000000");
        Equipe equipe = new Equipe(nom, coach);
        equipe.ajouterJoueur(new Joueur(100 + idCoach, "Joueur", "Prenom", "Joueur" + idCoach, "M", "0600000000"));
        return equipe;
    }

    @Test
    void retrouverEquipeParNom() {
        Tournoi tournoi = creerTournoi();
        Equipe equipe = creerEquipe("Les Lions", 1);
        tournoi.inscrireEquipe(equipe);

        assertSame(equipe, tournoi.trouverEquipeParNom("Les Lions"));
        assertNull(tournoi.trouverEquipeParNom("Les Tigres"));
    }

    @Test
    void classementTrieParPointsPuisParNom() {
        Tournoi tournoi = creerTournoi();
        Equipe lions = creerEquipe("Les Lions", 1);
        Equipe tigres = creerEquipe("Les Tigres", 2);
        Equipe aigles = creerEquipe("Les Aigles", 3);
        tournoi.inscrireEquipe(lions);
        tournoi.inscrireEquipe(tigres);
        tournoi.inscrireEquipe(aigles);

        tigres.enregistrerVictoire();

        assertIterableEquals(List.of(tigres, aigles, lions), tournoi.getClassement());
    }
}