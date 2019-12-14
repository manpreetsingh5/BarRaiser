package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    Optional<Progress> findByCohortAndDrinkAndUser(Cohort cohort, Drink drink, User user);
    Set<Progress> findAllByCohort(Cohort cohort);
    Set<Progress> findAllByDrink(Drink drink);
    Set<Progress> findAllByUser(User user);
    Progress findByDrinkAndUser(Drink drink, User user);
}