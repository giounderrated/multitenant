package com.luminicel.tenant.shard.infrastructure;

import com.luminicel.tenant.shard.domain.DataSourceDetails;
import com.luminicel.tenant.shard.domain.DataSourceDetailsServiceImpl;
import com.luminicel.tenant.shard.domain.DataSourceForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataSourceController {
    public static final String DATA_SOURCES = "/datasources";

    private final DataSourceDetailsServiceImpl service;

    public DataSourceController(DataSourceDetailsServiceImpl service) {
        this.service = service;
    }

    @PostMapping(DATA_SOURCES)
    public ResponseEntity<String> createDataSource(@RequestBody final DataSourceForm form) {
        service.createDataSource(form);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @GetMapping(DATA_SOURCES)
    public List<DataSourceDetails> getAll() {
        return service.getAll();
    }


}
