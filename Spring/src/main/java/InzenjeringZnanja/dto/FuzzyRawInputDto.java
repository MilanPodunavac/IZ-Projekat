package InzenjeringZnanja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuzzyRawInputDto {
    private int price;
    private int cpuCores;
    private int ramSize;
    private int vramSize;
    private int diskSpeed;
    private int diskSize;
}
