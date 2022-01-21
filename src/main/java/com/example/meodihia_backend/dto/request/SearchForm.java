package com.example.meodihia_backend.dto.request;

import lombok.Data;

@Data
public class SearchForm {
    private boolean isNameOrSinger;
    private String name;
}
