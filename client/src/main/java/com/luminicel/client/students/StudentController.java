package com.luminicel.client.students;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
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

    @PostMapping(STUDENTS)
    public String createStudent(@RequestBody final StudentForm form ){
        final Student student = Student.builder()
                .firstname(form.firstname())
                .lastname(form.lastname())
                .build();
        repository.save(student);
    return String.format("Student %s created",student.getFirstname());
    }
}
