package com.luminicel.client.courses;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createCourse(CourseForm form) {
        final Course course = Course.builder()
                .name(form.name())
                .description(form.description())
                .credits(form.credits())
                .programId(form.programId())
                .build();
        repository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }
}
