package org.innopolis.cookainno;

import org.innopolis.cookainno.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class CookainnoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookainnoApplication.class, args);
    }

}
