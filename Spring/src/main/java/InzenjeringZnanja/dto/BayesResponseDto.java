package InzenjeringZnanja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BayesResponseDto {
    private String malfunction;
    private float probability;
    public BayesResponseDto(String malfunction, float probability){
        this.malfunction = malfunction;
        this.probability = probability;
    }
}
