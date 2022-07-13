package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.ComponentNameDto;
import InzenjeringZnanja.dto.MemoryDiscDto;
import InzenjeringZnanja.service.MemoryDickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/MemoryDisc")
public class MemoryDiscController {

    @Autowired
    private MemoryDickService memoryDickService;

    @PostMapping(value = "getByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemoryDiscDto getOne(@RequestBody ComponentNameDto componentNameDto){
        return memoryDickService.Get(componentNameDto.getName());
    }
}
