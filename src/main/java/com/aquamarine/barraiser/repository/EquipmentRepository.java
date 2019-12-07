package com.aquamarine.barraiser.repository;


import com.aquamarine.barraiser.enums.EquipmentEnum;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Set<Equipment> findAllByCreatedByAndType(String email, EquipmentEnum type);
}