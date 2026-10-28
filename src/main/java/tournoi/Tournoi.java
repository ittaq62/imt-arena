/*
##################################################################
# @Auteur : DOUILLY Quentin, CHÂTELAIN Lilou & BEIGNON Arthur    #
# @DateCréation : 27/10/2026                                     #
# @DateDernièreModification : 27/10/2026                         #
##################################################################
*/

package tournoi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import equipe.Equipe;
import jeu.Jeu;
import personne.Arbitre;

// Class Tournoi représente un tournoi de jeu vidéo, incluant des équipes, des arbitres et des matchs. Elle gère l'état du tournoi, la planification, le démarrage et la simulation des matchs, ainsi que la génération d'un rapport final.
public class Tournoi {

    public enum Etat {
        PLANIFIE,
        EN_COURS,
        TERMINE
    }

    // Attributs
    private String nom;
    private Jeu jeu;
    private LocalDate date;
    private double budget;
    private int nombreArenes;
    private List<Equipe> equipes;
    private List<Arbitre> arbitres;
    private List<Match> matchs;
    private volatile Etat etat;
    private volatile RapportTournoi rapport;
    private Future<?> futureSimulation;

    // Constructeur
    public Tournoi(String nom, Jeu jeu, LocalDate date, double budget, int nombreArenes) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du tournoi est obligatoire.");
        }
        if (jeu == null) {
            throw new IllegalArgumentException("Le jeu est obligatoire.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La date est obligatoire.");
        }
        if (nombreArenes < 1 || nombreArenes > 4) {
            throw new IllegalArgumentException("Il faut entre 1 et 4 arènes.");
        }

        this.nom = nom;
        this.jeu = jeu;
        this.date = date;
        this.budget = budget;
        this.nombreArenes = nombreArenes;
        this.equipes = new ArrayList<>();
        this.arbitres = new ArrayList<>();
        this.matchs = new ArrayList<>();
        this.etat = Etat.PLANIFIE;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBudget() {
        return budget;
    }

    public int getNombreArenes() {
        return nombreArenes;
    }

    public Etat getEtat() {
        return etat;
    }

    public RapportTournoi getRapport() {
        return rapport;
    }

    public List<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }

    public List<Arbitre> getArbitres() {
        return new ArrayList<>(arbitres);
    }

    public List<Match> getMatchs() {
        return new ArrayList<>(matchs);
    }

    // Inscription des équipes et des arbitres
    public void inscrireEquipe(Equipe equipe) {
        if (etat != Etat.PLANIFIE) {
            throw new IllegalStateException("Le tournoi a déjà commencé.");
        }
        if (equipe == null) {
            throw new IllegalArgumentException("L'équipe ne peut pas être null.");
        }
        if (equipes.contains(equipe)) {
            throw new IllegalArgumentException("Cette équipe est déjà inscrite.");
        }

        equipes.add(equipe);
    }

    public void ajouterArbitre(Arbitre arbitre) {
        if (etat != Etat.PLANIFIE) {
            throw new IllegalStateException("Le tournoi a déjà commencé.");
        }
        if (arbitre == null) {
            throw new IllegalArgumentException("L'arbitre ne peut pas être null.");
        }
        if (!arbitres.contains(arbitre)) {
            arbitres.add(arbitre);
        }
    }

    // Vérification du tournoi et création des matchs
    void preparerDemarrage() {
        if (etat != Etat.PLANIFIE) {
            throw new IllegalStateException("Le tournoi n'est pas planifié.");
        }
        if (equipes.size() < 4) {
            throw new IllegalStateException("Il faut au moins quatre équipes.");
        }

        int joueursRequis = jeu.getNombreJoueursRequis();

        for (Equipe equipe : equipes) {
            if (equipe.getCoach() == null) {
                throw new IllegalStateException("Chaque équipe doit avoir un coach.");
            }
            if (equipe.getJoueurs().size() < joueursRequis) {
                throw new IllegalStateException("L'équipe " + equipe.getNom() + " n'a pas assez de joueurs.");
            }
        }

        List<Arbitre> arbitresQualifies = new ArrayList<>();

        for (Arbitre arbitre : arbitres) {
            if (arbitre.estQualifiePour(jeu)) {
                arbitresQualifies.add(arbitre);
            }
        }

        if (arbitresQualifies.isEmpty()) {
            throw new IllegalStateException("Il faut au moins un arbitre qualifié.");
        }

        matchs.clear();
        int indexArbitre = 0;

        for (int i = 0; i < equipes.size(); i++) {
            for (int j = i + 1; j < equipes.size(); j++) {
                Arbitre arbitre = arbitresQualifies.get(indexArbitre % arbitresQualifies.size());
                Match match = new Match(equipes.get(i), equipes.get(j), jeu, arbitre);

                matchs.add(match);
                indexArbitre++;
            }
        }

        etat = Etat.EN_COURS;
    }

    // Gestion des résultats
    void jouerMatch(Match match, Equipe vainqueur) {
        match.designerVainqueur(vainqueur);
        vainqueur.enregistrerVictoire();
    }

    void annulerMatch(Match match) {
        match.annuler();
    }

    void terminer(RapportTournoi rapport) {
        this.rapport = rapport;
        this.etat = Etat.TERMINE;
    }

    // Attente de la simulation
    void definirFuture(Future<?> future) {
        this.futureSimulation = future;
    }

    public void attendreFin(long delai, TimeUnit unite)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (futureSimulation == null) {
            throw new IllegalStateException("Le tournoi n'a pas encore été démarré.");
        }
        futureSimulation.get(delai, unite);
    }

    @Override
    public String toString() {
        return "Tournoi [nom=" + nom + ", jeu=" + jeu + ", date=" + date + ", budget=" + budget + ", nombreArenes="
                + nombreArenes + ", equipes=" + equipes + ", arbitres=" + arbitres + ", matchs=" + matchs + ", etat="
                + etat + ", rapport=" + rapport + ", futureSimulation=" + futureSimulation + "]";
    }
}