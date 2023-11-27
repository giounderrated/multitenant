package com.luminicel.client.programTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramTypeRepository extends JpaRepository<ProgramType,Long> {

}
