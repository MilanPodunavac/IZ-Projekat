package InzenjeringZnanja.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BayesDto {
    private List<String> computerSymptomsList;
    private List<String> malfunctionCausesList;
}
