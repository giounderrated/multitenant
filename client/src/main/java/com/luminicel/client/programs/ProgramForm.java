package com.luminicel.client.programs;

public record ProgramForm(
        String name,
        int credits,
        int cycles,
        Long typeId
) {
}
