package com.aquamarine.barraiser.service.progress.implementation;

import com.aquamarine.barraiser.dto.model.ProgressDTO;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.repository.CohortRepository;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.ProgressRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.progress.interfaces.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private CohortRepository cohortRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean addProgress(int cohort_id, int drink_id, int user_id) {
        Progress progress = new Progress()
                .setCohort(cohortRepository.findById(cohort_id).get())
                .setDrink(drinkRepository.findById(drink_id).get())
                .setUser(userRepository.findById(user_id).get());

        progressRepository.save(progress);
        return false;
    }

    @Override
    public boolean updateProgress(int cohort_id, int drink_id, int user_id) {
        return false;
    }

    @Override
    public Set<ProgressDTO> getProgressByUser(int user_id) {
        return null;
    }

}
