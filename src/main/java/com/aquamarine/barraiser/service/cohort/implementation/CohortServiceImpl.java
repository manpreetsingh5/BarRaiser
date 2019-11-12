package com.aquamarine.barraiser.service.cohort.implementation;

import com.aquamarine.barraiser.dto.mapper.CohortDTOMapper;
import com.aquamarine.barraiser.dto.mapper.UserDTOMapper;
import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.dto.model.UserDTO;
import com.aquamarine.barraiser.model.Cohort;
import com.aquamarine.barraiser.model.User;
import com.aquamarine.barraiser.repository.CohortRepository;
import com.aquamarine.barraiser.repository.UserRepository;
import com.aquamarine.barraiser.service.cohort.interfaces.CohortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CohortServiceImpl implements CohortService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CohortRepository cohortRepository;

    private UserDTOMapper userDTOMapper = new UserDTOMapper();

    @Override
    public int createCohort(CohortDTO cohortdto) {
        Cohort cohort = new Cohort()
                .setDescription(cohortdto.getDescription())
                .setInstructor(userRepository.findById(cohortdto.getInstructor()).get())
                .setUser(cohortdto.getStudents());

        cohortRepository.save(cohort);
        return cohort.getId();
    }

    @Override
    public void deleteCohort(CohortDTO cohortDTO) {
        int cohortID = cohortDTO.getId();
        if (cohortRepository.findById(cohortID).isPresent()) {
            cohortRepository.delete(cohortRepository.findById(cohortID).get());
        }
    }

    @Override
    public void addUserToCohort(CohortDTO cohortDTO, UserDTO userDTO) {
        int cohortID = cohortDTO.getId();
        if (cohortRepository.findById(cohortID).isPresent()) {
            Cohort cohort = cohortRepository.findById(cohortID).get();

            Set<User> users = cohort.getUser();
            users.add(userRepository.findById(userDTO.getId()).get());

            cohort.setUser(users);
        }
    }

    @Override
    public UserDTO deleteStudentFromCohort(CohortDTO cohortDTO, UserDTO userDTO) {
        int cohortID = cohortDTO.getId();
        if (cohortRepository.findById(cohortID).isPresent()) {
            Cohort cohort = cohortRepository.findById(cohortID).get();

            Set<User> users = cohort.getUser();
            User user = userRepository.findById(userDTO.getId()).get();

            users.remove(user);

            cohort.setUser(users);

            return UserDTOMapper.toUserDTO(user);
        }
        else {
            return null;
        }


    }

    @Override
    public Set<UserDTO> getCohortUsers(CohortDTO cohortDTO) {
        Set<User> users = cohortRepository.findById(cohortDTO.getId()).get().getUser();
        Set<UserDTO> res = new HashSet<>();

        for (User user : users) {
            res.add(userDTOMapper.toUserDTO(user));
        }

        return res;
    }


}
