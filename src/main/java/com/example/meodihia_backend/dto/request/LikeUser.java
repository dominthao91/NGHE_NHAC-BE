package com.example.meodihia_backend.dto.request;

import lombok.Data;

@Data
public class LikeUser {
    private Long idUser;
    private Long idSong;
    private Long idPlaylist;
}
