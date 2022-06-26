package fr.funixgaming.rpi.test.ledstrip.spring.enums;

public enum LedStripStatus {
    STATIC_COLOR(false),
    FADE_BLUE(true),
    EPILEPTIC(true);

    private boolean animated;

    LedStripStatus(boolean animated) {
        this.animated = animated;
    }

    public boolean isAnimated() {
        return animated;
    }
}
