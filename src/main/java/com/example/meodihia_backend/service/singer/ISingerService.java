package com.example.meodihia_backend.service.singer;

import com.example.meodihia_backend.model.Singer;
import com.example.meodihia_backend.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISingerService extends IGeneralService<Singer> {
    Page<Singer> findSingerByNameContaining(String name, Pageable pageable);
    boolean existsByName(String name);
}
