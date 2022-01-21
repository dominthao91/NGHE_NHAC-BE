package com.example.meodihia_backend.dto.request;

import lombok.Data;

@Data
public class DeleteSong {
    private Long idPlaylist;
    private Long idSong;
}
