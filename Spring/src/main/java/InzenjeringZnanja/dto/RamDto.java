package InzenjeringZnanja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RamDto {
    private String name;
    private int capacity;
    private int numberOfModules;
    private int frequency;
    private int casLatency;
    private float firstWordLatency;
    private String formFactor;
    private String type;
    private float voltage;
    private float price;
    private String manufacturer;
}
