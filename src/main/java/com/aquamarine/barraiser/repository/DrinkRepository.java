package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {
    Set<Drink> findAllByInstructor(User instructor);
}