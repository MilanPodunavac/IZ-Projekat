package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.CpuDto;
import InzenjeringZnanja.dto.GpuCompatibleDto;
import InzenjeringZnanja.dto.GpuDto;
import InzenjeringZnanja.global.SparqlConstants;
import InzenjeringZnanja.service.GpuService;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Gpu")
public class GpuController {

    @Autowired
    private GpuService gpuService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GpuDto getOne(@PathVariable(value="name") String name){
        return gpuService.Get(name);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GpuDto> GetRecommended(@RequestBody GpuCompatibleDto dto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?gpuName ?gpuPrice WHERE{\n" +
                "    ?psu rdf:type iz:Power_supply_unit.\n" +
                "    ?psu iz:has_a_name \"" + dto.getPsuName() + "\".\n" +
                "    ?psu iz:wattage ?psuWattage.\n" +
                "    ?gpu rdf:type iz:Graphics_card.\n" +
                "    ?gpu iz:GPU_recommended_system_power ?gpuRecomm.\n" +
                "  FILTER (?psuWattage * 0.95 >= ?gpuRecomm).\n" +
                "  FILTER (?psuWattage * 0.55 <= ?gpuRecomm).\n" +
                "    ?gpu iz:has_a_name ?gpuName.\n" +
                "    ?gpu iz:costs ?gpuPrice.\n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<GpuDto> retVal = new ArrayList<>();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            GpuDto retValDto = new GpuDto();
            retValDto.setName((solution.getLiteral("gpuName") != null) ? solution.getLiteral("gpuName").getString() : "No name gpu");
            retValDto.setPrice(Float.parseFloat((solution.getLiteral("gpuPrice") != null) ? solution.getLiteral("gpuPrice").getString() : "1000"));
            retVal.add(retValDto);
        }
        return retVal;

    }
}
