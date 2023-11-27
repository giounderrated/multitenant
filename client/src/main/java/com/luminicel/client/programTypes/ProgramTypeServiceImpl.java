package com.luminicel.client.programTypes;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramTypeServiceImpl implements ProgramTypeService {
    private final ProgramTypeRepository repository;

    public ProgramTypeServiceImpl(ProgramTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(ProgramType category) {
        repository.save(category);
    }

    @Override
    public List<ProgramType> findAll() {
        return repository.findAll();
    }
}
