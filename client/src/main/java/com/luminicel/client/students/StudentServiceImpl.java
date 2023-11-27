package com.luminicel.client.students;

import com.luminicel.client.users.Role;
import com.luminicel.client.users.User;
import com.luminicel.client.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private static final String DEFAULT_PROFILE_PIC =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Windows_10_Default_Profile_Picture.svg/2048px-Windows_10_Default_Profile_Picture.svg.png";

    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void createStudent(StudentForm form) {
        final String encryptedPassword = encoder.encode(form.password());
        final User user = User.builder()
                .firstname(form.firstname())
                .lastname(form.lastname())
                .email(form.email())
                .password(encryptedPassword)
                .role(Role.STUDENT)
                .profilePicture(DEFAULT_PROFILE_PIC)
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
