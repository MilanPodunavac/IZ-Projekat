package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.CpuCompatibleDto;
import InzenjeringZnanja.dto.CpuDto;
import InzenjeringZnanja.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Cpu")
public class CpuController {

    @Autowired
    private CpuService cpuService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CpuDto getOne(@PathVariable(value="name") String name){
        return cpuService.Get(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CpuDto> GetCompatible(CpuCompatibleDto dto){
        return null;
    }

}
