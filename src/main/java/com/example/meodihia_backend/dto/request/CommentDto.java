package com.example.meodihia_backend.dto.request;

import lombok.Data;

@Data
public class CommentDto {
    private String text;
    private Long idUser;
    private Long idSong;
    private Long idPlaylist;
}
