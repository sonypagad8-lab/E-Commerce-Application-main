package com.jsp.ecommerce.helper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryHelper {

    @Value("${cloudinary.cloud.name}")
    private String name;

    @Value("${cloudinary.api.key}")
    private String key;

    @Value("${cloudinary.api.secret}")
    private String secret;

    public String saveImage(MultipartFile image) {

        // DEBUG - check what values are being read from properties file
        System.out.println("=== CLOUDINARY DEBUG ===");
        System.out.println("cloud_name = [" + name + "]");
        System.out.println("api_key    = [" + key + "]");
        System.out.println("api_secret = [" + secret + "]");
        System.out.println("========================");

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", name,
            "api_key",    key,
            "api_secret", secret
        ));

        try {
            byte[] picture = image.getBytes();

            return (String) cloudinary.uploader()
                    .upload(picture, ObjectUtils.asMap("folder", "ecommerce"))
                    .get("url");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}