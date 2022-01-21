package com.example.meodihia_backend.dto.request;

import lombok.Data;

@Data
public class PlayListForm {
    private String name;
    private String file;
    private Long idUser;
}
