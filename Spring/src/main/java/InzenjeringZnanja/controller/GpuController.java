package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.GpuDto;
import InzenjeringZnanja.service.GpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/Gpu")
public class GpuController {

    @Autowired
    private GpuService gpuService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GpuDto getOne(@PathVariable(value="name") String name){
        return gpuService.Get(name);
    }
}
