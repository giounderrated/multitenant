package com.luminicel.client.students;

import java.util.List;

public interface StudentService {
    void createStudent(StudentForm form);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
}
