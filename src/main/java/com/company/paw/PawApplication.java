package com.company.paw;

import com.company.paw.payload.models.UploadedPicture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableConfigurationProperties({
        UploadedPicture.class
})
@SpringBootApplication
@EnableMongoAuditing
public class PawApplication {
    public static void main(String[] args) {
        SpringApplication.run(PawApplication.class, args);
    }
}
