package com.aquamarine.barraiser.service.images.interfaces;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    void uploadFileToS3bucket(String filePath, File file);
    S3Object downloadFileFromS3bucket(String sub_folder);
    void deleteFileFromS3bucket(String filePath);
    File convertMultiPartToFile(MultipartFile file, String fileName) throws IOException;
}
