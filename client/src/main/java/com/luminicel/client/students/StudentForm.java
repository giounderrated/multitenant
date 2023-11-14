package com.luminicel.client.students;

public record StudentForm(
        String firstname,
        String lastname,
        String email,
        String password,
        String enrollmentId
) {
}
