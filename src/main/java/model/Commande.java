package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

    @Column(name = "date")
    private Date date;

    @Column(name = "situation")
    private String situation;

    @Column(name = "total_prix")
    private Double totalPrix;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LingeDeCommande> ligneCommandes;


    public Commande() {
    }

    public Commande(User user, Date date, String situation, List<LingeDeCommande> ligneCommandes, Double totalPrix) {
        this.user = user;
        this.date = date;
        this.situation = situation;
        this.ligneCommandes = ligneCommandes;
        this.totalPrix = totalPrix;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Double getprix() {
        return totalPrix;
    }

    public void setprix(Double totalPrix) {
        this.totalPrix = totalPrix;
    }

    public List<LingeDeCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LingeDeCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    
}
