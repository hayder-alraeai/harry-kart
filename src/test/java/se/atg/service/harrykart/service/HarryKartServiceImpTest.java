package se.atg.service.harrykart.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import se.atg.service.harrykart.model.HarryKartType;
import se.atg.service.harrykart.utils.Utils;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class HarryKartServiceImpTest {
    @Autowired
    private HarryKartServiceImp harryKartServiceImp;
    @Autowired
    private Utils utils;
    @Value("classpath:input_1.xml")
    private Resource getResource;
    private HarryKartType harryKartType;

    @BeforeEach
    void setUp() throws IOException, JAXBException {
        harryKartType = utils.unMarshXmlInputToHarryKartType(getResource.getFile());
    }

    @Test
    void play() {
        assertThat(harryKartServiceImp.play(harryKartType).getRanking().get(0).getHorse(), equalTo("WAIKIKI SILVIO"));
    }

    @Test
    void prepareHorsesToTheRacing() {
        assertThat(harryKartServiceImp.
                prepareHorsesToTheRacing(harryKartType.
                        getStartList()
                        .getParticipant()).get(0).getClass().getSimpleName(),
                equalTo("HorseInRace"));
    }

    @Test
    void startRacing() {
        assertThat(harryKartServiceImp.startRacing(
                harryKartType,
                harryKartServiceImp.prepareHorsesToTheRacing(harryKartType.getStartList().getParticipant())
        ).get(0).getDurationTime().size()
                , equalTo(3));
    }

    @Test
    void getRanking() {
        assertThat(harryKartServiceImp.getRanking(harryKartServiceImp
                .prepareHorsesToTheRacing(harryKartType.getStartList().
                        getParticipant())).getRanking().size(), equalTo(3));
    }
}