package InzenjeringZnanja.controller;

import InzenjeringZnanja.model.CentralProcessingUnit;
import InzenjeringZnanja.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/Cpu")
public class CpuController {

    @Autowired
    private CpuService cpuService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CentralProcessingUnit getOne(@PathVariable(value="name") String name){
        return cpuService.Get(name);
    }

}
