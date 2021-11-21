package com.akriuchk.imageservice;

import com.akriuchk.imageservice.service.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ImageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageServiceApplication.class, args);
    }

    @Bean
    @Profile("demo")
    public CommandLineRunner initialData(
            @Value("${data.input.filename:source-data.json}") String dataSource,
            Parser parser) {
        return args -> {
            parser.parse(dataSource);
        };
    }

}
