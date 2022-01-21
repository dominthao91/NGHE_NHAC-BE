package com.example.meodihia_backend.service.singer;

import com.example.meodihia_backend.model.Singer;
import com.example.meodihia_backend.repository.ISingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingerService implements ISingerService{

    @Autowired
    private ISingerRepository singerRepository;
    @Override
    public List<Singer> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public Page<Singer> findAll(Pageable pageable) {
        return singerRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        singerRepository.deleteById(id);
    }

    @Override
    public Singer save(Singer singer) {
        return singerRepository.save(singer);
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return singerRepository.findById(id);
    }

    @Override
    public Page<Singer> findSingerByNameContaining(String name, Pageable pageable) {
        return singerRepository.findSingerByNameContaining(name, pageable);
    }

    @Override
    public boolean existsByName(String name) {
        return singerRepository.existsByName(name);
    }
}
