package InzenjeringZnanja.dto;

import java.util.ArrayList;
import java.util.List;

public class BayesResultDto {
    List<BayesResponseDto> results;

    public BayesResultDto() {
        results = new ArrayList<BayesResponseDto>();
    }

    public List<BayesResponseDto> getResults() {
        return results;
    }

    public void setResults(List<BayesResponseDto> results) {
        this.results = results;
    }
}
