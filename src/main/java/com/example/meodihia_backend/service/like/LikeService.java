package com.example.meodihia_backend.service.like;

import com.example.meodihia_backend.model.Likes;
import com.example.meodihia_backend.repository.ILikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LikeService implements ILikeService{

    @Autowired
    ILikeRepository likeRepository;

    @Override
    public List<Likes> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Page<Likes> findAll(Pageable pageable) {
        return likeRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
            likeRepository.deleteById(id);
    }

    @Override
    public Likes save(Likes likes) {
        return likeRepository.save(likes);
    }

    @Override
    public Optional<Likes> findById(Long id) {
        return likeRepository.findById(id);
    }
}
