package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);
    Optional<User> findByEmail(String email);

}