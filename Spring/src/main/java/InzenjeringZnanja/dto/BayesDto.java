package InzenjeringZnanja.dto;

import InzenjeringZnanja.dto.enums.ComputerSymptoms;
import InzenjeringZnanja.dto.enums.MalfunctionCauses;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BayesDto {
    private List<ComputerSymptoms> computerSymptomsList;
    private List<MalfunctionCauses> malfunctionCausesList;
}
