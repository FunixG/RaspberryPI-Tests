package fr.funixgaming.rpi.test.ledstrip.spring.dto;

import java.security.SecureRandom;

public class RgbDTO {
    public final static int MAX_BRIGHTNESS = 255;

    private Integer red = 0;
    private Integer green = 0;
    private Integer blue = 0;

    public Integer getRed() {
        return red;
    }

    public Integer getBlue() {
        return blue;
    }

    public Integer getGreen() {
        return green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void checkValid() {
        if (red == null || red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }

        if (green == null || green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }

        if (blue == null || blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
    }

    public void generateRandomValues() {
        final SecureRandom random = new SecureRandom();

        this.red = random.nextInt(MAX_BRIGHTNESS + 1);
        this.green = random.nextInt(MAX_BRIGHTNESS + 1);
        this.blue = random.nextInt(MAX_BRIGHTNESS + 1);
    }

    @Override
    public String toString() {
        return String.format("RGB -> R: %d G: %d B: %d", red, green, blue);
    }
}
