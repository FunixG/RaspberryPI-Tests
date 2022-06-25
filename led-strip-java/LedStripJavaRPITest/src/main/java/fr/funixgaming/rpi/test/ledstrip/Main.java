package fr.funixgaming.rpi.test.ledstrip;

import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final String COMMAND_LINE = "pigs p %s %d";

    private final Runtime runtime;

    private final String redPin;
    private final String greenPin;
    private final String bluePin;

    private Main() throws Exception {
        this.runtime = Runtime.getRuntime();

        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("program.properties"));

        this.redPin = properties.getProperty("LED_RED_PIN");
        this.greenPin = properties.getProperty("LED_GREEN_PIN");
        this.bluePin = properties.getProperty("LED_BLUE_PIN");
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setBlueBright(int brightness) {
        brightness = checkArg(brightness);
        runCommand(bluePin, brightness);
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setRedBright(int brightness) {
        brightness = checkArg(brightness);
        runCommand(redPin, brightness);
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setGreenBright(int brightness) {
        brightness = checkArg(brightness);
        runCommand(greenPin, brightness);
    }

    private void runCommand(final String pin, final int brightness) {
        new Thread(() -> {
            try {
                runtime.exec(String.format(COMMAND_LINE, pin, brightness));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }).start();
    }

    private int checkArg(int arg) {
        if (arg < 0) {
            return 0;
        } else if (arg > 255) {
            return 255;
        } else {
            return arg;
        }
    }

    public static void main(final String[] args) {
        try {
            final Main main = new Main();
            final Worker worker = new Worker(main);

            worker.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
