package com.cashback.loyermalin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Définition de l'Entité JPA pour la table 'users'
@Entity
@Table(name = "users")
@Data // Fournit les Getters, Setters, toString, equals et hashCode via Lombok
@NoArgsConstructor // Constructeur sans arguments (requis par JPA)
@AllArgsConstructor // Constructeur avec tous les arguments
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // La clé primaire de la base de données

    private String userId; // L'identifiant externe utilisé par le frontend/Firebase (comme l'ID de votre signal Angular)

    private String email;

    // Le mot de passe sera stocké haché et salé par Spring Security
    private String password;

    private String firstName;
    private String lastName;

    // Pour gérer les différents rôles (ex: LOCATAIRE, PROPRIETAIRE, ADMIN)
    private String role;

    private boolean isVerified = false;
}
