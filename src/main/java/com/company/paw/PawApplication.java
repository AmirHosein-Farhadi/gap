package com.company.paw;

import com.company.paw.models.Plate;
import com.company.paw.models.Weapon;
import com.company.paw.models.audits.Product;
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
