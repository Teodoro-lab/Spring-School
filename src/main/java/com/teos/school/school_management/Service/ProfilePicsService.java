package com.teos.school.school_management.Service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePicsService {

    public static String uploadProfilePicture(String fileObjKeyName, MultipartFile file) {
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = "sicei-aws-profile-pictures";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Get the content type of the file
            String contentType = file.getContentType();

            // Upload a file as a new object with ContentType and title specified.
            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(file.getSize());
            metadata.addUserMetadata("title", "Profile Picture");

            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, inputStream, metadata);
            s3Client.putObject(request);

            // Return the URL of the uploaded file
            return s3Client.getUrl(bucketName, fileObjKeyName).toString();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

