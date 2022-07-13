package InzenjeringZnanja.service.CaseBasedReasoning;

import InzenjeringZnanja.global.SparqlConstants;
import InzenjeringZnanja.model.*;
import org.apache.jena.query.*;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SparqlConnector implements Connector {
    @Override
    public void initFromXMLfile(URL url) throws InitializingException {

    }

    @Override
    public void close() {

    }

    @Override
    public void storeCases(Collection<CBRCase> collection) {

    }

    @Override
    public void deleteCases(Collection<CBRCase> collection) {

    }

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        List<CBRCase> pcs = new ArrayList<CBRCase>();
        String sparqlQuery = SparqlConstants.Prefix + "SELECT ?pcName ?cpuName ?gpuName ?mbName ?ramName ?diskName ?core ?cpuFrequency ?cpuTdp ?memoryDriveCapacity ?memoryDriveSpeed ?memoryDriveRotation ?memoryDriveConnection ?memoryDriveType ?mbChipset ?mbRamSlots ?mbMaximumRamFreq ?mbMaximumRamSize ?mbRamType ?RamCapacity ?RamFreq ?RamModules ?gpuTdp ?gpuMemSize WHERE {\n" +
                "  ?sub rdf:type iz:Desktop_system .\n" +
                "  OPTIONAL {?sub iz:has_a_name ?pcName.}\n" +
                "  ?sub iz:contains ?cpu.\n" +
                "  ?cpu rdf:type iz:Processor.\n" +
                "  ?cpu iz:number_of_cores ?core.\n" +
                "  ?cpu iz:base_frequency ?cpuFrequency.\n" +
                "  ?cpu iz:thermal_design_power ?cpuTdp.\n" +
                "  OPTIONAL {?cpu iz:has_a_name ?cpuName.}\n" +
                "  ?sub iz:contains ?drive.\n" +
                "  ?drive rdf:type iz:Memory_drive.\n" +
                "  OPTIONAL {?drive iz:disc_rotation_speed ?memoryDriveRotation.}\n" +
                "  OPTIONAL {?drive iz:write_speed ?memoryDriveSpeed.}\n" +
                "  ?drive iz:connection ?memoryDriveConnection.\n" +
                "  ?drive iz:is_type_of ?memoryDriveType.\n" +
                "  ?drive iz:capacity ?memoryDriveCapacity.\n" +
                "  OPTIONAL {?drive iz:has_a_name ?diskName.}\n" +
                "  ?sub iz:contains ?mb.\n" +
                "  OPTIONAL {?mb iz:has_a_name ?mbName.}\n" +
                "  ?mb rdf:type iz:Motherboard.\n" +
                "  ?mb iz:motherboard_chipset ?mbChipset.\n" +
                "  ?mb iz:motherboard_DIMM_slots ?mbRamSlots.\n" +
                "  ?mb iz:RAM_type ?mbRamType.\n" +
                "  ?mb iz:RAM_maximum_frequency ?mbMaximumRamFreq.\n" +
                "  ?mb iz:maximum_RAM ?mbMaximumRamSize.\n" +
                "  ?sub iz:contains ?ram.\n" +
                "  ?ram rdf:type iz:Random_access_memory.\n" +
                "  OPTIONAL {?ram iz:RAM_modules_capacity ?RamCapacity.}\n" +
                "  OPTIONAL {?ram iz:RAM_modules_number ?RamModules.}\n" +
                "  OPTIONAL {?ram iz:RAM_maximum_frequency ?RamFreq.}\n" +
                "  OPTIONAL {?ram iz:has_a_name ?ramName.}\n" +
                "  ?sub iz:contains ?gpu.\n" +
                "  ?gpu rdf:type iz:NVIDIA-GPU.\n" +
                "  ?gpu iz:thermal_design_power ?gpuTdp.\n" +
                "  ?gpu iz:GPU_Memory_size ?gpuMemSize.\n" +
                "  OPTIONAL {?gpu iz:has_a_name ?gpuName.}\n" +
                "}";
        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(SparqlConstants.SELECT_URL, query);
        ResultSet results = qexec.execSelect();
        while(results.hasNext()){
            QuerySolution solution = results.nextSolution();
            CentralProcessingUnit cpu = new CentralProcessingUnit();
            cpu.setName((solution.getLiteral("cpuName") != null) ? solution.getLiteral("cpuName").getString() : "NoName");
            cpu.setManufacturer((solution.getLiteral("cpuManufacturer") != null) ? solution.getLiteral("cpuManufacturer").getString() : "AMD");
            cpu.setFrequency(Float.parseFloat((solution.getLiteral("cpuFrequency") != null) ? solution.getLiteral("cpuFrequency").getString() : "3.8"));
            cpu.setCores(Integer.parseInt((solution.getLiteral("core") != null) ? solution.getLiteral("core").getString() : "8"));
            cpu.setThermalDesignPower(Integer.parseInt((solution.getLiteral("cpuTdp") != null) ? solution.getLiteral("cpuTdp").getString() : "105"));
            MemoryDisk disk = new MemoryDisk();
            disk.setName((solution.getLiteral("diskName") != null) ? solution.getLiteral("diskName").getString() : "NoName");
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
            ram.setName((solution.getLiteral("ramName") != null) ? solution.getLiteral("ramName").getString() : "NoName");
            ram.setCapacity(Integer.parseInt((solution.getLiteral("RamCapacity") != null) ? solution.getLiteral("RamCapacity").getString() : "32"));
            ram.setFrequency(Integer.parseInt((solution.getLiteral("RamFreq") != null) ? solution.getLiteral("RamFreq").getString() : "3200"));
            ram.setNumberOfModules(Integer.parseInt((solution.getLiteral("RamModules") != null) ? solution.getLiteral("RamModules").getString() : "2"));
            Motherboard mb = new Motherboard();
            mb.setName((solution.getLiteral("mbName") != null) ? solution.getLiteral("mbName").getString() : "NoName");
            mb.setChipset((solution.getLiteral("mbChipset") != null) ? solution.getLiteral("mbChipset").getString() : "X570");
            mb.setRamSlots(Integer.parseInt((solution.getLiteral("mbRamSlots") != null) ? solution.getLiteral("mbRamSlots").getString() : "4"));
            mb.setMemoryType((solution.getLiteral("mbRamType") != null) ? solution.getLiteral("mbRamType").getString() : "DDR4");
            mb.setMaximumMemoryFrequency(Integer.parseInt((solution.getLiteral("mbMaximumRamFreq") != null) ? solution.getLiteral("mbMaximumRamFreq").getString() : "5000"));
            mb.setMaximumMemorySize(Integer.parseInt((solution.getLiteral("mbMaximumRamSize") != null) ? solution.getLiteral("mbMaximumRamSize").getString() : "128"));
            GraphicsCard gpu = new GraphicsCard();
            gpu.setName((solution.getLiteral("gpuName") != null) ? solution.getLiteral("gpuName").getString() : "NoName");
            gpu.setTdp(Integer.parseInt((solution.getLiteral("gpuTdp") != null) ? solution.getLiteral("gpuTdp").getString() : "290"));
            gpu.setManufacturer((solution.getLiteral("gpuManufacturer") != null) ? solution.getLiteral("gpuManufacturer").getString() : "NVIDIA");
            gpu.setMemoryCapacity(Integer.parseInt((solution.getLiteral("gpuMemSize") != null) ? solution.getLiteral("gpuMemSize").getString() : "8"));
            PersonalComputer pc = new PersonalComputer();
            pc.setName((solution.getLiteral("pcName") != null) ? solution.getLiteral("pcName").getString() : "PcFromBase");
            pc.setGpu(gpu);
            pc.setDisk(disk);
            pc.setCpu(cpu);
            pc.setMotherboard(mb);
            pc.setRam(ram);
            CBRCase cbrCase = new CBRCase();
            cbrCase.setDescription(pc);
            pcs.add(cbrCase);
        }
        CBRCase cbrCase1 = new CBRCase();
        Motherboard mb1 = new Motherboard();
        mb1.setName("msi x570 torpedo");
        mb1.setChipset("x570");
        mb1.setMaximumMemoryFrequency(5000);
        mb1.setMemoryType("ddr4");
        mb1.setMaximumMemorySize(128);
        mb1.setRamSlots(4);
        CentralProcessingUnit cpu1 = new CentralProcessingUnit();
        cpu1.setName("Ryzen 7 5800X");
        cpu1.setCores(8);
        cpu1.setFrequency(3800);
        cpu1.setManufacturer("AMD");
        cpu1.setThermalDesignPower(105);
        RandomAccessMemory ram1 = new RandomAccessMemory();
        ram1.setName("Kingston 16x2 3200");
        ram1.setCapacity(32);
        ram1.setNumberOfModules(2);
        ram1.setFrequency(3200);
        GraphicsCard gpu1 = new GraphicsCard();
        gpu1.setName("MSI RTX 3070Ti");
        gpu1.setTdp(290);
        gpu1.setMemoryCapacity(8);
        gpu1.setManufacturer("nVidia");
        MemoryDisk disk1 = new MemoryDisk();
        disk1.setName("Nvme 500gb 3500mb/s");
        disk1. setSpeed(3500);
        disk1.setCapacity(500);
        disk1.setType("nvme ssd");
        PersonalComputer pc1 = new PersonalComputer();
        pc1.setName("Ralov komp");
        pc1.setRam(ram1);
        pc1.setCpu(cpu1);
        pc1.setDisk(disk1);
        pc1.setGpu(gpu1);
        pc1.setMotherboard(mb1);
        cbrCase1.setDescription(pc1);
        CBRCase cbrCase2 = new CBRCase();
        Motherboard mb2 = new Motherboard();
        mb2.setName("MSI b350");
        mb2.setChipset("b350");
        mb2.setMaximumMemoryFrequency(3200);
        mb2.setMemoryType("ddr4");
        mb2.setMaximumMemorySize(32);
        mb2.setRamSlots(4);
        CentralProcessingUnit cpu2 = new CentralProcessingUnit();
        cpu2.setName("Ryzen 5 2600");
        cpu2.setCores(6);
        cpu2.setFrequency(3400);
        cpu2.setManufacturer("AMD");
        cpu2.setThermalDesignPower(65);
        RandomAccessMemory ram2 = new RandomAccessMemory();
        ram2.setName("Kingston 16x1 2500");
        ram2.setCapacity(16);
        ram2.setNumberOfModules(1);
        ram2.setFrequency(2500);
        GraphicsCard gpu2 = new GraphicsCard();
        gpu2.setName("RX 570X");
        gpu2.setTdp(150);
        gpu2.setMemoryCapacity(4);
        gpu2.setManufacturer("AMD");
        MemoryDisk disk2 = new MemoryDisk();
        disk2.setName("hdd 1tb 7200rpm");
        disk2. setSpeed(180);
        disk2.setCapacity(1000);
        disk2.setType("hdd");
        PersonalComputer pc2 = new PersonalComputer();
        pc2.setName("Ralov stari komp");
        pc2.setRam(ram2);
        pc2.setCpu(cpu2);
        pc2.setDisk(disk2);
        pc2.setGpu(gpu2);
        pc2.setMotherboard(mb2);
        cbrCase2.setDescription(pc2);
        CBRCase cbrCase3 = new CBRCase();
        Motherboard mb3 = new Motherboard("gigabyte b450", "b450", 2, "ddr4", 64, 4000);
        CentralProcessingUnit cpu3 = new CentralProcessingUnit("Ryzen 7 2800X","AMD", 8, 3600, 105);
        RandomAccessMemory ram3 = new RandomAccessMemory("Some ram 16x2 2800",16, 2, 2800);
        GraphicsCard gpu3 = new GraphicsCard("RTX 3090","nVidia", 24, 350);
        MemoryDisk disk3 = new MemoryDisk("Some ssd 250 500","ssd", 250, 500);
        PersonalComputer pc3 = new PersonalComputer("Some pc", cpu3, mb3, gpu3, ram3, disk3);
        cbrCase3.setDescription(pc3);
        pcs.add(cbrCase3);
        pcs.add(cbrCase2);
        pcs.add(cbrCase1);
        return pcs;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter caseBaseFilter) {
        return null;
    }
}
/*PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX iz: <https://github.com/MilanPodunavac/IZ-Projekat#>
SELECT ?core ?cpuFrequency ?cpuTdp ?gpuMemoryCapacity ?gpuTdp ?memoryDriveCapacity ?memoryDriveSpeed ?memoryDriveRotation ?motherboardChipset ?mbRamSlots ?mbMaximumRamFreq ?mbMaximumRamSize ?mbRamType ?RamCapacity ?RamFreq ?RamModules WHERE {
  ?sub rdf:type iz:Desktop_system .
  ?sub iz:contains ?cpu.
  ?cpu rdf:type iz:Processor.
  ?cpu iz:number_of_cores ?core.
  ?cpu iz:baseFrequency ?cpuFrequency.
  OPTIONAL {?cpu iz:thermal_design_power ?cpuTdp.}
  ?sub iz:contains ?gpu.
  ?gpu rdf:type iz:
}*/

