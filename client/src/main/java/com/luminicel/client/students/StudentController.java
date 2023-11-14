package com.luminicel.client.students;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private static final String STUDENTS = "/students";

    private final StudentService service;

    public StudentController(final StudentService service) {
        this.service = service;
    }

    @GetMapping(STUDENTS)
    public List<Student> getAll(){
        return service.getAllStudents();
    }

    @PostMapping(STUDENTS)
    public ResponseEntity<Void> createStudent(@RequestBody final StudentForm form ){
        service.createStudent(form);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
