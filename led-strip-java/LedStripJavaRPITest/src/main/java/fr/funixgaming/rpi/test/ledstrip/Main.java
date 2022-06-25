package fr.funixgaming.rpi.test.ledstrip;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;

import java.util.Properties;

public class Main {
    private volatile static Main instance = null;

    private final Context pi4J;

    private final DigitalOutput redPin;
    private final DigitalOutput greenPin;
    private final DigitalOutput bluePin;

    private Main() throws Exception {
        final Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("program.properties"));

        this.pi4J = Pi4J.newAutoContext();

        this.redPin = pi4J.digitalOutput().create(Integer.parseInt(properties.getProperty("LED_RED_PIN")));
        this.greenPin = pi4J.digitalOutput().create(Integer.parseInt(properties.getProperty("LED_GREEN_PIN")));
        this.bluePin = pi4J.digitalOutput().create(Integer.parseInt(properties.getProperty("LED_BLUE_PIN")));
    }

    public void stop() {
        this.pi4J.shutdown();
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setBlueBright(int brightness) {
        brightness = checkArg(brightness);
        bluePin.setState(brightness);
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setRedBright(int brightness) {
        brightness = checkArg(brightness);
        redPin.setState(brightness);
    }

    /**
     * Change blue led strip
     * @param brightness Between 0 and 255
     */
    public void setGreenBright(int brightness) {
        brightness = checkArg(brightness);
        greenPin.setState(brightness);
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
            instance = new Main();

            final Worker worker = new Worker(instance);
            worker.run();

            instance.stop();
        } catch (Exception e) {
            if (instance != null) {
                instance.stop();
            }

            e.printStackTrace();
            System.exit(1);
        }
    }

}
