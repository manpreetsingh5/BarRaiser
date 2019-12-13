package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    Progress findByCohortAndDrinkAndUser(Cohort cohort, Drink drink, User user);
}