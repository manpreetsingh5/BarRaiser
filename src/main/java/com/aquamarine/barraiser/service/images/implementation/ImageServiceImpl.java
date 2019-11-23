package com.aquamarine.barraiser.service.images.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.aquamarine.barraiser.service.images.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Override
    public void uploadFileToS3bucket(String filePath, File file) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, filePath, file));
    }

    @Override
    public S3Object downloadFileFromS3bucket(String sub_folder) {
        S3Object object = this.amazonS3Client.getObject(bucketName, sub_folder);
        return object;
    }

    @Override
    public void deleteFileFromS3bucket(String filePath) {
        this.amazonS3Client.deleteObject(bucketName, filePath);
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file, String fileName) throws IOException {
        System.out.println(file.getOriginalFilename());
        File convFile = new File(fileName);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
