package InzenjeringZnanja.controller;

import InzenjeringZnanja.dto.RamDto;
import InzenjeringZnanja.service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/ram")
public class RamController {

    @Autowired
    private RamService ramService;

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RamDto getOne(@PathVariable(value="name") String name){
        return ramService.Get(name);
    }
}
