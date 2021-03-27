package se.atg.service.harrykart.utils;
import org.springframework.stereotype.Component;
import se.atg.service.harrykart.model.HarryKart;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

@Component
public class Utils {

    public HarryKart unMarshXmlInputToHarryKart(File xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HarryKart.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        HarryKart harryKartType = (HarryKart) unmarshaller.unmarshal(xml);
        return harryKartType;
    }
    public String marshalHarryKartToXmlFile(HarryKart harryKartType) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HarryKart.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(harryKartType, stringWriter);
        return stringWriter.toString();
    }
}
