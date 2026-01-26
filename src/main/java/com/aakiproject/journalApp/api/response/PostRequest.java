package com.aakiproject.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String body;
    private int userId;
}
