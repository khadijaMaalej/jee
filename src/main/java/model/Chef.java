package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Chef")
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @Column(name = "situation")
    private String situation; // Possible values: "en attente", "en cours", "prête"

    @Column(name = "date_confirme")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateConfirme;

    // Default constructor
    public Chef() {}

    // Parametrized constructor
    public Chef(User user, Commande commande, String situation) {
        this.user = user;
        this.commande = commande;
        this.situation = situation;
        this.dateConfirme = new java.util.Date(); // sets to current date/time
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }
    public java.util.Date getDateConfirme() { return dateConfirme; }
    public void setDateConfirme(java.util.Date dateConfirme) { this.dateConfirme = dateConfirme; }
}
