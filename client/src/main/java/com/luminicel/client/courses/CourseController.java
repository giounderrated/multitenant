package com.luminicel.client.courses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    private static final String COURSES = "/courses";
    private final CourseService service;

    public CourseController(final CourseService service) {
        this.service = service;
    }

    @GetMapping(COURSES)
    public List<Course> getAll(){
        return service.findAll();
    }

    @PostMapping(COURSES)
    public ResponseEntity<Void> create(@RequestBody final CourseForm form){
        service.createCourse(form);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
