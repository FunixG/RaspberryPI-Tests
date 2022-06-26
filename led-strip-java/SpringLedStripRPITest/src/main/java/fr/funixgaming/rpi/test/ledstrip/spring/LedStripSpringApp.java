package fr.funixgaming.rpi.test.ledstrip.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "fr.funixgaming.rpi")
public class LedStripSpringApp {
    public static void main(final String[] args) {
        SpringApplication.run(LedStripSpringApp.class);
    }
}
