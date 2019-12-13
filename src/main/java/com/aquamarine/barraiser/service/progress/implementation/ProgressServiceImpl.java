package com.aquamarine.barraiser.service.progress.implementation;

import com.aquamarine.barraiser.dto.model.ProgressDTO;
import com.aquamarine.barraiser.model.Progress;
import com.aquamarine.barraiser.repository.ProgressRepository;
import com.aquamarine.barraiser.service.progress.interfaces.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Override
    public boolean addProgress(int cohort_id, int drink_id, int user_id) {
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
