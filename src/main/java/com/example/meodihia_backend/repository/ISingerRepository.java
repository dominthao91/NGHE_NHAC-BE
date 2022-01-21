package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Singer;
import com.example.meodihia_backend.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISingerRepository extends JpaRepository<Singer, Long> {
    Page<Singer> findSingerByNameContaining(String name, Pageable pageable);
    boolean existsByName(String name);
}
