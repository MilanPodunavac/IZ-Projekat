package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.RamDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

@Service
public class RamService {

    public RamDto Get(String name){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?RAM_cas_latency ?RAM_first_word_latency ?RAM_form_factor ?RAM_maximum_frequency ?RAM_modules_number ?RAM_modules_capacity ?RAM_type ?voltage ?is_manufactured_by \n" +
                "WHERE {\n" +
                "?ram rdf:type iz:Random_access_memory .\n" +
                "?ram iz:has_a_name \"" + name + "\".\n" +
                "  ?cpu rdf:type iz:Random_access_memory .\n" +
                "  OPTIONAL {?ram iz:has_a_name ?name ;}\n" +
                "  OPTIONAL {?ram iz:costs ?cost .}\n" +
                "  OPTIONAL {?ram iz:RAM_cas_latency ?RAM_cas_latency .}\n" +
                "  OPTIONAL {?ram iz:RAM_first_word_latency ?RAM_first_word_latency .}\n" +
                "  OPTIONAL {?ram iz:RAM_form_factor ?RAM_form_factor .}\n" +
                "  OPTIONAL {?ram iz:RAM_maximum_frequency ?RAM_maximum_frequency .}\n" +
                "  OPTIONAL {?ram iz:RAM_modules_number ?RAM_modules_number .}\n" +
                "  OPTIONAL {?ram iz:RAM_modules_capacity ?RAM_modules_capacity .}\n" +
                "  OPTIONAL {?ram iz:RAM_type ?RAM_type .}\n" +
                "  OPTIONAL {?ram iz:voltage ?voltage .}\n" +
                "  OPTIONAL {?ram iz:is_manufactured_by ?is_manufactured_by .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        RamDto ram = new RamDto();
        if (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            ram.setName((solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : null);
            ram.setPrice((solution.getLiteral("cost") != null) ? solution.getLiteral("cost").getFloat() : -1);
            ram.setCasLatency((solution.getLiteral("RAM_cas_latency") != null) ? solution.getLiteral("RAM_cas_latency").getInt() : -1);
            ram.setFrequency((solution.getLiteral("RAM_first_word_latency") != null) ? solution.getLiteral("RAM_first_word_latency").getInt() : -1);
            ram.setFormFactor((solution.getLiteral("RAM_form_factor") != null) ? solution.getLiteral("RAM_form_factor").getString() : null);
            ram.setFrequency((solution.getLiteral("RAM_maximum_frequency") != null) ? solution.getLiteral("RAM_maximum_frequency").getInt() : -1);
            ram.setNumberOfModules((solution.getLiteral("RAM_modules_number") != null) ? solution.getLiteral("RAM_modules_number").getInt() : -1);
            ram.setCapacity((solution.getLiteral("RAM_modules_capacity") != null) ? solution.getLiteral("RAM_modules_capacity").getInt() : -1);
            ram.setType((solution.getLiteral("RAM_type") != null) ? solution.getLiteral("RAM_type").getString() : null);
            ram.setManufacturer((solution.getLiteral("is_manufactured_by") != null) ? solution.getLiteral("is_manufactured_by").getString() : null);
            ram.setVoltage((solution.getLiteral("voltage") != null) ? solution.getLiteral("voltage").getFloat() : -1);
        }
        return ram;
    }

}
