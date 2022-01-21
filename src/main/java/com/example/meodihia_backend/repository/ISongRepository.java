package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends JpaRepository<Song,Long>{
    Iterable<Song> findAllByUser_Id(Long id);
    Page<Song> findAll(Pageable pageable);
    Page<Song> findSongsByNameContaining(String name, Pageable pageable);

    @Query(value = "SELECT * from song  join song_singer ss on song.id = ss.song_id join singer s on s.id = ss.singer_id WHERE s.name like CONCAT('%',:name,'%') or song.name like CONCAT('%',:name,'%')", nativeQuery = true)
    Page<Song> findSongsBySingerNameorSongName(@Param("name") String name, Pageable pageable);

    boolean existsByName(String name);


}
