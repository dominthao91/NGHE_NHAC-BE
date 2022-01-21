package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    @Modifying
    @Query("DELETE from Comment WHERE id = ?1")
    void deleteById(Long id);

    List<Comment> findAllBySong_Id(Long id);
    List<Comment> findAllByPlaylist_Id(Long id);
}
