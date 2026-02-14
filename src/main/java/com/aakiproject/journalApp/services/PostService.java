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

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;


    public String getTitle() {
        PostResponse postResponse = redisService.get("external_title",PostResponse.class);
        if (postResponse != null) {
            return postResponse.getTitle();
        }else{
            ResponseEntity<PostResponse> postResponse1 = restTemplate.exchange(appCache.AppCache.get("post_get_request"), HttpMethod.GET, null, PostResponse.class);
            if (postResponse1.getBody() != null) {
                redisService.set("external_title",postResponse1.getBody(),300l);
            }
            return postResponse1.getBody().getTitle();
        }

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
