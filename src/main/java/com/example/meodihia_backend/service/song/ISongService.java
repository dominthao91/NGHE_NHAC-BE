package com.example.meodihia_backend.service.song;

import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ISongService extends IGeneralService<Song> {
    Iterable<Song> findAllByUser_Id(Long id);
    Page<Song> findSongByNameContaining(String name,Pageable pageable);
    boolean existsByName(String name);
    Page<Song> findAllLaters(Pageable pageable);
    Page<Song> findAllByCount(Pageable pageable);
    Page<Song> findSongsBySingerNameorSongName(@Param("name") String name, Pageable pageable);

}
