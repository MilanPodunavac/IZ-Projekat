package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.RamDto;
import InzenjeringZnanja.service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import InzenjeringZnanja.dto.RamCompatibleDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Ram")
public class RamController {
    @Autowired
    private RamService ramService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RamDto getOne(@PathVariable(value="name") String name){
        return ramService.Get(name);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RamDto> GetRecommended(@RequestBody RamCompatibleDto dto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?ramName ?ramPrice WHERE{\n" +
                "\t?mb rdf:type iz:Motherboard.\n" +
                "    ?mb iz:has_a_name \"" + dto.getMbName() + "\".\n" +
                "\t?mb iz:RAM_type ?mbRamType.\n" +
                "    ?mb iz:motherboard_DIMM_slots ?mbRamSlots.\n" +
                "    ?ram rdf:type iz:Random_access_memory.\n" +
                "    ?ram iz:RAM_type ?mbRamType.\n" +
                "    ?ram iz:RAM_modules_number ?ramModules.\n" +
                "    FILTER(?ramModules <= ?mbRamSlots).\n" +
                "    ?ram iz:has_a_name ?ramName.\n" +
                "    ?ram iz:costs ?ramPrice.\n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<RamDto> retVal = new ArrayList<>();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            RamDto retValDto = new RamDto();
            retValDto.setName((solution.getLiteral("ramName") != null) ? solution.getLiteral("ramName").getString() : "No name ram");
            retValDto.setPrice(Float.parseFloat((solution.getLiteral("ramPrice") != null) ? solution.getLiteral("ramPrice").getString() : "200"));
            retVal.add(retValDto);
        }
        return retVal;
    }
}
