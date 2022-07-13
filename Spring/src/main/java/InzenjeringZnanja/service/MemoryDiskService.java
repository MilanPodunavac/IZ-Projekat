package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.MemoryDiscDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

@Service
public class MemoryDiskService {
    public MemoryDiscDto Get(String name){
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?name ?cost ?is_type_of ?capacity ?memory_format ?disc_rotation_speed ?connection ?is_manufactured_by \n" +
                "WHERE {\n" +
                "?md rdf:type iz:Memory_drive .\n" +
                "?md iz:has_a_name \"" + name + "\".\n" +
                "  ?md rdf:type iz:Memory_drive .\n" +
                "  OPTIONAL {?md iz:has_a_name ?name ;}\n" +
                "  OPTIONAL {?md iz:costs ?cost .}\n" +
                "  OPTIONAL {?md iz:is_type_of ?is_type_of .}\n" +
                "  OPTIONAL {?md iz:capacity ?capacity .}\n" +
                "  OPTIONAL {?md iz:memory_format ?memory_format .}\n" +
                "  OPTIONAL {?md iz:disc_rotation_speed ?disc_rotation_speed .}\n" +
                "  OPTIONAL {?md iz:connection ?connection .}\n" +
                "  OPTIONAL {?md iz:is_manufactured_by ?is_manufactured_by .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        MemoryDiscDto memoryDiscDto = new MemoryDiscDto();
        if (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            memoryDiscDto.setName((solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : null);
            memoryDiscDto.setPrice((solution.getLiteral("cost") != null) ? solution.getLiteral("cost").getFloat() : -1);
            memoryDiscDto.setType((solution.getLiteral("is_type_of") != null) ? solution.getLiteral("is_type_of").getString() : null);
            memoryDiscDto.setCapacity((solution.getLiteral("capacity") != null) ? solution.getLiteral("capacity").getInt() : -1);
            memoryDiscDto.setMemoryFormat((solution.getLiteral("memory_format") != null) ? solution.getLiteral("memory_format").getString() : null);
            memoryDiscDto.setSpeed((solution.getLiteral("disc_rotation_speed") != null) ? solution.getLiteral("disc_rotation_speed").getInt() : -1);
            memoryDiscDto.setConnection((solution.getLiteral("connection") != null) ? solution.getLiteral("connection").getString() : null);
            memoryDiscDto.setManufacturer((solution.getLiteral("is_manufactured_by") != null) ? solution.getLiteral("is_manufactured_by").getString() : null);
        }
        return memoryDiscDto;
    }
}
