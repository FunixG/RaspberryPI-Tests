package fr.funixgaming.rpi.test.ledstrip.spring.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rpi.gpio")
public class LedStripGpioConfig {
    private Integer redPin;
    private Integer greenPin;
    private Integer bluePin;

    public Integer getBluePin() {
        return bluePin;
    }

    public Integer getGreenPin() {
        return greenPin;
    }

    public Integer getRedPin() {
        return redPin;
    }

    public void setBluePin(int bluePin) {
        this.bluePin = bluePin;
    }

    public void setGreenPin(int greenPin) {
        this.greenPin = greenPin;
    }

    public void setRedPin(int redPin) {
        this.redPin = redPin;
    }
}
