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
        Cohort cohort = cohortRepository.findById(cohort_id).get();
        Drink drink = drinkRepository.findById(drink_id).get();
        User user = userRepository.findById(user_id).get();

        Optional<Progress> progressOptional = progressRepository.findByCohortAndDrinkAndUser(cohort, drink, user);
        if (!progressOptional.isPresent()) {
            Progress progress = new Progress()
                    .setCohort(cohort)
                    .setDrink(drink)
                    .setUser(user)
                    .setStatus(false);

            progressRepository.save(progress);
        }

        return true;
    }

    @Override
    public boolean updateProgress(int cohort_id, int drink_id, int user_id) {
        Cohort cohort = cohortRepository.findById(cohort_id).get();
        Drink drink = drinkRepository.findById(drink_id).get();
        User user = userRepository.findById(user_id).get();
        Optional<Progress> progressOptional = progressRepository.findByCohortAndDrinkAndUser(cohort, drink, user);

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
            for (Progress p : progressRepository.findAllByUser(user)) {
                ret.add(ProgressDTOMapper.toProgressDTO(p));
            }
            return ret;
        }
        return null;
    }

    @Override
    public Set<ProgressDTO> getProgressByCohort(int cohort_id) {
        HashSet<ProgressDTO> ret = new HashSet<>();
        Optional<Cohort> cohortOptional = cohortRepository.findById(cohort_id);
        if (cohortOptional.isPresent()) {
            Cohort cohort = cohortOptional.get();
            for (Progress p : progressRepository.findAllByCohort(cohort)) {
                ret.add(ProgressDTOMapper.toProgressDTO(p));
            }
            return ret;
        }
        return null;
    }

    @Override
    public ProgressDTO getProgressByDrink(int drink_id, int user_id) {
        Optional<Drink> drinkOptional = drinkRepository.findById(drink_id);
        Optional<User> userOptional = userRepository.findById(user_id);
        if (drinkOptional.isPresent() && userOptional.isPresent()) {
            Drink drink = drinkOptional.get();
            User user = userOptional.get();
            Progress progress = progressRepository.findByDrinkAndUser(drink, user);
            ProgressDTO progressDTO = ProgressDTOMapper.toProgressDTO(progress);
            return progressDTO;
        }
        return null;
    }

}
