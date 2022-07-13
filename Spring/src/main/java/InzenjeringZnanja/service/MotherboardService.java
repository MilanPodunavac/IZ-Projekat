package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.MemoryDiscDto;
import InzenjeringZnanja.dtos.MotherboardDTO;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class MotherboardService {
    public MotherboardDTO Get(String name){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?timeOfIntroduction ?embedded ?maxRam ?chipset ?dimmSlots ?format ?m2Connections ?pciex1Slots ?pciex4Slots ?pciex8Slots ?pciex16Slots ?sataConnections ?usbSlots ?ramMaxFrequency ?ramType ?socket\n" +
                "WHERE {\n" +
                "?motherboard rdf:type iz:Motherboard .\n" +
                "?motherboard iz:has_a_name \"" + name + "\".\n" +
                "  OPTIONAL {?motherboard iz:has_a_name ?name ;}\n" +
                "  OPTIONAL {?motherboard iz:costs ?cost .}\n" +
                "  OPTIONAL {?motherboard iz:is_introduced ?timeOfIntroduction .}\n" +
                "  OPTIONAL {?motherboard iz:is_embedded ?embedded .}\n" +
                "  OPTIONAL {?motherboard iz:maximum_RAM ?maxRam .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_chipset ?chipset .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_DIMM_slots ?dimmSlots .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_format ?format .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_M2_connections ?m2Connections .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_PCIex1_slots ?pciex1Slots .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_PCIex4_slots ?pciex4Slots .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_PCIex8_slots ?pciex8Slots .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_PCIex16_slots ?pciex16Slots .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_sataConnections ?sataConnections .}\n" +
                "  OPTIONAL {?motherboard iz:motherboard_USB_slots ?usbSlots .}\n" +
                "  OPTIONAL {?motherboard iz:RAM_maximum_frequency ?ramMaxFrequency .}\n" +
                "  OPTIONAL {?motherboard iz:RAM_type ?ramType .}\n" +
                "  OPTIONAL {?motherboard iz:socket ?socket .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        MotherboardDTO dto = new MotherboardDTO();
        if (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            dto.name = (solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : null;
            dto.cost = (solution.getLiteral("cost") != null) ? solution.getLiteral("cost").getDouble() : -1.0;
            dto.timeOfIntroduction = (solution.getLiteral("timeOfIntroduction") != null) ? Date.valueOf(solution.getLiteral("timeOfIntroduction").getString()) : null;
            dto.embedded = (solution.getLiteral("embedded") != null) ? solution.getLiteral("embedded").getBoolean() : false;
            dto.maxRam = (solution.getLiteral("maxRam") != null) ? solution.getLiteral("maxRam").getInt() : -1;
            dto.chipset = (solution.getLiteral("chipset") != null) ? solution.getLiteral("chipset").getString() : null;
            dto.dimmSlots = (solution.getLiteral("dimmSlots") != null) ? solution.getLiteral("dimmSlots").getInt() : -1;
            dto.format = (solution.getLiteral("format") != null) ? solution.getLiteral("format").getString() : null;
            dto.m2Connections = (solution.getLiteral("m2Connections") != null) ? solution.getLiteral("m2Connections").getInt() : -1;
            dto.pciex1Slots = (solution.getLiteral("pciex1Slots") != null) ? solution.getLiteral("pciex1Slots").getInt() : -1;
            dto.pciex4Slots = (solution.getLiteral("pciex4Slots") != null) ? solution.getLiteral("pciex4Slots").getInt() : -1;
            dto.pciex8Slots = (solution.getLiteral("pciex8Slots") != null) ? solution.getLiteral("pciex8Slots").getInt() : -1;
            dto.pciex16Slots = (solution.getLiteral("pciex16Slots") != null) ? solution.getLiteral("pciex16Slots").getInt() : -1;
            dto.sataConnections = (solution.getLiteral("sataConnections") != null) ? solution.getLiteral("sataConnections").getInt() : -1;
            dto.usbSlots = (solution.getLiteral("usbSlots") != null) ? solution.getLiteral("usbSlots").getInt() : -1;
            dto.ramMaxFrequency = (solution.getLiteral("ramMaxFrequency") != null) ? solution.getLiteral("ramMaxFrequency").getInt() : -1;
            dto.ramType = (solution.getLiteral("ramType") != null) ? solution.getLiteral("ramType").getString() : null;
            dto.socket = (solution.getLiteral("socket") != null) ? solution.getLiteral("socket").getString() : null;
        }
        return dto;
    }
}
