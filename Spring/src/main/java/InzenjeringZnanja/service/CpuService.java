package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.ComponentShortDto;
import InzenjeringZnanja.dto.CpuDto;
import InzenjeringZnanja.dtos.MotherboardDTO;
import InzenjeringZnanja.dtos.MotherboardShortDTO;
import InzenjeringZnanja.global.SparqlConstants;
import InzenjeringZnanja.model.CentralProcessingUnit;
import org.apache.jena.query.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CpuService {

    public CentralProcessingUnit Get(String name){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?can_multithread ?base_frequency ?number_of_cores ?thermal_design_power ?register_type ?L1_cache ?L2_cache ?L3_cache ?maximumRAM ?number_of_transistors ?process_microns ?RAM_maximum_frequency ?socket ?turbo_frequency ?voltage \n" +
                "WHERE {\n" +
                "?cpu rdf:type iz:Processor .\n" +
                "?cpu iz:has_a_name \"" + name + "\".\n" +
                "  ?cpu rdf:type iz:Processor .\n" +
                "  OPTIONAL {?cpu iz:has_a_name ?name ;}\n" +
                "  OPTIONAL {?cpu iz:costs ?cost .}\n" +
                "  OPTIONAL {?cpu iz:can_multithread ?can_multithread .}\n" +
                "  OPTIONAL {?cpu iz:base_frequency ?base_frequency .}\n" +
                "  OPTIONAL {?cpu iz:number_of_cores ?number_of_cores .}\n" +
                "  OPTIONAL {?cpu iz:thermal_design_power ?thermal_design_power .}\n" +
                "  OPTIONAL {?cpu iz:register_type ?register_type .}\n" +
                "  OPTIONAL {?cpu iz:L1_cache ?L1_cache .}\n" +
                "  OPTIONAL {?cpu iz:L2_cache ?L2_cache .}\n" +
                "  OPTIONAL {?cpu iz:L3_cache ?L3_cache .}\n" +
                "  OPTIONAL {?cpu iz:maximum_RAM ?maximumRAM .}\n" +
                "  OPTIONAL {?cpu iz:number_of_transistors ?number_of_transistors .}\n" +
                "  OPTIONAL {?cpu iz:process_microns ?process_microns .}\n" +
                "  OPTIONAL {?cpu iz:RAM_maximum_frequency ?RAM_maximum_frequency .}\n" +
                "  OPTIONAL {?cpu iz:socket ?socket .}\n" +
                "  OPTIONAL {?cpu iz:turbo_frequency ?turbo_frequency .}\n" +
                "  OPTIONAL {?cpu iz:voltage ?voltage .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        if (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            cpu.setName((solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : null);
            cpu.setPrice((solution.getLiteral("cost") != null) ? solution.getLiteral("cost").getFloat() : -1);
            cpu.setCan_multiThread((solution.getLiteral("can_multithread") != null) ? solution.getLiteral("can_multithread").getBoolean() : null);
            cpu.setFrequency((solution.getLiteral("base_frequency") != null) ? solution.getLiteral("base_frequency").getFloat() : -1);
            cpu.setCores((solution.getLiteral("number_of_cores") != null) ? solution.getLiteral("number_of_cores").getInt() : -1);
            cpu.setThermalDesignPower((solution.getLiteral("thermal_design_power") != null) ? solution.getLiteral("thermal_design_power").getInt() : -1);
            cpu.setRegisterType((solution.getLiteral("register_type") != null) ? solution.getLiteral("register_type").getString() : null);
            cpu.setL1Cache((solution.getLiteral("L1_cache") != null) ? solution.getLiteral("L1_cache").getString() : null);
            cpu.setL2Cache((solution.getLiteral("L2_cache") != null) ? solution.getLiteral("L2_cache").getString() : null);
            cpu.setL3Cache((solution.getLiteral("L3_cache") != null) ? solution.getLiteral("L3_cache").getString() : null);
            cpu.setMaximumRam((solution.getLiteral("maximumRAM") != null) ? solution.getLiteral("maximumRAM").getInt() : -1);
            cpu.setNumberOfTransistors((solution.getLiteral("number_of_transistors") != null) ? solution.getLiteral("number_of_transistors").getInt() : -1);
            cpu.setProcessMicrons((solution.getLiteral("process_microns") != null) ? solution.getLiteral("process_microns").getInt() : -1);
            cpu.setRamMaximumFrequency((solution.getLiteral("RAM_maximum_frequency") != null) ? solution.getLiteral("RAM_maximum_frequency").getInt() : -1);
            cpu.setSocket((solution.getLiteral("socket") != null) ? solution.getLiteral("socket").getString() : null);
            cpu.setTurboFrequency((solution.getLiteral("turbo_frequency") != null) ? solution.getLiteral("turbo_frequency").getFloat() : -1);
            cpu.setVoltage((solution.getLiteral("voltage") != null) ? solution.getLiteral("voltage").getFloat() : -1);
        }
        return cpu;
    }

    @GetMapping(value = "Compatible/{processorIri}/{coolingSystemIri}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MotherboardDTO> GetCompatibleMotherboards(@PathVariable(value="processorIri") String processorIri, @PathVariable(value="coolingSystemIri") String coolingSystemIri){
        String selectString = SparqlConstants.Prefix +
                "SELECT *\n" +
                "WHERE {\n" +
                "?motherboard rdf:type iz:Motherboard .\n" +
                "?motherboard iz:socket ?socket .\n" +
                "ontology_instances:" + processorIri + " iz:socket ?socket .\n" +
                "ontology_instances:" + coolingSystemIri + " iz:socket ?socket .\n" +
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

}
