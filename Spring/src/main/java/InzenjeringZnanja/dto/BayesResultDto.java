package InzenjeringZnanja.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BayesResultDto {
    List<BayesResponseDto> results;
    public BayesResultDto() {
        results = new ArrayList<BayesResponseDto>();
    }
}
