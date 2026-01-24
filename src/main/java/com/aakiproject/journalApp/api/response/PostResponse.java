package com.aakiproject.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private int userId;
    private int id;
    private String title;
    private String body;
}
