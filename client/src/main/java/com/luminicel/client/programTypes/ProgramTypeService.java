package com.luminicel.client.programTypes;

import java.util.List;

public interface ProgramTypeService {
    void create(ProgramType category);
    List<ProgramType> findAll();

}
