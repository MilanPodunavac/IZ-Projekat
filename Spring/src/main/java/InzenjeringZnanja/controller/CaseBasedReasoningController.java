package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.PersonalComputerDto;
import InzenjeringZnanja.global.SparqlConstants;
import InzenjeringZnanja.model.*;
import InzenjeringZnanja.service.CaseBasedReasoning.CaseBasedReasoningService;
import org.apache.jena.query.*;
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
    public List<PersonalComputer> FindSimilarRaw(@RequestBody PersonalComputer pc){
        List<PersonalComputer> retVal = service.start(pc);
        System.out.println(retVal);
        return retVal;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonalComputer> FindSimilar(@RequestBody PersonalComputerDto pdDto){
        String sparqlQuery = SparqlConstants.Prefix + "SELECT " +
                "                ?core ?cpuFrequency ?cpuTdp ?cpuManufacturer \n" +
                "                ?memoryDriveCapacity ?memoryDriveSpeed ?memoryDriveRotation ?memoryDriveConnection ?memoryDriveType \n" +
                "                ?mbChipset ?mbRamSlots ?mbMaximumRamFreq ?mbMaximumRamSize ?mbRamType \n" +
                "                ?RamCapacity ?RamFreq ?RamModules \n" +
                "                ?gpuTdp ?gpuMemSize ?gpuManufacturer WHERE {\n" +
                "                ?cpu rdf:type iz:Processor.\n" +
                "                ?cpu iz:has_a_name \"" + pdDto.getCpuName() + "\".\n" +
                "                ?cpu iz:number_of_cores ?core.\n" +
                "                ?cpu iz:base_frequency ?cpuFrequency.\n" +
                "                ?cpu iz:thermal_design_power ?cpuTdp.\n" +
                "                ?cpu iz:is_manufactured_by ?cpuManufacturer.\n" +
                "                ?drive rdf:type iz:Memory_drive.\n" +
                "                ?drive iz:has_a_name \"" + pdDto.getDiskName() + "\".\n" +
                "                OPTIONAL {?drive iz:disc_rotation_speed ?memoryDriveRotation.}\n" +
                "                OPTIONAL {?drive iz:write_speed ?memoryDriveSpeed.}\n" +
                "                ?drive iz:connection ?memoryDriveConnection.\n" +
                "                ?drive iz:is_type_of ?memoryDriveType.\n" +
                "                ?drive iz:capacity ?memoryDriveCapacity.\n" +
                "                ?mb iz:has_a_name \"" + pdDto.getMbName() + "\".\n" +
                "                ?mb rdf:type iz:Motherboard.\n" +
                "                ?mb iz:motherboard_chipset ?mbChipset.\n" +
                "                ?mb iz:motherboard_DIMM_slots ?mbRamSlots.\n" +
                "                ?mb iz:RAM_type ?mbRamType.\n" +
                "                ?mb iz:RAM_maximum_frequency ?mbMaximumRamFreq.\n" +
                "                ?mb iz:maximum_RAM ?mbMaximumRamSize.\n" +
                "                ?ram iz:has_a_name \"" + pdDto.getRamName() + "\".\n" +
                "                ?ram rdf:type iz:Random_access_memory.\n" +
                "                ?ram iz:RAM_modules_capacity ?RamCapacity.\n" +
                "                ?ram iz:RAM_modules_number ?RamModules.\n" +
                "                ?ram iz:RAM_maximum_frequency ?RamFreq.\n" +
                "                ?gpu iz:has_a_name \"" + pdDto.getGpuName() + "\".\n" +
                "                ?gpu rdf:type iz:Graphics_card.\n" +
                "                ?gpu iz:thermal_design_power ?gpuTdp.\n" +
                "                ?gpu iz:GPU_Memory_size ?gpuMemSize.\n" +
                "                ?gpu iz:is_manufactured_by ?gpuManufacturer.\n" +
                "                }";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        if(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            CentralProcessingUnit cpu = new CentralProcessingUnit();
            cpu.setName(pdDto.getCpuName());
            cpu.setManufacturer((solution.getLiteral("cpuManufacturer") != null) ? solution.getLiteral("cpuManufacturer").getString() : "AMD");
            cpu.setFrequency(Float.parseFloat((solution.getLiteral("cpuFrequency") != null) ? solution.getLiteral("cpuFrequency").getString() : "3.8"));
            cpu.setCores(Integer.parseInt((solution.getLiteral("core") != null) ? solution.getLiteral("core").getString() : "8"));
            cpu.setThermalDesignPower(Integer.parseInt((solution.getLiteral("cpuTdp") != null) ? solution.getLiteral("cpuTdp").getString() : "105"));
            MemoryDisk disk = new MemoryDisk();
            disk.setName(pdDto.getDiskName());
            disk.setCapacity(Integer.parseInt((solution.getLiteral("memoryDriveCapacity") != null) ? solution.getLiteral("memoryDriveCapacity").getString() : "500"));
            String diskType = (solution.getLiteral("memoryDriveType") != null) ? solution.getLiteral("memoryDriveType").getString() : "SSD";
            disk.setType(diskType);
            if(diskType.equals("SSD")){
                disk.setSpeed(Integer.parseInt((solution.getLiteral("memoryDriveSpeed") != null) ? solution.getLiteral("memoryDriveSpeed").getString() : "500"));
            }else{
                int rotations = Integer.parseInt((solution.getLiteral("memoryDriveRotation") != null) ? solution.getLiteral("memoryDriveRotation").getString() : "7200");
                switch(rotations){
                    case 5400:
                        disk.setSpeed(100);
                        break;
                    case 7200:
                        disk.setSpeed(150);
                        break;
                    case 10000:
                        disk.setSpeed(300);
                        break;
                    default:
                        disk.setSpeed(150);
                        break;
                }
            }
            String diskConnection = (solution.getLiteral("memoryDriveConnection") != null) ? solution.getLiteral("memoryDriveConnection").getString() : "SATA3";
            if(diskConnection.equals("NVMA M.2")){
                disk.setType("NVME");
            }
            RandomAccessMemory ram = new RandomAccessMemory();
            ram.setName(pdDto.getRamName());
            ram.setFrequency(Integer.parseInt((solution.getLiteral("RamFreq") != null) ? solution.getLiteral("RamFreq").getString() : "3200"));
            ram.setNumberOfModules(Integer.parseInt((solution.getLiteral("RamModules") != null) ? solution.getLiteral("RamModules").getString() : "2"));
            ram.setCapacity(Integer.parseInt((solution.getLiteral("RamCapacity") != null) ? solution.getLiteral("RamCapacity").getString() : "16") * ram.getNumberOfModules());
            Motherboard mb = new Motherboard();
            mb.setName(pdDto.getMbName());
            mb.setChipset((solution.getLiteral("mbChipset") != null) ? solution.getLiteral("mbChipset").getString() : "X570");
            mb.setRamSlots(Integer.parseInt((solution.getLiteral("mbRamSlots") != null) ? solution.getLiteral("mbRamSlots").getString() : "4"));
            mb.setMemoryType((solution.getLiteral("mbRamType") != null) ? solution.getLiteral("mbRamType").getString() : "DDR4");
            mb.setMaximumMemoryFrequency(Integer.parseInt((solution.getLiteral("mbMaximumRamFreq") != null) ? solution.getLiteral("mbMaximumRamFreq").getString() : "5000"));
            mb.setMaximumMemorySize(Integer.parseInt((solution.getLiteral("mbMaximumRamSize") != null) ? solution.getLiteral("mbMaximumRamSize").getString() : "128"));
            GraphicsCard gpu = new GraphicsCard();
            gpu.setName(pdDto.getGpuName());
            gpu.setTdp(Integer.parseInt((solution.getLiteral("gpuTdp") != null) ? solution.getLiteral("gpuTdp").getString() : "290"));
            gpu.setManufacturer((solution.getLiteral("gpuManufacturer") != null) ? solution.getLiteral("gpuManufacturer").getString() : "NVIDIA");
            gpu.setMemoryCapacity(Integer.parseInt((solution.getLiteral("gpuMemSize") != null) ? solution.getLiteral("gpuMemSize").getString() : "8"));
            PersonalComputer pc = new PersonalComputer();
            pc.setName("Pc from user");
            pc.setGpu(gpu);
            pc.setDisk(disk);
            pc.setCpu(cpu);
            pc.setMotherboard(mb);
            pc.setRam(ram);
            List<PersonalComputer> retVal = service.start(pc);
            System.out.println(retVal);
            return retVal;
        }
        PersonalComputer pc = new PersonalComputer();
        Motherboard mb = new Motherboard();
        mb.setName("msi x570 torpedo");
        mb.setChipset("x570");
        mb.setMaximumMemoryFrequency(5000);
        mb.setMemoryType("ddr4");
        mb.setMaximumMemorySize(128);
        mb.setRamSlots(4);
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        cpu.setName("Ryzen 7 5800X");
        cpu.setCores(8);
        cpu.setFrequency(3800);
        cpu.setManufacturer("AMD");
        cpu.setThermalDesignPower(105);
        RandomAccessMemory ram = new RandomAccessMemory();
        ram.setName("Kingston 16x2 3200");
        ram.setCapacity(32);
        ram.setNumberOfModules(2);
        ram.setFrequency(3200);
        GraphicsCard gpu = new GraphicsCard();
        gpu.setName("MSI RTX 3070Ti");
        gpu.setTdp(290);
        gpu.setMemoryCapacity(8);
        gpu.setManufacturer("nVidia");
        MemoryDisk disk = new MemoryDisk();
        disk.setName("Nvme 500gb 3500mb/s");
        disk. setSpeed(3500);
        disk.setCapacity(500);
        disk.setType("NVME");
        pc.setName("Ralov komp");
        pc.setRam(ram);
        pc.setCpu(cpu);
        pc.setDisk(disk);
        pc.setGpu(gpu);
        pc.setMotherboard(mb);
        List<PersonalComputer> retVal = service.start(pc);
        System.out.println(retVal);
        return retVal;
    }
}
