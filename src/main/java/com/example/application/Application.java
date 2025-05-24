package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "my-app")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
       if (System.getenv("SPRING_PROFILES_ACTIVE") == null) {
        Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
        SpringApplication.run(Application.class, args);
    }
}
