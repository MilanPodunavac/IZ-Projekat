package InzenjeringZnanja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalComputer implements CaseComponent {
    private String name;
    private CentralProcessingUnit cpu;
    private Motherboard motherboard;
    private GraphicsCard gpu;
    private RandomAccessMemory ram;
    private MemoryDisk disk;

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id",this.getClass());
    }

    @Override
    public String toString(){
        return "CPU\n " + cpu + "\nMotherboard\n" + motherboard + "\nGraphics card\n" + gpu +
                "\nRam\n" + ram + "\nMemory disk\n" + disk;
    }
}
