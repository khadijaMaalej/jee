package model;

import jakarta.persistence.*;

@Entity
@Table(name = "ligne")
public class LingeDeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_pizza")
    private Pizza pizza;

    @Column(name = "quantite")
    private int quantite;
    
    @Column(name = "size")
    private String size;

    @Column(name = "prix")
    private double prix;

    @Column(name = "message")
    private String message;

    public LingeDeCommande() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public String getsize() {
        return size;
    }

    public void setsize(String size) {
        this.size = size;
    }
    

}
