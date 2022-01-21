package com.example.meodihia_backend.service.song;

import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService{
    @Autowired
    private ISongRepository songRepository;
    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }
    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }
    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }
    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public Page<Song> findAllByCount(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Page<Song> findSongsBySingerNameorSongName(String name, Pageable pageable) {
        return songRepository.findSongsBySingerNameorSongName(name, pageable);
    }

    @Override
    public Page<Song> findAllLaters(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Iterable<Song> findAllByUser_Id(Long id) {
        return songRepository.findAllByUser_Id(id);
    }

    @Override
    public Page<Song> findSongByNameContaining(String name, Pageable pageable) {
        return songRepository.findSongsByNameContaining(name,pageable);
    }

    @Override
    public boolean existsByName(String name) {
        return songRepository.existsByName(name);
    }
}
