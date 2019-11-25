package com.aquamarine.barraiser.service.actions.implementation;

import com.aquamarine.barraiser.dto.mapper.CohortDTOMapper;
import com.aquamarine.barraiser.dto.model.CohortDTO;
import com.aquamarine.barraiser.model.Actions;
import com.aquamarine.barraiser.repository.ActionRepository;
import com.aquamarine.barraiser.service.actions.interfaces.ActionService;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ImageService imageService;

    @Value("images/cohorts/")
    private String sub_folder;

    @Override
    public Set<Map<String, Object>> getAllActions() throws IOException {
        Set<Map<String, Object>> ret = new HashSet<>();
        List<Actions> actions = actionRepository.findAll();
        for (Actions a : actions) {
            HashMap<String, Object> action = new HashMap<>();
            action.put("action", a);
            InputStream in = imageService.downloadFileFromS3bucket(a.getImage_path()).getObjectContent();
            BufferedImage imageFromAWS = ImageIO.read(in);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageFromAWS, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            in.close();
            action.put("file", imageBytes);
        }
        return ret;
    }
}
