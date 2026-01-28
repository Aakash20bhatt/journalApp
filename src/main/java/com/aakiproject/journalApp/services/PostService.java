package com.aakiproject.journalApp.services;

import com.aakiproject.journalApp.api.response.PostRequest;
import com.aakiproject.journalApp.api.response.PostResponse;
import com.aakiproject.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    @Autowired
    private AppCache appCache;

    private static final String API = "";
    @Autowired
    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTitle() {
        ResponseEntity<PostResponse> postResponse = restTemplate.exchange(appCache.AppCache.get("post_get_request"), HttpMethod.GET, null, PostResponse.class);
        return postResponse.getBody().getTitle();
    }

    // POST (NEW)
    public PostResponse createPost(PostRequest postRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PostRequest> requestEntity =
                new HttpEntity<>(postRequest, headers);

        ResponseEntity<PostResponse> response =
                restTemplate.exchange(
                        appCache.AppCache.get("post_create_request"),
                        HttpMethod.POST,
                        requestEntity,
                        PostResponse.class
                );

        return response.getBody();
    }
}
