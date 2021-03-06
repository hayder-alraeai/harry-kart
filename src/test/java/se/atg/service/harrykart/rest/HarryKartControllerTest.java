package se.atg.service.harrykart.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.ResultResponse;
import se.atg.service.harrykart.utils.Utils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class HarryKartControllerTest {
    @LocalServerPort
    private int port;
    public URI uri;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Utils utils;
    private String xmlInputFile;
    @Value("classpath:input_1.xml")
    private Resource getResource;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        uri = new URI("http://localhost:" + port + "/api/play");
        xmlInputFile = new String(Files.readAllBytes(getResource.getFile().toPath()));
    }
    @Test
    @Description("This test sends xml file to play controller and make sure that http status is 200 and write the result to console")
    public void test_play_controller_should_success() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Accept","*/*");
        HttpEntity<String> httpEntity = new HttpEntity(xmlInputFile, httpHeaders);
        ResponseEntity<ResultResponse> responseEntity = testRestTemplate.exchange( uri ,HttpMethod.POST, httpEntity, ResultResponse.class);
        System.out.println( "\033[0;32m" + "This test is created with " + getResource.getFile().getName() + " file!" + "\033[0m");
        System.out.println(objectMapper.writeValueAsString(responseEntity.getBody()));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }
    @Test
    @Description("This test send xml file with first participant's baseSpeed zero and should fail with http status 400")
    public void test_play_controller_with_zero_baseSpeed() throws IOException, JAXBException {
        HarryKart harryKart = utils.unMarshXmlInputToHarryKart(getResource.getFile());
        harryKart.getStartList().getParticipant().get(0).setBaseSpeed(BigInteger.valueOf(0));
        String input = utils.marshalHarryKartToXmlFile(harryKart);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Accept","*/*");
        HttpEntity<String> httpEntity = new HttpEntity(input, httpHeaders);
        ResponseEntity<ResultResponse> responseEntity = testRestTemplate.exchange( uri ,HttpMethod.POST, httpEntity, ResultResponse.class);
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}