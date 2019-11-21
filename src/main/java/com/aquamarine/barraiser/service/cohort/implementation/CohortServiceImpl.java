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
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CohortServiceImpl implements CohortService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CohortRepository cohortRepository;

    @Autowired
    private ImageService imageService;

    @Value("images/cohorts/")
    private String sub_folder;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    private UserDTOMapper userDTOMapper = new UserDTOMapper();

    @Override
    public int createCohort(CohortDTO cohortdto, MultipartFile multipartFile) throws IOException {
        String fileName = cohortdto.getName();
        File file = imageService.convertMultiPartToFile(multipartFile, fileName);
        imageService.uploadFileToS3bucket(fileName, file, sub_folder);

        Cohort cohort = new Cohort()
                .setName(cohortdto.getName())
                .setDescription(cohortdto.getDescription())
                .setInstructor(userRepository.findById(cohortdto.getInstructor()).get())
                .setImage_path(sub_folder+fileName);


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
    public ResponseEntity<byte[]> getCohortPicture(int cohort_id) throws IOException {
        Cohort cohort = cohortRepository.findById(cohort_id).get();
        InputStream in = imageService.downloadFileFromS3bucket(bucketName, cohort.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(imageBytes.length);
        httpHeaders.setContentDispositionFormData("attachment", cohort.getDescription());

        return new ResponseEntity<>(imageBytes, httpHeaders, HttpStatus.OK);
    }

    @Override
    public void addUserToCohort(int cohort_id, int user_id) {
        if (cohortRepository.findById(cohort_id).isPresent()) {
            Cohort cohort = cohortRepository.findById(cohort_id).get();

            Set<User> users = cohort.getUser();
            users.add(userRepository.findById(user_id).get());

            cohort.setUser(users);
            cohortRepository.save(cohort);
        }
    }

    @Override
    public Map<String, Object> findById(int id) throws IOException {
        HashMap<String, Object> ret = new HashMap<>();
        CohortDTO cohortDTO = CohortDTOMapper.toCohortDTO(cohortRepository.findById(id).get());
        ret.put("cohort", cohortDTO);
        InputStream in = imageService.downloadFileFromS3bucket(bucketName, cohortDTO.getImage_path()).getObjectContent();
        BufferedImage imageFromAWS = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageFromAWS, "png", baos );
        byte[] imageBytes = baos.toByteArray();
        in.close();
        ret.put("file", imageBytes);
        return ret;
    }

    @Override
    public UserDTO deleteStudentFromCohort(int cohort_id, int user_id) {
        if (cohortRepository.findById(cohort_id).isPresent()) {
            Cohort cohort = cohortRepository.findById(cohort_id).get();

            Set<User> users = cohort.getUser();
            User user = userRepository.findById(user_id).get();

            users.remove(user);

            cohort.setUser(users);
            cohortRepository.save(cohort);
            return UserDTOMapper.toUserDTO(user);
        }
        else {
            return null;
        }
    }

    @Override
    public Set<UserDTO> getCohortUsers(int cohort_id) {
        Set<User> users = cohortRepository.findById(cohort_id).get().getUser();
        Set<UserDTO> res = new HashSet<>();

        for (User user : users) {
            res.add(UserDTOMapper.toUserDTO(user));
        }

        return res;
    }

    @Override
    public Set<CohortDTO> getUserCohorts(int user_id) {
        User user = userRepository.findById(user_id).get();
        String status = user.getStatus();
        Set<CohortDTO> res = new HashSet<>();

        if (status.equals("BARTENDER")) {
            Set<Cohort> cohorts = cohortRepository.findAllByInstructor(userRepository.findById(user_id).get());
            for (Cohort c : cohorts) {
                c.setUser(null);
                res.add(CohortDTOMapper.toCohortDTO(c));
            }
        }
        else if (status.equals("TRAINEE")) {
            Set<Cohort> cohorts = userRepository.findById(user_id).get().getCohort();

            for (Cohort c : cohorts) {
                c.setUser(null);
                res.add(CohortDTOMapper.toCohortDTO(c));
            }
        }

        return res;

    }





}
