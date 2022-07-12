package InzenjeringZnanja.controller;

import InzenjeringZnanja.dtos.MotherboardDTO;
import InzenjeringZnanja.dtos.MotherboardShortDTO;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Motherboard")
public class MotherboardController {


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MotherboardShortDTO> GetAll(){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?iri ?name \n" +
                "WHERE {\n" +
                "  ?motherboard rdf:type iz:Motherboard .\n" +
                "  OPTIONAL {?motherboard iz:has_a_name ?name .}\n" +
                "  OPTIONAL {?motherboard iz:costs ?cost .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<MotherboardShortDTO> dtos = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            MotherboardShortDTO dto = new MotherboardShortDTO();
            dto.name = (solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : "";
            dto.iri = (solution.getLiteral("iri") != null) ? solution.getLiteral("iri").getString() : "";
            dtos.add(dto);
        }
        return dtos;
    }

    @GetMapping(value = "/{iri}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MotherboardDTO Get(@PathVariable(value="iri") String iri){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?timeOfIntroduction ?embedded ?maxRam ?chipset ?dimmSlots ?format ?m2Connections ?pciex1Slots ?pciex4Slots ?pciex8Slots ?pciex16Slots ?sataConnections ?usbSlots ?ramMaxFrequency ?ramType ?socket\n" +
                "WHERE {\n" +
                "  FILTER(?motherboard =  ontology_instances:" + iri + ")\n" +
                "  ?motherboard rdf:type iz:Motherboard .\n" +
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

    @GetMapping(value = "Compatible/{processorIri}/{coolingSystemIri}/{caseIri}/{soundCardIri}/{ramIri}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MotherboardDTO> GetCompatibleMotherboards(@PathVariable(value="processorIri") String processorIri,
                                                          @PathVariable(value="coolingSystemIri") String coolingSystemIri,
                                                          @PathVariable(value="caseIri") String caseIri,
                                                          @PathVariable(value="soundCardIri") String soundCardIri,
                                                          @PathVariable(value="ramIri") String ramIri){
        String selectString = SparqlConstants.Prefix +
                "SELECT *\n" +
                "WHERE {\n" +
                "?motherboard rdf:type iz:Motherboard .\n" +
                "?motherboard iz:socket ?socket .\n" +
                "ontology_instances:" + processorIri + " iz:socket ?socket .\n" +
                "ontology_instances:" + coolingSystemIri + " iz:socket ?socket .\n" +
                "ontology_instances:" + caseIri + " iz:Computer_casing_supported_motherboard_formats ?format ." +
                "ontology_instances:" + soundCardIri + " iz:chipset ?chipset ." +
                "ontology_instances:" + ramIri + " iz:RAM_type ?ramType ." +
                "ontology_instances:" + ramIri + " iz:RAM_type ?neededRamFrequency ." +
                "FILTER (?neededRamFrequency <=  ?ramMaxFrequency) ." +
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
        ArrayList<MotherboardDTO> dtos = new ArrayList<MotherboardDTO>();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            MotherboardDTO dto = new MotherboardDTO();

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

            dtos.add(dto);
        }
        return dtos;
    }
/*
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Post(@RequestBody MotherboardDTO dto){
        if (dto.name == null) return "Component needs a name";
        String insertString = SparqlConstants.Prefix +
                    "INSERT DATA {\n" +
                    "ontology_instances:" + dto.name + " rdf:type iz:Motherboard ;\n" +
                    "iz:has_a_name \"" + dto.name +  "\" ;\n" +
                    "iz:costs " + dto.cost + " ;\n" +
                    "iz:is_introduced \"" + new SimpleDateFormat("yyyy-MM-dd").format(dto.timeOfIntroduction) + "\" ;\n" +
                    "iz:is_embedded " + dto.embedded + " ;\n" +
                    "iz:maximum_RAM " + dto.maxRam + " ;\n" +
                    "iz:motherboard_chipset \"" + dto.chipset + "\" ;\n" +
                    "iz:motherboard_DIMM_slots " + dto.dimmSlots + " ;\n" +
                    "iz:motherboard_format \"" + dto.format + "\" ;\n" +
                    "iz:motherboard_M2_connections " + dto.m2Connections + " ;\n" +
                    "iz:motherboard_PCIex1_slots " + dto.pciex1Slots + " ;\n" +
                    "iz:motherboard_PCIex4_slots " + dto.pciex4Slots + " ;\n" +
                    "iz:motherboard_PCIex8_slots " + dto.pciex8Slots + " ;\n" +
                    "iz:motherboard_PCIex16_slots " + dto.pciex16Slots + " ;\n" +
                    "iz:motherboard_sataConnections " + dto.sataConnections + " ;\n" +
                    "iz:motherboard_USB_slots " + dto.usbSlots + " ;\n" +
                    "iz:RAM_maximum_frequency " + dto.ramMaxFrequency + " ;\n" +
                    "iz:RAM_type \"" + dto.ramType + "\" ;\n" +
                    "iz:socket \"" + dto.socket + "\" .\n" +
                    "}";

        UpdateRequest updateRequest = UpdateFactory.create(insertString);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, SparqlConstants.UPDATE_URL);
        updateProcessor.execute();
        return "Success!";
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String Delete(@RequestBody MotherboardDTO dto){
        if (dto.name == null) return "No name given";
        String deleteString = SparqlConstants.Prefix +
                "DELETE DATA {\n" +
                "ontology_instances:" + dto.name + " rdf:type iz:Motherboard ;\n" +
                "iz:has_a_name \"" + dto.name +  "\" ;\n" +
                "iz:costs " + dto.cost + " ;\n" +
                "iz:is_introduced \"" + new SimpleDateFormat("yyyy-MM-dd").format(dto.timeOfIntroduction) + "\" ;\n" +
                "iz:is_embedded " + dto.embedded + " ;\n" +
                "iz:maximum_RAM " + dto.maxRam + " ;\n" +
                "iz:motherboard_chipset \"" + dto.chipset + "\" ;\n" +
                "iz:motherboard_DIMM_slots " + dto.dimmSlots + " ;\n" +
                "iz:motherboard_format \"" + dto.format + "\" ;\n" +
                "iz:motherboard_M2_connections " + dto.m2Connections + " ;\n" +
                "iz:motherboard_PCIex1_slots " + dto.pciex1Slots + " ;\n" +
                "iz:motherboard_PCIex4_slots " + dto.pciex4Slots + " ;\n" +
                "iz:motherboard_PCIex8_slots " + dto.pciex8Slots + " ;\n" +
                "iz:motherboard_PCIex16_slots " + dto.pciex16Slots + " ;\n" +
                "iz:motherboard_sataConnections " + dto.sataConnections + " ;\n" +
                "iz:motherboard_USB_slots " + dto.usbSlots + " ;\n" +
                "iz:RAM_maximum_frequency " + dto.ramMaxFrequency + " ;\n" +
                "iz:RAM_type \"" + dto.ramType + "\" ;\n" +
                "iz:socket \"" + dto.socket + "\" .\n" +
                "}";

        UpdateRequest updateRequest = UpdateFactory.create(deleteString);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, SparqlConstants.UPDATE_URL);
        updateProcessor.execute();
        return "Success!";
    }*/
}

