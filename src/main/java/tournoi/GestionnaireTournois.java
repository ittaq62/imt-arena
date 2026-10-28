/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 27/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/

package tournoi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import equipe.Equipe;
import personne.Joueur;

public class GestionnaireTournois {

    // Attributs
    private List<Tournoi> tournoisPlanifies;
    private List<Tournoi> tournoisEnCours;
    private ExecutorService pool;

    private int tempsParMatch;
    private int chanceIncident;
    private long graineAleatoire;

    // Constructeur
    public GestionnaireTournois(int tempsParMatch, int chanceIncident, long graineAleatoire) {
        this.tempsParMatch = tempsParMatch;
        this.chanceIncident = chanceIncident;
        this.graineAleatoire = graineAleatoire;
        this.tournoisPlanifies = new ArrayList<>();
        this.tournoisEnCours = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(3);
    }

    // Planification
    public synchronized void planifier(Tournoi tournoi) {
        if (tournoi == null) {
            throw new IllegalArgumentException("Le tournoi ne peut pas être null.");
        }
        if (tournoi.getEtat() != Tournoi.Etat.PLANIFIE) {
            throw new IllegalArgumentException("Le tournoi doit être planifié.");
        }
        if (tournoisPlanifies.contains(tournoi)) {
            throw new IllegalArgumentException("Ce tournoi est déjà planifié.");
        }
        tournoisPlanifies.add(tournoi);
    }

    // Lancement du tournoi le plus proche
    public synchronized Tournoi demarrerProchain() {
        if (tournoisEnCours.size() >= 3) {
            throw new IllegalStateException("Trois tournois sont déjà en cours.");
        }
        if (tournoisPlanifies.isEmpty()) {
            throw new IllegalStateException("Aucun tournoi n'est planifié.");
        }

        int indexProchain = 0;

        for (int i = 1; i < tournoisPlanifies.size(); i++) {
            if (tournoisPlanifies.get(i).getDate().isBefore(
                    tournoisPlanifies.get(indexProchain).getDate())) {
                indexProchain = i;
            }
        }

        Tournoi tournoi = tournoisPlanifies.get(indexProchain);

        for (Tournoi autreTournoi : tournoisEnCours) {
            for (Equipe equipe : tournoi.getEquipes()) {
                for (Equipe equipeOccupee : autreTournoi.getEquipes()) {
                    if (equipe == equipeOccupee) {
                        throw new IllegalStateException("Une équipe est déjà engagée ailleurs.");
                    }

                    if (equipe.getCoach() != null
                            && equipe.getCoach() == equipeOccupee.getCoach()) {
                        throw new IllegalStateException("Un coach est déjà engagé ailleurs.");
                    }

                    for (Joueur joueur : equipe.getJoueurs()) {
                        for (Joueur joueurOccupe : equipeOccupee.getJoueurs()) {
                            if (joueur == joueurOccupe) {
                                throw new IllegalStateException("Un joueur est déjà engagé ailleurs.");
                            }
                        }
                    }
                }
            }
        }

        tournoi.preparerDemarrage();

        tournoisPlanifies.remove(indexProchain);
        tournoisEnCours.add(tournoi);

        long graine = graineAleatoire;
        graineAleatoire++;

        pool.execute(new Runnable() {
            @Override
            public void run() {
                simuler(tournoi, graine);
                retirerTournoiTermine(tournoi);
            }
        });

        return tournoi;
    }

    // Simulation des matchs
    private void simuler(Tournoi tournoi, long graine) {
        long duree = (long) tournoi.getMatchs().size() * tempsParMatch / tournoi.getNombreArenes();

        try {
            Thread.sleep(duree);
        } catch (InterruptedException e) {
        }

        Random random = new Random(graine);
        Map<Equipe, Integer> victoires = new HashMap<>();

        for (Equipe equipe : tournoi.getEquipes()) {
            victoires.put(equipe, 0);
        }

        int matchsJoues = 0;

        for (Match match : tournoi.getMatchs()) {
            match.getArbitre().enregistrerMatchArbitre();

            if (random.nextInt(100) < chanceIncident) {
                tournoi.annulerMatch(match);
            } else {
                Equipe vainqueur;

                if (random.nextBoolean()) {
                    vainqueur = match.getEquipe1();
                } else {
                    vainqueur = match.getEquipe2();
                }

                tournoi.jouerMatch(match, vainqueur);
                victoires.put(vainqueur, victoires.get(vainqueur) + 1);
                matchsJoues++;
            }
        }

        Equipe vainqueurTournoi = null;
        int plusDeVictoires = -1;

        for (Equipe equipe : tournoi.getEquipes()) {
            int nombreVictoires = victoires.get(equipe);

            if (nombreVictoires > plusDeVictoires) {
                plusDeVictoires = nombreVictoires;
                vainqueurTournoi = equipe;
            }
        }

        if (matchsJoues == 0) {
            vainqueurTournoi = null;
        }

        String nomVainqueur = null;
        if (vainqueurTournoi != null) {
            nomVainqueur = vainqueurTournoi.getNom();
        }

        boolean reussi = matchsJoues * 100 >= tournoi.getMatchs().size() * 70;

        RapportTournoi rapport = new RapportTournoi(tournoi.getNom(), tournoi.getDate(), tournoi.getJeu().getNom(),
                tournoi.getMatchs().size(), matchsJoues, nomVainqueur, reussi);

        tournoi.terminer(rapport);
    }

    // Retrait des tournois terminés
    private synchronized void retirerTournoiTermine(Tournoi tournoi) {
        tournoisEnCours.remove(tournoi);
    }

    // Getters
    public synchronized List<Tournoi> getTournoisPlanifies() {
        return new ArrayList<>(tournoisPlanifies);
    }

    public synchronized List<Tournoi> getTournoisEnCours() {
        return new ArrayList<>(tournoisEnCours);
    }

    // Arrêt du pool de threads
    public void fermer() {
        pool.shutdown();
    }
}