/*PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX iz: <https://github.com/MilanPodunavac/IZ-Projekat#>
SELECT ?core ?cpuFrequency ?cpuTdp ?memoryDriveCapacity ?memoryDriveSpeed ?memoryDriveRotation ?memoryDriveConnection ?memoryDriveType ?mbChipset ?mbRamSlots ?mbMaximumRamFreq ?mbMaximumRamSize ?mbRamType ?RamCapacity ?RamFreq ?RamModules ?gpuTdp ?gpuMemSize WHERE {
  ?sub rdf:type iz:Desktop_system .
  ?sub iz:contains ?cpu.
  ?cpu rdf:type iz:Processor.
  ?cpu iz:number_of_cores ?core.
  ?cpu iz:base_frequency ?cpuFrequency.
  ?cpu iz:thermal_design_power ?cpuTdp.
  ?sub iz:contains ?drive.
  ?drive rdf:type iz:Memory_drive.
  OPTIONAL {?drive iz:disc_rotation_speed ?memoryDriveRotation.}
  OPTIONAL {?drive iz:write_speed ?memoryDriveSpeed.}
  ?drive iz:connection ?memoryDriveConnection.
  ?drive iz:is_type_of ?memoryDriveType.
  ?drive iz:capacity ?memoryDriveCapacity.
  ?sub iz:contains ?mb.
  ?mb rdf:type iz:Motherboard.
  ?mb iz:motherboard_chipset ?mbChipset.
  ?mb iz:motherboard_DIMM_slots ?mbRamSlots.
  ?mb iz:RAM_type ?mbRamType.
  ?mb iz:RAM_maximum_frequency ?mbMaximumRamFreq.
  ?mb iz:maximum_RAM ?mbMaximumRamSize.
  ?sub iz:contains ?ram.
  ?ram rdf:type iz:Random_access_memory.
  OPTIONAL {?ram iz:RAM_modules_capacity ?RamCapacity.}
  OPTIONAL {?ram iz:RAM_modules_number ?RamModules.}
  OPTIONAL {?ram iz:RAM_maximum_frequency ?RamFreq.}
  ?sub iz:contains ?gpu.
  ?gpu rdf:type iz:NVIDIA-GPU.
  ?gpu iz:thermal_design_power ?gpuTdp.
  ?gpu iz:GPU_Memory_size ?gpuMemSize.
}*/