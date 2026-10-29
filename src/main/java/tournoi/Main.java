package tournoi;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import equipe.Equipe;
import jeu.Jeu;
import jeu.JeuEquipe;
import jeu.JeuSolo;
import personne.Arbitre;
import personne.Coach;
import personne.Joueur;

public class Main {

    public static void main(String[] args) throws Exception {
        RapportRepository repository = new RapportRepository();
        GestionnaireTournois gestionnaire = new GestionnaireTournois(
                100, 10, 42L, new FavoriseMieuxClassee(), repository);

        // 1. Ajout des jeux
        Jeu streetFighter = new JeuSolo("Street Fighter", 3);
        Jeu callOfDutty = new JeuEquipe("Call Of Dutty", 5, 3);

        // 2. Ajout des arbitres
        Arbitre arbitreSolo = new Arbitre(100, "Bei", "Arthur", "Raibei", "M", "0601020304");
        arbitreSolo.ajouterQualification(streetFighter);
        Arbitre arbitreEquipe = new Arbitre(101, "Durand", "Emma", "Emmy", "F", "0605060708");
        arbitreEquipe.ajouterQualification(callOfDutty);

        // 3. Ajout des équipes (un coach et 3 joueurs chacune)
        String[] noms = { "Les Lions", "Les Tigres", "Les Aigles", "Les Loups" };
        Equipe[] equipes = new Equipe[noms.length];
        int id = 1;
        for (int e = 0; e < noms.length; e++) {
            Coach coach = new Coach(id++, "Coach" + e, "Prenom", "Coach" + e, "M", "0601020304");
            equipes[e] = new Equipe(noms[e], coach);
            for (int j = 1; j <= 3; j++) {
                equipes[e].ajouterJoueur(new Joueur(id++, "Nom" + id, "Prenom", "Pseudo" + id, "M", "0601020304"));
            }
        }

        // 4. Ajout de deux tournois avec les mêmes équipes
        Tournoi grandTournoi = new Tournoi("Grand Tournoi", callOfDutty, LocalDate.of(2026, 12, 5), 8000, 3);
        Tournoi petitTournoi = new Tournoi("Petit Tournoi", streetFighter, LocalDate.of(2026, 11, 20), 2000, 2);
        for (Equipe equipe : equipes) {
            grandTournoi.inscrireEquipe(equipe);
            petitTournoi.inscrireEquipe(equipe);
        }
        grandTournoi.ajouterArbitre(arbitreEquipe);
        petitTournoi.ajouterArbitre(arbitreSolo);

        // 5. Planification (le plus proche d'abord)
        gestionnaire.planifier(grandTournoi);
        gestionnaire.planifier(petitTournoi);
        System.out.println("=== Tournois planifiés (le plus proche d'abord) ===");
        for (Tournoi tournoi : gestionnaire.getTournoisPlanifies()) {
            System.out.println(tournoi.getNom() + " le " + tournoi.getDate());
        }

        // 6. Lancement du tournoi le plus proche
        Tournoi lance = gestionnaire.demarrerProchain();
        System.out.println();
        System.out.println("=== Lancement de : " + lance.getNom() + " ===");
        System.out.println("Etat : " + lance.getEtat());
        System.out.println("Arbitres occupés : " + gestionnaire.getArbitresOccupes());
        System.out.println("Arbitres disponibles : " + gestionnaire.getArbitresDisponibles());

        // 7. Attente de la fin (clôture automatique)
        lance.attendreFin(10, TimeUnit.SECONDS);
        afficherResultats(lance);

        // 8. Lancement du tournoi suivant
        Tournoi suivant = gestionnaire.demarrerProchain();
        suivant.attendreFin(10, TimeUnit.SECONDS);
        afficherResultats(suivant);

        // 9. Tournois terminés (du plus gros au plus petit budget)
        System.out.println();
        System.out.println("=== Tournois terminés (du plus gros au plus petit budget) ===");
        for (Tournoi tournoi : gestionnaire.getTournoisTermines()) {
            System.out.println(tournoi.getNom() + " : " + tournoi.getBudget() + " euros");
        }

        // 10. Classement des équipes (points cumulés sur les deux tournois)
        System.out.println();
        System.out.println("=== Classement des équipes ===");
        for (Equipe equipe : suivant.getClassement()) {
            System.out.println(equipe);
        }

        // 11. Fin automatique : coachs, arbitres et joueurs
        Equipe premiere = suivant.getClassement().first();
        System.out.println();
        System.out.println("=== Après les tournois ===");
        System.out.println("Victoires du coach de " + premiere.getNom() + " : "
                + premiere.getCoach().getTotalVictoires());
        System.out.println("Statut des joueurs de " + premiere.getNom() + " : "
                + premiere.getJoueurs().get(0).getStatut());
        System.out.println("Matchs arbitrés par " + arbitreSolo + " : " + arbitreSolo.getMatchsArbitres());

        // 12. Rapports enregistrés dans la base H2
        System.out.println();
        System.out.println("=== Base de données ===");
        System.out.println("Tous les rapports : " + repository.trouverTous().size());
        System.out.println("Rapports de Street Fighter : " + repository.trouverParJeu("Street Fighter").size());

        gestionnaire.fermer();
    }

    // Affiche le rapport et les listes d'un tournoi terminé
    private static void afficherResultats(Tournoi tournoi) {
        RapportTournoi rapport = tournoi.getRapport();
        System.out.println();
        System.out.println("=== Fin de : " + tournoi.getNom() + " ===");
        System.out.println("Etat : " + tournoi.getEtat());
        System.out.println("Matchs joués : " + rapport.getMatchsJoues() + " sur " + rapport.getMatchsPrevus());
        System.out.println("Vainqueur : " + rapport.getNomVainqueur() + ", réussi : " + rapport.isReussi());

        Equipe lions = tournoi.trouverEquipeParNom("Les Lions");
        System.out.println("Matchs de " + lions.getNom() + " :");
        for (Match match : tournoi.getMatchsEquipe(lions)) {
            System.out.println("  " + match);
        }

        System.out.println("Matchs regroupés par équipe gagnante :");
        Map<Equipe, List<Match>> parVainqueur = tournoi.getMatchsParVainqueur();
        for (Equipe equipe : parVainqueur.keySet()) {
            System.out.println("  " + equipe.getNom() + " : " + parVainqueur.get(equipe));
        }
    }
}