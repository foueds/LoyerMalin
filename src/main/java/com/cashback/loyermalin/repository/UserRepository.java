package com.cashback.loyermalin.repository;


import com.cashback.loyermalin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode personnalisée pour trouver un utilisateur par son identifiant unique (userId)
    // Spring Data JPA génère automatiquement la requête SQL à partir du nom de la méthode.
    Optional<User> findByUserId(String userId);

    // Utile pour la connexion par email
    Optional<User> findByEmail(String email);
}
