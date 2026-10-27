import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EquipeTest {

    private Equipe equipe;
    private Joueur joueur;

    @BeforeEach
    void creerEquipe() {
        Coach coach = new Coach(1, "Petit", "David", "Dave", "M", "0622222222");
        equipe = new Equipe("Les Lions", coach);
        joueur = new Joueur(2, "Dupont", "Alice", "Alicia", "F", "0600000000");
    }

    @Test
    void equipeCommenceAvecZeroPoint() {
        assertEquals(0, equipe.getPoints());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    void chaqueVictoireRapporteTroisPoints(int nbVictoires) {
        for (int i = 0; i < nbVictoires; i++) {
            equipe.enregistrerVictoire();
        }

        assertEquals(nbVictoires * 3, equipe.getPoints());
    }

    @Test
    void joueurDejaDansUneAutreEquipeEstRefuse() {
        Coach autreCoach = new Coach(3, "Martin", "Paul", "Polo", "M", "0611111111");
        Equipe autreEquipe = new Equipe("Les Tigres", autreCoach);
        equipe.ajouterJoueur(joueur);

        assertThrows(IllegalStateException.class, () -> autreEquipe.ajouterJoueur(joueur));
    }

    @Test
    void joueurQuiQuitteEquipeRepartAZero() {
        equipe.ajouterJoueur(joueur);
        equipe.enregistrerVictoire();

        equipe.retirerJoueur(joueur);

        assertNull(joueur.getEquipe());
        assertEquals(0, joueur.getVictoiresAvecEquipe());
    }
}