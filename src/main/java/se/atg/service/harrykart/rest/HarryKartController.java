package se.atg.service.harrykart.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.atg.service.harrykart.model.*;
import se.atg.service.harrykart.service.HarryKartService;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class HarryKartController {

    private HarryKartService harryKartService;

    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public ResultResponseType playHarryKart(@RequestBody HarryKartType harryKartType) {
        return harryKartService.playHarryKart(harryKartType);
    }
}
