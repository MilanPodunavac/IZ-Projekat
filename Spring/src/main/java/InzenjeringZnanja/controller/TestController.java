package InzenjeringZnanja.controller;

import InzenjeringZnanja.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Test TestGet(){
        return new Test("Ralo", 1.685);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Test TestPost(@RequestBody Test test){
        return new Test(test.getIme(), test.getVisina());
    }
}

