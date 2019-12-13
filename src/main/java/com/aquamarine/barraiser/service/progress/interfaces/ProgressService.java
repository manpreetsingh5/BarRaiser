package com.aquamarine.barraiser.service.progress.interfaces;

public interface ProgressService {
    boolean addProgress(int cohort_id, int drink_id, int user_id);
    boolean updateProgress(int cohort_id, int drink_id, int user_id);
}
