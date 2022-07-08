package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.FuzzyRawInputDto;
import InzenjeringZnanja.dto.FuzzyResultDto;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/Fuzzy")
public class FuzzyController {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FuzzyResultDto FuzzyNoOnthology(@RequestBody FuzzyRawInputDto dto){
        FIS fis = FIS.load("data/pc_usage.fcl", true);

        if (fis == null) {
            System.err.println("Can't load file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fcl file not found");
        }

        FunctionBlock block = fis.getFunctionBlock("pc_usage");

        /*try{
            JFuzzyChart.get().chart(block);
        }catch(Exception e){
            e.printStackTrace();
        }*/


        fis.setVariable("price", dto.getPrice());
        fis.setVariable("cpu_cores", dto.getCpuCores());
        fis.setVariable("ram_size", dto.getRamSize());
        fis.setVariable("vram_size", dto.getVramSize());
        fis.setVariable("disk_speed", dto.getDiskSpeed());
        fis.setVariable("disk_size", dto.getDiskSize());

        fis.evaluate();

        Variable homeUsage = block.getVariable("home_usage");
        Variable professionalUsage = block.getVariable("professional_usage");
        Variable serverHosting = block.getVariable("server_hosting");
        Variable gaming = block.getVariable("gaming");
        Variable mining = block.getVariable("mining");

        FuzzyResultDto retVal = new FuzzyResultDto();
        retVal.setHomeUsage((float) homeUsage.getValue());
        retVal.setProfessionalUsage((float) professionalUsage.getValue());
        retVal.setServerHosting((float) serverHosting.getValue());
        retVal.setGaming((float) gaming.getValue());
        retVal.setMining((float) mining.getValue());
        //JFuzzyChart.get().chart(analise, analise.getDefuzzifier(), true);
        //System.out.println(analise.getValue());
        return retVal;
    }
}
