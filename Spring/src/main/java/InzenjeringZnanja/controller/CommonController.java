package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.ComponentShortDto;
import InzenjeringZnanja.dto.ComponentTypesDto;
import InzenjeringZnanja.dto.enums.ComponentTypes;
import InzenjeringZnanja.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping(value = "componentTypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentTypesDto GetComponentTypes(){
        ComponentTypesDto componentTypesDto = new ComponentTypesDto();
        componentTypesDto.setComponentTypesList(Stream.of(ComponentTypes.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
        return componentTypesDto;
    }

    @GetMapping(value = "allPsu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentShortDto> GetAllPsu(){
        return commonService.GetAllByString("power_supply_unit");
    }

    @GetMapping(value = "allRam",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentShortDto> GetAllRam(){
        return commonService.GetAllByString("random_access_memory");
    }

    @GetMapping(value = "allCpu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentShortDto> GetAllCPU(){
        return commonService.GetAllByString("processor");
    }

    @GetMapping(value = "allGpu",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComponentShortDto> GetAllGPU(){
        return commonService.GetAllByString("Graphics_card");
    }
}
