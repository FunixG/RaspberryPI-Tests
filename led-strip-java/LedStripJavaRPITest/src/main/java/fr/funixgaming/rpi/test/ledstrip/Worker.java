package fr.funixgaming.rpi.test.ledstrip;

import java.time.Instant;
import java.util.Random;

public class Worker implements Runnable {
    private static final int MAX_BRIGHTNESS = 256;
    private final Main main;

    public Worker(final Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        final Instant end = Instant.now().plusSeconds(60);

        while (Instant.now().isBefore(end)) {
            final Random random = new Random();
            final int redBright = random.nextInt(MAX_BRIGHTNESS);
            final int greenBright = random.nextInt(MAX_BRIGHTNESS);
            final int blueBright = random.nextInt(MAX_BRIGHTNESS);

            main.setRedBright(redBright);
            main.setGreenBright(greenBright);
            main.setBlueBright(blueBright);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Program stopped");
                main.stop();
            }
        }
    }
}
