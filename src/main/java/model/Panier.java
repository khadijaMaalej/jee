package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



public class Panier {
   
    private Long id;
    private static long nextId = 1;

    private Long pizzaId;
    private int quantite;
    private String message;
    private double prix;
    private String size;
    

    public Panier() {
        // Constructeur par défaut requis par JPA
    }

    public Panier(Long pizzaId, int quantite, String message, double prix, String size) {
        this.pizzaId = pizzaId;
        this.quantite = quantite;
        this.message = message;
        this.prix = prix;
        this.size = size;
        this.id = nextId++;
    }

    public Long getPizzaId() {
        return pizzaId;
    }
    public Long getId() {
        return id;
    }
    
    
    public void setMessage(String message) {        this.message = message;
}

    public void setprix(Double prix) {        this.prix = prix;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getQuantite() {
        return quantite;
    }
    public String getMessage() {
        return message;
    }
    public double getprix() {
        return prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public String getsize() {
        return size;
    }
    public void setsize(String size) {
        this.size = size;
    }

   
}
