package InzenjeringZnanja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CpuDto {

    private String name;          // has_a_name
    private String manufacturer; //AMD, Intel
    private float frequency;
    private int cores;
    private int thermalDesignPower;
    private boolean can_multiThread;
    private String l1Cache;
    private String l2Cache;
    private String l3Cache;
    private int maximumRam;
    private int numberOfTransistors;
    private int processMicrons;
    private int ramMaximumFrequency;
    private String registerType;
    private String socket;
    private float turboFrequency;
    private float voltage;
    private float price;

}
