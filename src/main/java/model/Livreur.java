package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Livreur")
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    
    @ManyToOne
    @JoinColumn(name = "chef_id", nullable = false)  // Associer avec Chef plutôt que Commande
    private Chef chef;

    @Column(name = "situation")
    private String situation; // Possible values: "en attente", "en cours", "prête"

    @Column(name = "date_confirme")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateConfirme;

    // Default constructor
    public Livreur() {}

    // Parametrized constructor
    public Livreur(User user, Commande commande, String situation) {
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
    
    public Chef getChef() { return chef; }
    public void setChef(Chef chef) { this.chef = chef; }
    
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }
    public java.util.Date getDateConfirme() { return dateConfirme; }
    public void setDateConfirme(java.util.Date dateConfirme) { this.dateConfirme = dateConfirme; }
}
