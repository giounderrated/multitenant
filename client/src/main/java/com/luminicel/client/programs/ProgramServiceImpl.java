package com.luminicel.client.programs;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService{
    private final ProgramRepository repository;

    public ProgramServiceImpl(ProgramRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createProgram(ProgramForm form) {
        final Program program = Program.builder()
                .name(form.name())
                .cycles(form.cycles())
                .credits(form.credits())
                .typeId(form.typeId())
                .build();
        repository.save(program);
    }

    @Override
    public List<Program> findAll() {
        return repository.findAll();
    }
}
