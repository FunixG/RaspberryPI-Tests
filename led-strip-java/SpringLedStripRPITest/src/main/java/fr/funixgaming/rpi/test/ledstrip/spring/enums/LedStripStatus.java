package fr.funixgaming.rpi.test.ledstrip.spring.enums;

public enum LedStripStatus {
    STATIC_COLOR(false),
    FADE_BLUE(true),
    ALERT(true),
    EPILEPTIC(true);

    private final boolean animated;

    LedStripStatus(boolean animated) {
        this.animated = animated;
    }

    public boolean isAnimated() {
        return animated;
    }
}
