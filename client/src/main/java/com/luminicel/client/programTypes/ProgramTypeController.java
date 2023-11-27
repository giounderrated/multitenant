package com.luminicel.client.programTypes;

import com.luminicel.client.rest.JSend;
import com.luminicel.client.rest.Success;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProgramTypeController {

    private final ProgramTypeService service;

    private static final String PROGRAM_TYPES = "/program-types";

    public ProgramTypeController(ProgramTypeService service) {
        this.service = service;
    }

    @GetMapping(PROGRAM_TYPES)
    public JSend<List<ProgramType>> getAll(){
        final List<ProgramType> categories = service.findAll();
        return Success.<List<ProgramType>>builder()
                .data(categories)
                .build();
    }

    @PostMapping(PROGRAM_TYPES)
    public ResponseEntity<Void> createProgramCategory(@RequestBody final ProgramTypeForm form){
        final ProgramType category = ProgramType.builder()
                .name(form.name())
                .build();
        service.create(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
