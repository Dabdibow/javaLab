package ru.itis.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.itis.practice.property.FileStorageProperties;


@EnableConfigurationProperties({
        FileStorageProperties.class
})

@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {

        SpringApplication.run(PracticeApplication.class, args);
    }

}
