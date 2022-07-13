package InzenjeringZnanja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GpuDto {
    private String name;
    private String manufacturer; //AMD, nVidia ili zotac gigabyte i td
    private int memoryCapacity;
    private int tdp;
    private int sixPinConnectors;
    private int eightPinConnectors;
    private int coreBoostClock;
    private int coreClock;
    private String directXVersion;
    private int dpConnectors;
    private int hdmiConnectors;
    private int memoryBus;
    private int memoryClock;
    private int memoryType;
    private int recommendedSystemPower;
    private int vgaConnectors;
    private float width;
    private float length;
    private float price;
}
