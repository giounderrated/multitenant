package com.luminicel.client.courses;

import java.util.List;

public interface CourseService {
    void createCourse(final CourseForm form);
    List<Course> findAll();
}
