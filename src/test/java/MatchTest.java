import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void equipeNePeutPasJouerContreElleMeme() {
        assertThrows(IllegalArgumentException.class, () -> new Match(equipe1, equipe1, jeu, arbitre));
    }

    @Test
    void arbitreNonQualifieEstRefuse() {
        Jeu autreJeu = new JeuSolo("Hearthstone", 3);

        assertThrows(IllegalArgumentException.class, () -> new Match(equipe1, equipe2, autreJeu, arbitre));
    }

    @Test
    void designerVainqueurTermineLeMatch() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        match.designerVainqueur(equipe2);

        assertEquals(equipe2, match.getVainqueur());
        assertEquals(Match.Etat.JOUE, match.getEtat());
    }

    @Test
    void matchAnnuleNaPasDeVainqueur() {
        Match match = new Match(equipe1, equipe2, jeu, arbitre);

        match.annuler();

        assertNull(match.getVainqueur());
        assertEquals(Match.Etat.ANNULE, match.getEtat());
    }
}