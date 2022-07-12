package InzenjeringZnanja.controller;

import InzenjeringZnanja.model.PersonalComputer;
import InzenjeringZnanja.service.CaseBasedReasoning.CaseBasedReasoningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/CaseBasedReasoning")
public class CaseBasedReasoningController {
    @Autowired
    private CaseBasedReasoningService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonalComputer> FindSimilar(@RequestBody PersonalComputer pc){
        List<PersonalComputer> retVal = service.start(pc);
        System.out.println(retVal);
        return retVal;
    }
}
