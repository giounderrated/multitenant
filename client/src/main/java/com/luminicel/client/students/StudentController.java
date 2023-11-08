package com.luminicel.client.students;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class StudentController {

    private static final String STUDENTS = "/students";

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping(STUDENTS)
    public List<Student> getAll(){
        return repository.findAll();
    }
}
