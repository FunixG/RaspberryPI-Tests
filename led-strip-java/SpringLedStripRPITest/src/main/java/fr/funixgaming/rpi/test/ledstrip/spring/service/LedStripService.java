package fr.funixgaming.rpi.test.ledstrip.spring.service;

import fr.funixgaming.rpi.test.ledstrip.spring.configuration.LedStripGpioConfig;
import fr.funixgaming.rpi.test.ledstrip.spring.dto.LedStripDTO;
import fr.funixgaming.rpi.test.ledstrip.spring.dto.RgbDTO;
import fr.funixgaming.rpi.test.ledstrip.spring.enums.LedStripStatus;
import org.springframework.scheduling.annotation.Async;
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

        final LedStripDTO ledStripDTO = new LedStripDTO();
        ledStripDTO.setStatus(LedStripStatus.STATIC_COLOR);
        ledStripDTO.setRgb(new RgbDTO());

        setLedStatus(ledStripDTO);
    }

    @Async
    public void setLedStatus(final LedStripDTO request) {
        if (request.getStatus() == null) {
            return;
        }

        this.statusLedStrip = request;
        if (request.getStatus().isAnimated()) {
            worker();
        } else {
            executeGpioChange();
        }
    }

    public LedStripDTO getStatusLedStrip() {
        return statusLedStrip;
    }

    private void worker() {
        while (statusLedStrip.getStatus().isAnimated()) {
            switch (statusLedStrip.getStatus()) {
                case FADE_BLUE -> {
                    //TODO fade blue
                }
                case EPILEPTIC -> {
                    final RgbDTO rgbDTO = new RgbDTO();
                    rgbDTO.generateRandomValues();

                    changeGPIO(rgbDTO);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    void executeGpioChange() {
        if (statusLedStrip.getRgb() != null) {
            changeGPIO(statusLedStrip.getRgb());
        }
    }

    private void changeGPIO(final RgbDTO rgbDTO) {
        try {
            rgbDTO.checkValid();

            runtime.exec(String.format(
                    COMMAND_LINE + " && " + COMMAND_LINE + " && " + COMMAND_LINE,
                    config.getRedPin(), rgbDTO.getRed(),
                    config.getGreenPin(), rgbDTO.getGreen(),
                    config.getBluePin(), rgbDTO.getBlue())
            );
        } catch (Exception e) {
            log.log(Level.WARNING, String.format("Erreur exec commande (%s) erreur: %s", rgbDTO, e.getMessage()));
        }
    }
}
