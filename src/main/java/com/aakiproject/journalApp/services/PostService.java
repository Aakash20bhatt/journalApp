package com.aakiproject.journalApp.services;

import com.aakiproject.journalApp.api.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    private static final String API = "https://jsonplaceholder.typicode.com/posts/1";

    @Autowired
    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTitle() {
        ResponseEntity<PostResponse> postResponse = restTemplate.exchange(API, HttpMethod.GET, null, PostResponse.class);
        return postResponse.getBody().getTitle();
    }
}
