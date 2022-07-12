package InzenjeringZnanja.service;

import InzenjeringZnanja.dto.ComponentShortDto;
import InzenjeringZnanja.global.SparqlConstants;
import org.apache.jena.query.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentShortDto> GetAllByString(String componentType){
        System.out.println(componentType);
        System.out.println(capitalize(componentType));
        String selectString = SparqlConstants.Prefix +
                "SELECT  ?iri ?name \n" +
                "WHERE {\n" +
                "  ?"+ componentType +" rdf:type iz:" + capitalize(componentType) +" .\n" +
                "  OPTIONAL {?" + componentType + " iz:has_a_name ?name .}\n" +
                "  OPTIONAL {?" + componentType + " iz:costs ?cost .}\n" +
                "}";

        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        List<ComponentShortDto> dtos = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            ComponentShortDto dto = new ComponentShortDto();
            dto.name = (solution.getLiteral("name") != null) ? solution.getLiteral("name").getString() : "";
            dto.iri = (solution.getLiteral("iri") != null) ? solution.getLiteral("iri").getString() : "";
            dtos.add(dto);
        }
        return dtos;
    }

    public static final String capitalize(String str) {

        if (str == null || str.length() == 0) return str;

        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }

}
