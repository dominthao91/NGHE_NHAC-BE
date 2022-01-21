package com.example.meodihia_backend.service.comment;

import com.example.meodihia_backend.model.Comment;
import com.example.meodihia_backend.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService implements ICommentService{
    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllBySong_Id(Long id) {
        return commentRepository.findAllBySong_Id(id);
    }

    @Override
    public List<Comment> findAllByPlaylist_Id(Long id) {
        return commentRepository.findAllByPlaylist_Id(id);
    }
}
