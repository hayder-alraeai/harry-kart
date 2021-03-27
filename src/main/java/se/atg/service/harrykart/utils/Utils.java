package se.atg.service.harrykart.utils;
import org.springframework.stereotype.Component;
import se.atg.service.harrykart.model.HarryKartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

@Component
public class Utils {

    public HarryKartType unMarshXmlInputToHarryKartType(File xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HarryKartType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        HarryKartType harryKartType = (HarryKartType) unmarshaller.unmarshal(xml);
        return harryKartType;
    }
    public String marshalHarryKartTypeToXmlFile(HarryKartType harryKartType) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(HarryKartType.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(harryKartType, stringWriter);
        return stringWriter.toString();
    }
}
