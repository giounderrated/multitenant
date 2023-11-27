package com.luminicel.client.courses;

public record CourseForm(
        String name,
        String description,
        int credits,
        Long programId
) {
}
