package com.example.meodihia_backend.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class PlayListAdd {
    private Long idPlaylist;
    private List<Long> idSongs;
}
