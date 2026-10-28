import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import personne.Joueur;

class JoueurTest {

    @Test
    void joueurCommenceAmateur() {
        Joueur joueur = new Joueur(
                1, "Dupont", "Alice", "Alicia", "F", "0600000000");

        assertEquals(Joueur.Statut.AMATEUR, joueur.getStatut());
        assertEquals(0, joueur.getVictoiresAvecEquipe());
    }

    @Test
    void joueurPasseProApresTroisVictoires() {
        Joueur joueur = new Joueur(
                1, "Dupont", "Alice", "Alicia", "F", "0600000000");

        joueur.enregistrerVictoireAvecEquipe();
        joueur.enregistrerVictoireAvecEquipe();

        assertEquals(Joueur.Statut.AMATEUR, joueur.getStatut());

        joueur.enregistrerVictoireAvecEquipe();

        assertEquals(3, joueur.getVictoiresAvecEquipe());
        assertEquals(Joueur.Statut.PRO, joueur.getStatut());
    }
}