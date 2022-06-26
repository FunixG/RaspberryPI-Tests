package fr.funixgaming.rpi.test.ledstrip.spring.service;

import fr.funixgaming.rpi.test.ledstrip.spring.configuration.LedStripGpioConfig;
import fr.funixgaming.rpi.test.ledstrip.spring.dto.LedStripDTO;
import fr.funixgaming.rpi.test.ledstrip.spring.dto.RgbDTO;
import fr.funixgaming.rpi.test.ledstrip.spring.enums.LedStripStatus;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LedStripService {
    private static final String COMMAND_LINE = "pigs p %d %d";

    private final Logger log;
    private final Runtime runtime;
    private final LedStripGpioConfig config;

    private LedStripDTO statusLedStrip;

    public LedStripService(LedStripGpioConfig config) {
        this.log = Logger.getLogger("LedStripService");
        this.runtime = Runtime.getRuntime();
        this.config = config;

        setLedStatus(new LedStripDTO());
    }

    public void setLedStatus(final LedStripDTO request) {
        if (request.getStatus() == null) {
            request.setStatus(LedStripStatus.STATIC_COLOR);
        }
        if (request.getRgb() == null) {
            request.setRgb(new RgbDTO());
        }

        this.statusLedStrip = request;

        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {
        }

        if (request.getStatus().isAnimated()) {
            worker();
        } else {
            changeGPIO(this.statusLedStrip.getRgb());
        }
    }

    public LedStripDTO getStatusLedStrip() {
        return statusLedStrip;
    }

    private void worker() {
        while (statusLedStrip.getStatus().isAnimated()) {
            RgbDTO rgbDTO = statusLedStrip.getRgb();

            switch (statusLedStrip.getStatus()) {
                case FADE_BLUE -> {
                    rgbDTO.setRed(0);
                    rgbDTO.setGreen(100);
                    rgbDTO.setBlue(rgbDTO.getBlue() + 10);
                    if (rgbDTO.getBlue() > RgbDTO.MAX_BRIGHTNESS) {
                        rgbDTO.setBlue(10);
                    }
                }

                case ALERT -> {
                    rgbDTO.setGreen(0);
                    rgbDTO.setBlue(0);
                    rgbDTO.setRed(rgbDTO.getRed() + 10);
                    if (rgbDTO.getRed() > RgbDTO.MAX_BRIGHTNESS) {
                        rgbDTO.setRed(10);
                    }
                }

                case EPILEPTIC -> rgbDTO.generateRandomValues();
            }

            this.statusLedStrip.setRgb(rgbDTO);
            changeGPIO(rgbDTO);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void changeGPIO(RgbDTO rgbDTO) {
        try {
            if (rgbDTO == null) {
                rgbDTO = new RgbDTO();
            }
            rgbDTO.checkValid();

            runtime.exec(String.format(COMMAND_LINE, config.getRedPin(), rgbDTO.getRed()));
            runtime.exec(String.format(COMMAND_LINE, config.getGreenPin(), rgbDTO.getGreen()));
            runtime.exec(String.format(COMMAND_LINE, config.getBluePin(), rgbDTO.getBlue()));
        } catch (Exception e) {
            log.log(Level.WARNING, String.format("Erreur exec commande (%s) erreur: %s", rgbDTO, e.getMessage()));
        }
    }
}
