package InzenjeringZnanja.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuzzyResultDto {
    private float homeUsage;
    private float professionalUsage;
    private float serverHosting;
    private float gaming;
    private float mining;
}
