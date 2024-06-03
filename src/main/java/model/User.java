package model;

import java.util.List;
import model.Commande;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "Utilisateur")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "telephone")
    private int telephone;

    

    @Column(name = "pass")
    private String pass;




    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Commande> commandes;
	public User() {
    }
	public User(String nom, String adresse, int telephone, String pass) {
		super();
		this.nom = nom;
		this.pass = pass;
		
		this.adresse = adresse;
		this.telephone=telephone;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String nom) {
		this.telephone = telephone;
	}
	
	
	public Long getId() {
		return id;
	}
	

	

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	
	

}