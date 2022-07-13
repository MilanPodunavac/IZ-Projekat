package InzenjeringZnanja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryDiscDto {
    private String name;
    private String type;
    private int capacity;
    private int speed;
    private String connection;
    private String memoryFormat;
    private Float price;
    private String manufacturer;
}
