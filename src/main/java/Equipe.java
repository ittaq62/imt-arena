import java.util.ArrayList;
import java.util.List;

public class Equipe {

    private String nom;
    private Coach coach;
    private List<Joueur> joueurs;
    private int points;

    public Equipe(String nom, Coach coach) {
        this.nom = nom;
        this.coach = coach;
        this.joueurs = new ArrayList<>();
        this.points = 0;
    }

    public String getNom() {
        return nom;
    }

    public Coach getCoach() {
        return coach;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs);
    }

    public int getPoints() {
        return points;
    }

    public void ajouterJoueur(Joueur joueur) {
        joueurs.add(joueur);
    }

    public void retirerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
    }

    public void enregistrerVictoire() {
        points += 3;
    }
}