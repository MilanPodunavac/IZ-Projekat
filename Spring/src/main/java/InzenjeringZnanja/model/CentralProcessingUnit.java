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
public class CentralProcessingUnit implements CaseComponent {
    private String name;          // has_a_name
    private String manufacturer; //AMD, Intel
    private float frequency;
    private int cores;
    private int thermalDesignPower;

    @JsonIgnore
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id",this.getClass());
    }

    @Override
    public String toString(){
        return "Manufacturer " + manufacturer + "\nfrequency " + frequency + "\nnumber of cores " + cores + "\nTDP " + thermalDesignPower;
    }
}
