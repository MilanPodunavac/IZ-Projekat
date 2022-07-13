package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.FuzzyInputDto;
import InzenjeringZnanja.dto.FuzzyRawInputDto;
import InzenjeringZnanja.dto.FuzzyResultDto;
import InzenjeringZnanja.global.SparqlConstants;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.apache.jena.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/Fuzzy")
public class FuzzyController {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FuzzyResultDto FuzzyNoOnthology(@RequestBody FuzzyRawInputDto dto){
        FuzzyResultDto retVal = DoFuzzy(dto);
        //JFuzzyChart.get().chart(analise, analise.getDefuzzifier(), true);
        //System.out.println(analise.getValue());
        return retVal;
    }

    private FuzzyResultDto DoFuzzy(FuzzyRawInputDto dto) {
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
        return retVal;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FuzzyResultDto Fuzzy(@RequestBody FuzzyInputDto dto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?cpuPrice ?ramPrice ?diskPrice ?gpuPrice ?core ?gpuMem " +
                "?ramMem ?ramMod ?diskSpeed ?diskRotation ?diskCapacity WHERE {\n" +
                "  ?gpu rdf:type iz:Graphics_card.\n" +
                "  ?gpu iz:has_a_name \"" + dto.getGpuName() + "\".\n" +
                "  ?gpu iz:costs ?gpuPrice.\n" +
                "  ?gpu iz:GPU_Memory_size ?gpuMem.\n" +
                "  ?cpu rdf:type iz:Processor.\n" +
                "  ?cpu iz:has_a_name \"" + dto.getCpuName() +"\".\n" +
                "  ?cpu iz:costs ?cpuPrice.\n" +
                "  ?cpu iz:number_of_cores ?core.\n" +
                "  ?ram rdf:type iz:Random_access_memory.\n" +
                "  ?ram iz:has_a_name \"" + dto.getRamName() +"\".\n" +
                "  ?ram iz:RAM_modules_number ?ramMod.\n" +
                "  ?ram iz:RAM_modules_capacity ?ramMem.\n" +
                "  ?ram iz:costs ?ramPrice.\n" +
                "  ?disk rdf:type iz:Memory_drive.\n" +
                "  ?disk iz:has_a_name \"" + dto.getDiskName() +"\".\n" +
                "  OPTIONAL {?disk iz:write_speed ?diskSpeed.}\n" +
                "  OPTIONAL {?disk iz:disc_rotation_speed ?diskRotation.}\n" +
                "  ?disk iz:capacity ?diskCapacity.\n" +
                "  ?disk iz:costs ?diskPrice.\n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        FuzzyRawInputDto rawDto = new FuzzyRawInputDto();
        if(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            rawDto.setCpuCores(Integer.parseInt((solution.getLiteral("core") != null) ? solution.getLiteral("core").getString() : "8"));
            rawDto.setVramSize(Integer.parseInt((solution.getLiteral("gpuMem") != null) ? solution.getLiteral("gpuMem").getString() : "8"));
            int modules = Integer.parseInt((solution.getLiteral("ramMod") != null) ? solution.getLiteral("ramMod").getString() : "2");
            int modSize = Integer.parseInt((solution.getLiteral("ramMem") != null) ? solution.getLiteral("ramMem").getString() : "16");
            rawDto.setRamSize(modSize * modules);
            String rotationStr = (solution.getLiteral("diskRotation") != null) ? solution.getLiteral("diskRotation").getString() : "NoRot";
            String diskSpeedStr = (solution.getLiteral("diskSpeed") != null) ? solution.getLiteral("diskSpeed").getString() : "500";
            if(!rotationStr.equals("NoRot")){
                switch(Integer.parseInt(rotationStr)){
                    case 5400:
                        rawDto.setDiskSpeed(100);
                        break;
                    case 7200:
                        rawDto.setDiskSpeed(150);
                        break;
                    case 10000:
                        rawDto.setDiskSpeed(300);
                        break;
                    default:
                        rawDto.setDiskSpeed(150);
                        break;
                }
            }
            else{
                rawDto.setDiskSpeed(Integer.parseInt(diskSpeedStr));
            }
            rawDto.setDiskSpeed(Integer.parseInt((solution.getLiteral("diskCapacity") != null) ? solution.getLiteral("diskCapacity").getString() : "500"));
            float cpuPrice = Float.parseFloat((solution.getLiteral("cpuPrice") != null) ? solution.getLiteral("cpuPrice").getString() : "300");
            float diskPrice = Float.parseFloat((solution.getLiteral("diskPrice") != null) ? solution.getLiteral("diskPrice").getString() : "50");
            float ramPrice = Float.parseFloat((solution.getLiteral("ramPrice") != null) ? solution.getLiteral("ramPrice").getString() : "140");
            float gpuPrice = Float.parseFloat((solution.getLiteral("gpuPrice") != null) ? solution.getLiteral("gpuPrice").getString() : "1100");
            rawDto.setPrice(cpuPrice + diskPrice + ramPrice + gpuPrice + 200);
        }
        else{
            rawDto.setCpuCores(8);
            rawDto.setDiskSize(1000);
            rawDto.setDiskSpeed(300);
            rawDto.setPrice(600);
            rawDto.setRamSize(12);
            rawDto.setVramSize(4);
        }
        FuzzyResultDto resultDto = DoFuzzy(rawDto);
        return resultDto;
    }
}
