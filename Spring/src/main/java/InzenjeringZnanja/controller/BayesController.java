package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.BayesDto;
import InzenjeringZnanja.dto.BayesResponseDto;
import InzenjeringZnanja.dto.BayesResultDto;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        for (ComputerSymptoms symptom : dto.getComputerSymptomsList()) ((ProbabilisticNode)net.getNode(ComputerSymptoms.getNodeName(symptom))).addFinding(0);
        for (MalfunctionCauses cause : dto.getMalfunctionCausesList()) ((ProbabilisticNode)net.getNode(MalfunctionCauses.getNodeName(cause))).addFinding(0);
        for (MalfunctionCauses cause : MalfunctionCauses.values()){
            boolean found = false;
            for(MalfunctionCauses inDto : dto.getMalfunctionCausesList()){
                if (cause == inDto){
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
}
