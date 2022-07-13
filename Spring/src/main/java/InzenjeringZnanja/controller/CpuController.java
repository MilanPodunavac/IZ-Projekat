package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.CpuCompatibleDto;
import InzenjeringZnanja.dto.CpuDto;
import InzenjeringZnanja.dto.RamDto;
import InzenjeringZnanja.global.SparqlConstants;
import InzenjeringZnanja.service.CpuService;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Cpu")
public class CpuController {

    @Autowired
    private CpuService cpuService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CpuDto getOne(@PathVariable(value="name") String name){
        return cpuService.Get(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CpuDto> GetCompatible(@RequestBody CpuCompatibleDto dto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?cpuName ?cpuPrice WHERE{\n" +
                "    ?mb rdf:type iz:Motherboard.\n" +
                "    ?mb iz:has_a_name \"" + dto.getMbName() + "\".\n" +
                "    ?mb iz:socket ?mbSocket.\n" +
                "    ?cpu rdf:type iz:Processor.\n" +
                "    ?cpu iz:socket ?mbSocket.\n" +
                "    ?cpu iz:has_a_name ?cpuName.\n" +
                "    ?cpu iz:costs ?cpuPrice.\n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<CpuDto> retVal = new ArrayList<>();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            CpuDto retValDto = new CpuDto();
            retValDto.setName((solution.getLiteral("cpuName") != null) ? solution.getLiteral("cpuName").getString() : "No name cpu");
            retValDto.setPrice(Float.parseFloat((solution.getLiteral("cpuPrice") != null) ? solution.getLiteral("cpuPrice").getString() : "350"));
            retVal.add(retValDto);
        }
        return retVal;
    }

}
