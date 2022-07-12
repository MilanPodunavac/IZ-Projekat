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
public class GraphicsCard implements CaseComponent{
    private String name;
    private String manufacturer; //AMD, nVidia ili zotac gigabyte i td
    private int memoryCapacity;
    private int tdp;

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id",this.getClass());
    }

    @Override
    public String toString(){
        return "Manufacturer " + manufacturer + "\nMemory capacity " + memoryCapacity + "\nTDP " + tdp;
    }
}
