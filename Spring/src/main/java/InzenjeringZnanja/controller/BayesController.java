package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.*;
import InzenjeringZnanja.dto.enums.ComputerMalfunctions;
import InzenjeringZnanja.dto.enums.ComputerSymptoms;
import InzenjeringZnanja.dto.enums.MalfunctionCauses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/api/Bayes")
public class BayesController {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BayesResultDto Bayes(@RequestBody BayesDto dto){
        ProbabilisticNetwork net;
        BaseIO io = new NetIO();
        try {
            net = (ProbabilisticNetwork)io.load(new File("data/kvarovi.net"));
        } catch (IOException e) {
            throw new RuntimeException("No such file");
        }
        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();

        algorithm.setNetwork(net);
        algorithm.run();

        for (String symptom : dto.getComputerSymptomsList()) ((ProbabilisticNode)net.getNode(ComputerSymptoms.getNodeName(ComputerSymptoms.valueOf(symptom)))).addFinding(0);
        for (String cause : dto.getMalfunctionCausesList()) ((ProbabilisticNode)net.getNode(MalfunctionCauses.getNodeName(MalfunctionCauses.valueOf(cause)))).addFinding(0);
        for (MalfunctionCauses cause : MalfunctionCauses.values()){
            boolean found = false;
            for(String inDto : dto.getMalfunctionCausesList()){
                if (cause == MalfunctionCauses.valueOf(inDto)){
                    found = true;
                    break;
                }
            }
            if(found) continue;
            ((ProbabilisticNode)net.getNode(MalfunctionCauses.getNodeName(cause))).addFinding(1);
        }

        try {
            net.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ComputerMalfunctions[] allMalfunctions = ComputerMalfunctions.values();
        BayesResultDto malfunctions = new BayesResultDto();
        for(ComputerMalfunctions malfunction : allMalfunctions){
            ProbabilisticNode node = (ProbabilisticNode) net.getNode(ComputerMalfunctions.getNodeName(malfunction));
            if (node.getMarginalAt(0) > 0)
                malfunctions.getResults().
                        add(new BayesResponseDto(ComputerMalfunctions.toString(malfunction), node.getMarginalAt(0)));
        }
        malfunctions.getResults().sort(Comparator.comparing(BayesResponseDto::getProbability).reversed());
        return malfunctions;
    }

    @GetMapping(value = "computerSymptoms",produces = MediaType.APPLICATION_JSON_VALUE)
    public ComputerSymptomsDto GetComputerSymptoms(){
        ComputerSymptomsDto computerSymptomsDto = new ComputerSymptomsDto();
        computerSymptomsDto.setComputerSymptomsList(Stream.of(ComputerSymptoms.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
        return computerSymptomsDto;
    }

    @GetMapping(value = "malfunctionCauses",produces = MediaType.APPLICATION_JSON_VALUE)
    public MalfunctionCausesDto GetMalfunctionCauses(){
        MalfunctionCausesDto malfunctionCausesDto = new MalfunctionCausesDto();
        malfunctionCausesDto.setMalfunctionCausesList(Stream.of(MalfunctionCauses.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
        return malfunctionCausesDto;
    }
}
