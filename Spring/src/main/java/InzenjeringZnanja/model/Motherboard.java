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
public class Motherboard implements CaseComponent {
    private String name;
    private String chipset; //X570, X570S..
    private int ramSlots;
    private String memoryType;
    private int maximumMemorySize;
    private int maximumMemoryFrequency;

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id",this.getClass());
    }

    @Override
    public String toString(){
        return "Chipset " + chipset + "\nRam slots " + ramSlots + "\nMemory type " + memoryType +
                "\nMaximum ram size " + maximumMemorySize + "\nMaximum ram frequency" + maximumMemoryFrequency;
    }
}
