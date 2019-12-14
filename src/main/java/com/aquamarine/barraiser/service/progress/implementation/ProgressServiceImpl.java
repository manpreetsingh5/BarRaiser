package com.aquamarine.barraiser.service.progress.implementation;

import com.aquamarine.barraiser.dto.mapper.ProgressDTOMapper;
import com.aquamarine.barraiser.dto.model.ProgressDTO;
import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.Drink;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.CohortRepository;
import com.aquamarine.barraiser.repository.DrinkRepository;
import com.aquamarine.barraiser.repository.ProgressRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.progress.interfaces.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
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
        Set<Drink> drinks = new HashSet<>();
        Set<User> users = new HashSet<>();
        Set<Cohort> cohorts = new HashSet<>();
        Cohort cohort = cohortRepository.findById(cohort_id).get();
        Drink drink = drinkRepository.findById(drink_id).get();
        User user = userRepository.findById(user_id).get();

        Optional<Progress> progressOptional = progressRepository.findByCohortsAndDrinksAndUsers(cohort, drink, user);
        if (!progressOptional.isPresent()) {
            cohorts.add(cohortRepository.findById(cohort_id).get());
            drinks.add(drinkRepository.findById(drink_id).get());
            users.add(userRepository.findById(user_id).get());
            Progress progress = new Progress()
                    .setCohorts(cohorts)
                    .setDrinks(drinks)
                    .setUsers(users)
                    .setStatus(false);

            progressRepository.save(progress);
        }
        else {
            Progress progress = progressOptional.get();
            cohorts = progress.getCohorts();
            drinks = progress.getDrinks();
            users = progress.getUsers();

            cohorts.add(cohortRepository.findById(cohort_id).get());
            drinks.add(drinkRepository.findById(drink_id).get());
            users.add(userRepository.findById(user_id).get());

            progress.setCohorts(cohorts)
                    .setDrinks(drinks)
                    .setUsers(users)
                    .setStatus(false);

        }

        return true;
    }

    @Override
    public boolean updateProgress(int cohort_id, int drink_id, int user_id) {
        Cohort cohort = cohortRepository.findById(cohort_id).get();
        Drink drink = drinkRepository.findById(drink_id).get();
        User user = userRepository.findById(user_id).get();
        Optional<Progress> progressOptional = progressRepository.findByCohortsAndDrinksAndUsers(cohort, drink, user);

        if (progressOptional.isPresent()) {
            Progress progress = progressOptional.get();
            progressRepository.save(progress);
        }
        return false;
    }

    @Override
    public Set<ProgressDTO> getProgressByUser(int user_id) {
        HashSet<ProgressDTO> ret = new HashSet<>();
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            for (Progress p : user.getProgress()) {
                ret.add(ProgressDTOMapper.toProgressDTO(p));
            }
            return(ret);
        }
        return null;
    }

    @Override
    public Set<ProgressDTO> getProgressByCohort(int cohort_id) {
        return null;
    }

    @Override
    public Set<ProgressDTO> getProgressByDrink(int drink_id) {
        return null;
    }

}
