package tournoi;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import equipe.Equipe;
import jeu.Jeu;
import jeu.JeuSolo;
import personne.Arbitre;
import personne.Coach;
import personne.Joueur;

public class Main {

    public static void main(String[] args) throws Exception {
        RapportRepository repository = new RapportRepository();
        GestionnaireTournois gestionnaire = new GestionnaireTournois(
                100, 10, 42L, new FavoriseMieuxClassee(), repository);

        // Un jeu
        Jeu jeu = new JeuSolo("Call Of Dutty", 3);

        // Un arbitre qualifié
        Arbitre arbitre = new Arbitre(100, "Bei", "Arthur", "Raibei", "M", "0601020304");
        arbitre.ajouterQualification(jeu);

        // 4 équipes de 3 joueurs
        Tournoi tournoi = new Tournoi("Grand Tournoi", jeu, LocalDate.of(2026, 10, 29), 5000, 2);
        int id = 1;
        for (int e = 1; e <= 4; e++) {
            Coach coach = new Coach(id++, "Coach" + e, "Prenom", "Coach" + e, "M", "0601020304");
            Equipe equipe = new Equipe("Equipe" + e, coach);
            for (int j = 1; j <= 3; j++) {
                equipe.ajouterJoueur(new Joueur(id++, "Nom" + id, "Prenom", "Pseudo" + id, "M", "0601020304"));
            }
            tournoi.inscrireEquipe(equipe);
        }
        tournoi.ajouterArbitre(arbitre);

        // Planifier, lancer, attendre
        gestionnaire.planifier(tournoi);
        System.out.println("Planifiés : " + gestionnaire.getTournoisPlanifies());
        gestionnaire.demarrerProchain();
        System.out.println("En cours : " + gestionnaire.getTournoisEnCours());
        tournoi.attendreFin(10, TimeUnit.SECONDS);

        // Résultats
        System.out.println("Etat : " + tournoi.getEtat());
        RapportTournoi rapport = tournoi.getRapport();
        System.out.println("Vainqueur : " + rapport.getNomVainqueur() + ", réussi : " + rapport.isReussi());
        System.out.println("Matchs : " + tournoi.getMatchs());

        // Base de données
        System.out.println("Tous les rapports : " + repository.trouverTous().size());
        System.out.println("Rapports du jeu : " + repository.trouverParJeu("Call Of Dutty").size());

        // Arrêt propre
        gestionnaire.fermer();
    }
}