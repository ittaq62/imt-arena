package tournoi;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//Class RapportRepository sauvegarde et retrouve les rapports de tournoi dans la base H2 (JPA).
public class RapportRepository {

    private EntityManagerFactory fabrique;

    public RapportRepository() {
        this.fabrique = Persistence.createEntityManagerFactory("arena");
    }

    // Sauvegarde un rapport dans la base
    public void sauvegarder(RapportTournoi rapport) {
        EntityManager em = fabrique.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rapport);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // Retrouve tous les rapports
    public List<RapportTournoi> trouverTous() {
        EntityManager em = fabrique.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM RapportTournoi r", RapportTournoi.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Retrouve les rapports d'un jeu donné
    public List<RapportTournoi> trouverParJeu(String nomJeu) {
        EntityManager em = fabrique.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM RapportTournoi r WHERE r.nomJeu = :nomJeu",
                    RapportTournoi.class)
                    .setParameter("nomJeu", nomJeu)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void fermer() {
        fabrique.close();
    }
}