package com.aquamarine.barraiser.repository;// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

        import com.aquamarine.barraiser.model.Drink;
        import com.aquamarine.barraiser.model.Step;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.Set;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> { }