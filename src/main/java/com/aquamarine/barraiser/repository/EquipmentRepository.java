package com.aquamarine.barraiser.repository;


import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> { }