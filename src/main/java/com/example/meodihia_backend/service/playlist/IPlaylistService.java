package com.example.meodihia_backend.service.playlist;

import com.example.meodihia_backend.model.Playlist;
import com.example.meodihia_backend.service.IGeneralService;

import java.util.List;

public interface IPlaylistService extends IGeneralService<Playlist> {
    List<Playlist> findAllByUser_Id(Long idUser);
}
