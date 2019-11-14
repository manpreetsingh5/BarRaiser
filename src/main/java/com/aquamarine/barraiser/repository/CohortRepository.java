package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Integer> {
    Set<Cohort> findAllByInstructor(User instructor);
}
