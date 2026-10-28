/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 28/10/2026                                     #
# @DateDernièreModification : 28/10/2026                         #
##################################################################
*/
package tournoi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import equipe.Equipe;
import jeu.Jeu;
import jeu.JeuSolo;
import personne.Arbitre;
import personne.Coach;

//Tests unitaires des règles de création, de résultat et d'annulation d'un match.
class MatchTest {

    private Equipe equipe1;
    private Equipe equipe2;
    private Jeu jeu;
    private Arbitre arbitre;

    @BeforeEach
    void creerMatch() {
        equipe1 = new Equipe("Les Lions", new Coach(1, "Petit", "David", "Dave", "M", "0622222222"));
        equipe2 = new Equipe("Les Tigres", new Coach(2, "Martin", "Paul", "Polo", "M", "0611111111"));
        jeu = new JeuSolo("Street Fighter", 3);
        arbitre = new Arbitre(3, "Durand", "Emma", "Emmy", "F", "0633333333");
        arbitre.ajouterQualification(jeu);
    }

    // Vérifie qu'une même équipe ne peut pas jouer contre elle-même.
    @Test
    void equipeNePeutPasJouerContreElleMeme() {
        assertThrows(IllegalArgumentException.class, () -> new Match(equipe1, equipe1, jeu, arbitre));
    }

    // Vérifie qu'un arbitre doit être qualifié pour le jeu du match.
    @Test
    void arbitreNonQualifieEstRefuse() {
        Jeu autreJeu = new JeuSolo("Hearthstone", 3);

        assertThrows(IllegalArgumentException.class, () -> new Match(equipe1, equipe2, autreJeu, arbitre));
    }

    // Vérifie que la désignation du vainqueur termine le match.
    @Test
    void designerVainqueurTermineLeMatch() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        match.designerVainqueur(equipe2);

        assertEquals(equipe2, match.getVainqueur());
        assertEquals(Match.Etat.JOUE, match.getEtat());
    }

    // Vérifie qu'un match annulé n'a pas de vainqueur.
    @Test
    void matchAnnuleNaPasDeVainqueur() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        match.annuler();

        assertNull(match.getVainqueur());
        assertEquals(Match.Etat.ANNULE, match.getEtat());
    }

    // Vérifie les informations initiales et l'état prévu du match.
    @Test
    void matchCommencePrevuAvecLesBonsParticipants() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        assertSame(equipe1, match.getEquipe1());
        assertSame(equipe2, match.getEquipe2());
        assertSame(jeu, match.getJeu());
        assertSame(arbitre, match.getArbitre());
        assertNull(match.getVainqueur());
        assertEquals(Match.Etat.PREVU, match.getEtat());
    }

    // Vérifie que le vainqueur appartient à l'une des équipes du match.
    @Test
    void vainqueurDoitFairePartieDuMatch() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);
        Equipe autreEquipe = new Equipe("Les Aigles",
                new Coach(4, "Martin", "Paul", "Polo", "M", "0611111111"));

        assertThrows(IllegalArgumentException.class, () -> match.designerVainqueur(autreEquipe));
        assertEquals(Match.Etat.PREVU, match.getEtat());
    }

    // Vérifie qu'un match terminé ne peut pas être modifié une seconde fois.
    @Test
    void matchTermineNePeutPasEtreJoueOuAnnuleDeNouveau() {
        Match matchJoue = new Match(equipe1, equipe2, jeu, arbitre);
        matchJoue.designerVainqueur(equipe1);

        Match matchAnnule = new Match(equipe1, equipe2, jeu, arbitre);
        matchAnnule.annuler();

        assertAll(
                () -> assertThrows(IllegalStateException.class,
                        () -> matchJoue.designerVainqueur(equipe2)),
                () -> assertThrows(IllegalStateException.class, matchJoue::annuler),
                () -> assertThrows(IllegalStateException.class,
                        () -> matchAnnule.designerVainqueur(equipe1)),
                () -> assertThrows(IllegalStateException.class, matchAnnule::annuler));
    }
}
