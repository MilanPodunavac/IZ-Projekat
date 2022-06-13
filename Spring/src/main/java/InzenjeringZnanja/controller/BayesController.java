package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.BayesDto;
import InzenjeringZnanja.dto.BayesResponseDto;
import InzenjeringZnanja.dto.BayesResultDto;
import InzenjeringZnanja.model.Test;
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

        ProbabilisticNode factNode = (ProbabilisticNode)net.getNode("Blue_screen_of_death");
        int stateIndex = 0; // index of state "green"
        factNode.addFinding(stateIndex);

        if(dto.isComputerDropped()){
            ProbabilisticNode pcDropped = (ProbabilisticNode)net.getNode("Computer_dropped");
            pcDropped.addFinding(0);
        }
        if(dto.isComputerOverclocked()){
            ProbabilisticNode overclocked = (ProbabilisticNode)net.getNode("Overclocking");
            overclocked.addFinding(0);
        }

        try {
            net.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ProbabilisticNode overclocking = (ProbabilisticNode)net.getNode("Overclocking");
        ProbabilisticNode pcDrop = (ProbabilisticNode)net.getNode("Computer_dropped");
        ProbabilisticNode cpuDamaged = (ProbabilisticNode)net.getNode("Processor_damaged");
        ProbabilisticNode gpuDamaged = (ProbabilisticNode)net.getNode("Graphics_card_damaged");

        System.out.println("System has been overclocked: " + ((ProbabilisticNode)overclocking).getMarginalAt(0));
        //System.out.println(overclocking.getStateAt(1) + ": " + ((ProbabilisticNode)overclocking).getMarginalAt(1));
        System.out.println("Computer has been dropped: " + ((ProbabilisticNode)pcDrop).getMarginalAt(0));
        //System.out.println(pcDrop.getStateAt(1) + ": " + ((ProbabilisticNode)pcDrop).getMarginalAt(1));
        System.out.println("Processor has been damaged: " + ((ProbabilisticNode)cpuDamaged).getMarginalAt(0));
        //System.out.println(cpuDamaged.getStateAt(1) + ": " + ((ProbabilisticNode)cpuDamaged).getMarginalAt(1));
       System.out.println("Graphics card has been damaged: " + ((ProbabilisticNode)gpuDamaged).getMarginalAt(0));
        //System.out.println(gpuDamaged.getStateAt(1) + ": " + ((ProbabilisticNode)gpuDamaged).getMarginalAt(1));

        BayesResultDto result = new BayesResultDto();
        BayesResponseDto malf1 = new BayesResponseDto();
        BayesResponseDto malf2 = new BayesResponseDto();
        malf1.setMalfunction(cpuDamaged.getName());
        malf1.setProbability(cpuDamaged.getMarginalAt(0));
        malf2.setMalfunction(gpuDamaged.getName());
        malf2.setProbability(gpuDamaged.getMarginalAt(0));
        result.getResults().add(malf1);
        result.getResults().add(malf2);
        return result;
    }
}
