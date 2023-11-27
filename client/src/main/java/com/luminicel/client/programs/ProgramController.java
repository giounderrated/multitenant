package com.luminicel.client.programs;

import com.luminicel.client.rest.JSend;
import com.luminicel.client.rest.Success;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProgramController {

    private static final String PROGRAMS = "/programs";
    private final ProgramService service;

    public ProgramController(ProgramService service) {
        this.service = service;
    }

    @GetMapping(PROGRAMS)
    public JSend<List<Program>> getAll(){
        final List<Program> programs= service.findAll();
        return Success.<List<Program>>builder()
                .data(programs)
                .build();
    }

    @PostMapping(PROGRAMS)
    public ResponseEntity<Void> create(@RequestBody final ProgramForm form){
        service.createProgram(form);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
