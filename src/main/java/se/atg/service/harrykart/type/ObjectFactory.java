//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.25 at 05:03:56 PM CET 
//


package se.atg.service.harrykart.type;

import se.atg.service.harrykart.model.HarryKart;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HarryKart_QNAME = new QName("", "harryKart");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HarryKart }
     * 
     */
    public HarryKart createHarryKart() {
        return new HarryKart();
    }

    /**
     * Create an instance of {@link LoopType }
     * 
     */
    public LoopType createLoopType() {
        return new LoopType();
    }

    /**
     * Create an instance of {@link LaneType }
     * 
     */
    public LaneType createLaneType() {
        return new LaneType();
    }

    /**
     * Create an instance of {@link StartListType }
     * 
     */
    public StartListType createStartListType() {
        return new StartListType();
    }

    /**
     * Create an instance of {@link PowerUpsType }
     * 
     */
    public PowerUpsType createPowerUpsType() {
        return new PowerUpsType();
    }

    /**
     * Create an instance of {@link ParticipantType }
     * 
     */
    public ParticipantType createParticipantType() {
        return new ParticipantType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HarryKart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "harryKart")
    public JAXBElement<HarryKart> createHarryKart(HarryKart value) {
        return new JAXBElement<HarryKart>(_HarryKart_QNAME, HarryKart.class, null, value);
    }

}
