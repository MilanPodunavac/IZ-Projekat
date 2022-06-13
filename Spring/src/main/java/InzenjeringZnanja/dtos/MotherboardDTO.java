package InzenjeringZnanja.dtos;

import org.apache.jena.rdf.model.Literal;

import java.util.Date;

public class MotherboardDTO {
    public String name;
    public double cost;
    public Date timeOfIntroduction;
    public boolean embedded;
    public int maxRam;
    public String chipset;
    public int dimmSlots;
    public String format;
    public int m2Connections;
    public int pciex1Slots;
    public int pciex4Slots;
    public int pciex8Slots;
    public int pciex16Slots;
    public int sataConnections;
    public int usbSlots;
    public int ramMaxFrequency;
    public String ramType;
    public String socket;
}
