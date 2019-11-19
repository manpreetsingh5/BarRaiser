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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        File file = imageService.convertMultiPartToFile(multipartFile);
        String fileName = cohortdto.getDescription();
        imageService.uploadFileToS3bucket(fileName, file, sub_folder);

        Cohort cohort = new Cohort()
                .setDescription(cohortdto.getDescription())
                .setInstructor(userRepository.findById(cohortdto.getInstructor()).get())
                .setImage_path(sub_folder+"/"+fileName);

        System.out.println(fileName);
        System.out.println(sub_folder);


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
    public ResponseEntity<byte[]> getCohortPicture(CohortDTO cohortDTO) throws IOException {
        Cohort cohort = cohortRepository.findById(cohortDTO.getId()).get();
        System.out.println(bucketName+sub_folder+cohort.getDescription());
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
    public void addUserToCohort(CohortDTO cohortDTO, UserDTO userDTO) {
        int cohortID = cohortDTO.getId();
        System.out.println("ID is" + cohortID);
        if (cohortRepository.findById(1).isPresent()) {
            Cohort cohort = cohortRepository.findById(1).get();

            Set<User> users = cohort.getUser();
            users.add(userRepository.findById(userDTO.getId()).get());

            cohort.setUser(users);
            System.out.println(users.size());
            cohortRepository.save(cohort);
        }
    }

    @Override
    public Set<Object> findById(int id) {
        HashSet<Object> ret = new HashSet<>();
        System.out.println(id);
        Cohort res = cohortRepository.findById(id).get();
        ret.add(Arrays.asList(CohortDTOMapper.toCohortDTO(res), imageService.downloadFileFromS3bucket(res.getImage_path(), "/cohorts/")));
        return ret;
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
            cohortRepository.save(cohort);
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
            res.add(UserDTOMapper.toUserDTO(user));
        }

        return res;
    }

    @Override
    public Set<CohortDTO> getUserCohorts(UserDTO userDTO) {
        String status = userDTO.getStatus();
        Set<CohortDTO> res = new HashSet<>();

        if (status == "BARTENDER") {
            Set<Cohort> cohorts = cohortRepository.findAllByInstructor(userRepository.findById(userDTO.getId()).get());
            for (Cohort c : cohorts) {
                res.add(CohortDTOMapper.toCohortDTO(c));
            }
        }
        else if (status == "TRAINEE") {
            Set<Cohort> cohorts = userRepository.findById(userDTO.getId()).get().getCohort();

            for (Cohort c : cohorts) {
                res.add(CohortDTOMapper.toCohortDTO(c));
            }
        }

        return res;

    }





}
