package se.atg.service.harrykart.harrykart.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HarryKartController {

    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public String playHarryKart() {
        return "{ \"message\": \"Don't know how to play yet\" }";
    }
    @GetMapping
    public String getTest(){
        return "hello";
    }
}
