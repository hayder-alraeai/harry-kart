package se.atg.service.harrykart.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.atg.service.harrykart.HarryKartApplication;
import se.atg.service.harrykart.model.ResultResponseType;
import se.atg.service.harrykart.utils.Utils;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = HarryKartApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class HarryKartTypeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private Utils utils;
    @Autowired
    private ObjectMapper objectMapper;
    private String xmlFile;
    @BeforeEach
    void setUp() throws IOException {
        xmlFile = utils.readXmlFileFromResources("input_1.xml");
    }
    @Test
    public void test_play_controller_should_success() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Accept","*/*");
        HttpEntity<String> httpEntity = new HttpEntity(xmlFile, httpHeaders);
        ResponseEntity<ResultResponseType> responseEntity = testRestTemplate.postForEntity("http://localhost:8080/api/play", httpEntity, ResultResponseType.class);
        System.out.println(objectMapper.writeValueAsString(responseEntity.getBody()));
    }
}