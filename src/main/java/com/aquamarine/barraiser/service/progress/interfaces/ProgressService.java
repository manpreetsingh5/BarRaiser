package com.aquamarine.barraiser.service.progress.interfaces;

import com.aquamarine.barraiser.dto.model.ProgressDTO;

import java.util.Set;

public interface ProgressService {
    boolean addProgress(int cohort_id, int drink_id, int user_id);
    boolean updateProgress(int cohort_id, int drink_id, int user_id);
    Set<ProgressDTO> getProgressByUser(int user_id);
}
