package InzenjeringZnanja.service.CaseBasedReasoning;

import InzenjeringZnanja.model.*;
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
