package InzenjeringZnanja.controller;

import InzenjeringZnanja.model.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/Ontology")
public class OntologyController {
    private static final String SELECT_URL = "http://localhost:3030/primer/sparql";

    private static final String UPDATE_URL = "http://localhost:3030/primer/update";
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Test TestGet(){
        Model model = ModelFactory.createDefaultModel();
        try {
            InputStream is = new FileInputStream("data/primer.ttl");
            RDFDataMgr.read(model, is, Lang.TURTLE);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SPARQL upit INSERT
        String insertString = "";
        try {
            insertString = new String(Files.readAllBytes(Paths.get("data/insert.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateRequest updateRequest = UpdateFactory.create(insertString);
        UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, UPDATE_URL);
        updateProcessor.execute();

        // SPARQL upit DELETE

        String deleteString = "";
        try {
            deleteString = new String(Files.readAllBytes(Paths.get("data/delete.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateRequest updateRequest2 = UpdateFactory.create(deleteString);
        UpdateProcessor updateProcessor2 = UpdateExecutionFactory.createRemote(updateRequest2, UPDATE_URL);
        updateProcessor2.execute();

        // SPARQL upit SELECT
        String selectString = "";
        try {
            selectString = new String(Files.readAllBytes(Paths.get("data/select.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Query query = QueryFactory.create(selectString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            Resource resource = solution.getResource("glumac");
            Literal literal = solution.getLiteral("ime");
            System.out.println(resource.getURI());
            System.out.println(literal.getString());
        }
        return new Test("Ralo", 1.685);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Test TestPost(@RequestBody Test test){
        return new Test(test.getIme(), test.getVisina());
    }
}

