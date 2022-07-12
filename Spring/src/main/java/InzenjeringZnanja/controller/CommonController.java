package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.ComponentTypesDto;
import InzenjeringZnanja.dto.enums.ComponentTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @GetMapping(value = "componentTypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentTypesDto GetComponentTypes(){
        ComponentTypesDto componentTypesDto = new ComponentTypesDto();
        componentTypesDto.setComponentTypesList(Stream.of(ComponentTypes.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
        return componentTypesDto;
    }

}
