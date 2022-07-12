package InzenjeringZnanja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AllComponentsByTypeDto {

    private List<ComponentShortDto> componentShortDtoList;

}
