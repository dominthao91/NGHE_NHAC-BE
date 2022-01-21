package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query("DELETE from Likes WHERE id = ?1")
    void deleteById(Long id);
}
