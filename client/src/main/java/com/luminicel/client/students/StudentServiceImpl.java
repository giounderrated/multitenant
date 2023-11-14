package com.luminicel.client.students;

import com.luminicel.client.users.Role;
import com.luminicel.client.users.User;
import com.luminicel.client.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createStudent(StudentForm form) {
        final User user = User.builder()
                .firstname(form.firstname())
                .lastname(form.lastname())
                .email(form.email())
                .password(form.password())
                .role(Role.STUDENT)
                .build();
        userRepository.saveAndFlush(user);

        final Student student = Student.builder()
                .enrollment_id(form.enrollmentId())
                .userId(user.getId())
                .build();
        studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("No such student with that id"));
    }
}
