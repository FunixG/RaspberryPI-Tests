package fr.funixgaming.rpi.test.ledstrip.spring.resource;

import fr.funixgaming.rpi.test.ledstrip.spring.dto.LedStripDTO;
import fr.funixgaming.rpi.test.ledstrip.spring.service.LedStripService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedStripResource {

    private final LedStripService service;

    public LedStripResource(LedStripService service) {
        this.service = service;
    }

    @GetMapping
    public LedStripDTO getActualStatus() {
        return service.getStatusLedStrip();
    }

    @PostMapping
    public void changeStatus(@RequestBody final LedStripDTO stripDTO) {
        service.setLedStatus(stripDTO);
    }

}
