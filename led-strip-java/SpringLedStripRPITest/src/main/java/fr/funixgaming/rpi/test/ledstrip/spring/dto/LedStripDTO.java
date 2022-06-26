package fr.funixgaming.rpi.test.ledstrip.spring.dto;

import fr.funixgaming.rpi.test.ledstrip.spring.enums.LedStripStatus;

public class LedStripDTO {
    private LedStripStatus status;
    private RgbDTO rgb;

    public void setStatus(LedStripStatus status) {
        this.status = status;
    }

    public void setRgb(RgbDTO rgb) {
        this.rgb = rgb;
    }

    public LedStripStatus getStatus() {
        return status;
    }

    public RgbDTO getRgb() {
        return rgb;
    }
}
