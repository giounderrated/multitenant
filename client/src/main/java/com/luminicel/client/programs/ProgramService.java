package com.luminicel.client.programs;

import java.util.List;

public interface ProgramService {
    void createProgram(final ProgramForm form);
    List<Program> findAll();
}
