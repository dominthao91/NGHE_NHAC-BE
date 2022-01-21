package com.example.meodihia_backend.service.comment;

import com.example.meodihia_backend.model.Comment;
import com.example.meodihia_backend.service.IGeneralService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentService extends IGeneralService<Comment> {
    List<Comment> findAllBySong_Id(Long id);
    List<Comment> findAllByPlaylist_Id(Long id);
}
