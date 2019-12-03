package com.aquamarine.barraiser.repository;

import com.aquamarine.barraiser.model.Actions;
import com.aquamarine.barraiser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ActionRepository extends JpaRepository<Actions, Integer> {
}
