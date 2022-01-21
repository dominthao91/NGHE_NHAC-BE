package com.example.meodihia_backend.service.playlist;

import com.example.meodihia_backend.model.Playlist;
import com.example.meodihia_backend.repository.IPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService implements IPlaylistService{
    @Autowired
    private IPlaylistRepository playlistRepository;
    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Page<Playlist> findAll(Pageable pageable) {
        return playlistRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public List<Playlist> findAllByUser_Id(Long idUser) {
        return playlistRepository.findAllByUser_Id(idUser);
    }
}
