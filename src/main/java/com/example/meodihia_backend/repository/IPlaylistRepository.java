package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUser_Id(Long idUser);
}
