package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.PsuCompatibleDto;
import InzenjeringZnanja.dto.PsuDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Psu")
public class PsuController {



    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PsuDto> GetRecommended(@RequestBody PsuCompatibleDto dto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?psuName ?psuPower WHERE{\n" +
                "?gpu rdf:type iz:Graphics_card.\n" +
                "    ?gpu iz:has_a_name \"" + dto.getGpuName() + "\".\n" +
                "\t?gpu iz:GPU_recommended_system_power ?gpuRecomm.\n" +
                "    ?psu rdf:type iz:Power_supply_unit.\n" +
                "    ?psu iz:wattage ?psuPower.\n" +
                "    FILTER(?gpuRecomm <= ?psuPower).\n" +
                "    FILTER(?gpuRecomm + 100 >= ?psuPower).\n" +
                "    ?psu iz:has_a_name ?psuName.   \n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<PsuDto> retVal = new ArrayList<>();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            PsuDto retValDto = new PsuDto();
            retValDto.setName((solution.getLiteral("psuName") != null) ? solution.getLiteral("psuName").getString() : "No name psu");
            retValDto.setWattage(Integer.parseInt((solution.getLiteral("psuPower") != null) ? solution.getLiteral("psuPower").getString() : "750"));
            retVal.add(retValDto);
        }
        return retVal;
    }
}
